package com.ctapweb.feature.featureAE;

import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import com.ctapweb.feature.logging.LogMarker;
import com.ctapweb.feature.logging.message.AEType;
import com.ctapweb.feature.logging.message.DestroyAECompleteMessage;
import com.ctapweb.feature.logging.message.DestroyingAEMessage;
import com.ctapweb.feature.logging.message.InitializeAECompleteMessage;
import com.ctapweb.feature.logging.message.InitializingAEMessage;
import com.ctapweb.feature.logging.message.PopulatedFeatureValueMessage;
import com.ctapweb.feature.logging.message.ProcessingDocumentMessage;
import com.ctapweb.feature.type.DLTIntegrationCost;
import com.ctapweb.feature.type.DependencyParse;
import com.ctapweb.feature.util.DependencyLabelCategories;
import com.ctapweb.feature.util.DependencyTree;
import com.ctapweb.feature.util.GermanDependencyLabels;
import com.ctapweb.feature.util.GermanWordCategories;
import com.ctapweb.feature.util.WordCategories;

/**
 * Calculates cognitive Integration Costs based on Gibson 2000's Dependency Locality Theory, see Shain et al. 2016 for configurations
 * @author zweiss
 *
 */
public class DLTIntegrationCostAE  extends JCasAnnotator_ImplBase {

	//the analysis engine's id from the database
	//this value needs to be set when initiating the analysis engine
	public static final String PARAM_AEID = "aeID";
	public static final String PARAM_DLT_COST_CALC_CONFIG = "dltConfiguration";
	public static final String DLT_FEATURE_TYPE = "featureType";


	private int aeID;
	private String dltCostCalculationConfig;
	private String featureType;
	private WordCategories posMapping;
	private DependencyLabelCategories labelMapping;

	private static final Logger logger = LogManager.getLogger();

	private static final String PARAM_LANGUAGE_CODE = "LanguageCode";
	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "Dependency Locality Theory Feature Extractor";

	private static final String ENHANCED_VERB_WEIGHT_CONFIG = "v";
	private static final String CANCELLED_MODIFIER_WEIGHT_CONFIG = "w";
	private static final String REDUCED_COORDINATION_WEIGHT_CONFIG = "c";
	private static final int HIGH_COST_THRESHOLD = 2;  // TODO maybe give this as parameter in descriptor file

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));
		super.initialize(aContext);

		// get the parameters

		// obtain mandatory language parameter and access language dependent resources
		String lCode = "";
		if(aContext.getConfigParameterValue(PARAM_LANGUAGE_CODE) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_LANGUAGE_CODE});
			logger.throwing(e);
			throw e;
		} else {
			lCode = ((String) aContext.getConfigParameterValue(PARAM_LANGUAGE_CODE)).toUpperCase();
			switch (lCode) {
			case "DE":
				posMapping = new GermanWordCategories();
				labelMapping = new GermanDependencyLabels();
				break;
			case "EN":
			default:  // See if this is a reasonable default
				posMapping = new GermanWordCategories();
				labelMapping = new GermanDependencyLabels();
				break;
			}
		}

		// get the parameter value of analysis id
		if(aContext.getConfigParameterValue(PARAM_AEID) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_AEID});
			logger.throwing(e);
			throw e;
		} else {
			aeID = (Integer) aContext.getConfigParameterValue(PARAM_AEID);
		}

		// get DLT configuration 
		if(aContext.getConfigParameterValue(PARAM_DLT_COST_CALC_CONFIG) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_DLT_COST_CALC_CONFIG});
			logger.throwing(e);
			throw e;
		} else {
			dltCostCalculationConfig = (String) aContext.getConfigParameterValue(PARAM_DLT_COST_CALC_CONFIG);
		}

		// get DLT feature type 
		if(aContext.getConfigParameterValue(DLT_FEATURE_TYPE) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {DLT_FEATURE_TYPE});
			logger.throwing(e);
			throw e;
		} else {
			featureType = (String) aContext.getConfigParameterValue(DLT_FEATURE_TYPE);
		}

//		logger.trace(LogMarker.UIMA_MARKER, "Calculate DLT feature: {} for DLT configuration: {}", featureType, dltCostCalculationConfig);
		logger.trace(LogMarker.UIMA_MARKER, new InitializeAECompleteMessage(aeType, aeName));
	}

	/* (non-Javadoc)
	 * @see org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
	 */
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		logger.trace(LogMarker.UIMA_MARKER, 
				new ProcessingDocumentMessage(aeType, aeName, aJCas.getDocumentText()));
		int nFiniteVerbs = 0;  // denominator
		int numerator = 0; 

		// get annotation indexes and iterator
		//iterate through all dependency trees (sentences).
		Iterator it = aJCas.getAnnotationIndex(DependencyParse.type).iterator();

		// get the number of discourse referents
		String curPos, curLabel;
		boolean foundFiniteVerb;
		int curIC, prevIC, maxIC, wordID;  // IC = integration cost
		boolean[] wordAtIntexAddsToDIC;  // DIC = discourse integration cost
		while(it.hasNext()) {
			DependencyParse depParse = (DependencyParse) it.next();
//			logger.trace(LogMarker.UIMA_MARKER, "Dependency parse: {}", depParse.getDependencyParse());  // debugging
			//code requires a dependency tree object, so create a dependency tree from the parse string
			DependencyTree depTree = DependencyTree.valueOf(depParse.getDependencyParse());
			// Calculate DLT features
			foundFiniteVerb = false;
			wordAtIntexAddsToDIC = new boolean[depTree.getForms().length-1];
			curIC = 0;
			prevIC = 0;
			maxIC = 0;
			for (int dpID = 1; dpID < depTree.getPOS().length; dpID++) {  // start at index 1, because dep trees have root element at 0
				wordID = dpID-1;  // note: dependency tree has an initial root index, i.e. it is proper word indexing + 1
				curPos = depTree.getPOS()[wordID];
				curLabel = depTree.getLabels()[wordID];

				// 1. calculate the discourse integration cost: new discourse referents are nouns
				if (posMapping.isNoun(curPos)) {
					foundFiniteVerb = false;
					if (dltCostCalculationConfig.contains(REDUCED_COORDINATION_WEIGHT_CONFIG)) {
						wordAtIntexAddsToDIC[wordID] = !labelMapping.isConjunct(curLabel);
						continue;
					} 
					wordAtIntexAddsToDIC[wordID] = true;
					continue;
				}

				// verbs may be considered discourse referents, but nothing else, so ignore word, if it isn't a verb
				if (!posMapping.isVerb(curPos)) {
					foundFiniteVerb = false;
					wordAtIntexAddsToDIC[wordID] = false;
					continue;
				}

				// if we are here, we found a verb. Only finite verbs are relevant here, unless this is the extra verb weight configuration
				if(!posMapping.isFiniteVerb(curPos)) {
					foundFiniteVerb = false;
					wordAtIntexAddsToDIC[wordID] = dltCostCalculationConfig.contains(ENHANCED_VERB_WEIGHT_CONFIG);
					continue;
				}

				// integration step: at this point we found a finite verb and integrate the discourse referents
				wordAtIntexAddsToDIC[wordID] = true;
				nFiniteVerbs += 1;

				// save previous counts, if preceding token was a finite verb, too
				if (foundFiniteVerb) {
					prevIC = curIC;
				}
				curIC = caluculateIC(depTree, wordAtIntexAddsToDIC, dpID);
				maxIC = Math.max(curIC, maxIC);

				if (featureType.equals("totalIC")) {  // sum up all ICs
					numerator += curIC;
				}
				// sum up only adjacent counts that break the threshold
				else if (featureType.equals("highAdjacentIC")) {  
					if (prevIC > HIGH_COST_THRESHOLD && curIC > HIGH_COST_THRESHOLD) {
						numerator += 1;
					}
				}
			}
			if (featureType.equals("maxIC")) {  // sum up all max ICs
				numerator += maxIC;
			}
		}


		// calculate the feature
		double icFeature = 0.0;
		if(nFiniteVerbs > 0) {
			icFeature = numerator / nFiniteVerbs;
		}
		
		logger.trace(LogMarker.UIMA_MARKER, "Calculate DLT feature: {} for DLT configuration: {}: {} / {} = {}", dltCostCalculationConfig, featureType, numerator, nFiniteVerbs, icFeature);  

		//output the feature type
		DLTIntegrationCost annotation = new DLTIntegrationCost(aJCas);
		//set the feature ID and feature value
		annotation.setId(aeID);
		annotation.setValue(icFeature);
		annotation.setIntegrationCostType(featureType);
		annotation.setCostCalculationConfiguration(dltCostCalculationConfig);
		annotation.addToIndexes();

		logger.info(new PopulatedFeatureValueMessage(aeID, icFeature));
	}


	/**
	 * Calculates the total integration cost at the position of a finite verb for several cost calculation configurations.
	 * Note: this code assumes that total integrations costs are only calculated at the point of finite verbs, because
	 * at all other positions in the sentence, the integration costs will not exceed 1 anyway.
	 * @param depTree dependency tree in which finite verb occurs
	 * @param dic discourse integration cost so far calculated for the given sentence based on the current cost calculation configurations
	 * @param wordID position of the finite verb in question in the sentence
	 * @return total integration cost of current cost calculation configurations
	 */
	private int caluculateIC(DependencyTree depTree, boolean[] wordAtIntexAddsToDIC, int wordID) {
		int curIC = 0;
		int endID = -1;
		for (int depID : depTree.getDependents(wordID)) {
			// Step 0: skip dependents that occur to the right of their head, 
			// i.e. stop iteration as soon as first depID larger than head (assume ordered list of dependents)
			if (depID > wordID) {
				break;
			}

			// Step 1: determine the end index for structural integration:
			// note: if we have a relative clause, the finite verb itself contributes to the structural integration cost
			if (posMapping.isRelativePronoun(depTree.getForms()[depID], depTree.getPOS()[depID]) || 
					(posMapping.isPreposition(depTree.getPOS()[depID]) && 
							depID+1<depTree.getPOS().length && 
							posMapping.isRelativePronoun(depTree.getForms()[depID+1], depTree.getPOS()[depID+1]))) {
				// TODO rewrite this to alternatively use RC deprel label: das Haus, wo wir gewohnt haben
				endID = wordID+1;
			} else {  // all other cases: don't include finite verb itself as discourse referent
				endID = wordID;
			}

			// Step 2: calculate structural integration cost
			// TODO understand this step again and figure out, why we need to do this
			for (int id = depID+1; id < endID; id++) {
				// structurally integrate for "less modifier weight" condition only arguments, not modifiers!
				if (dltCostCalculationConfig.contains(CANCELLED_MODIFIER_WEIGHT_CONFIG) && 
						!labelMapping.isArgument(depTree.getLabels()[depID])) {
					continue;
				}

				// add integration cos if the current dependent has been flagged as adding costs in the current condition
				if (wordAtIntexAddsToDIC[id-1]) {
					curIC += 1;
				}
			}
		}

		// Step 3: add discourse integration cost for finite verb, which is +2 for all integration costs that add verb weight
		if (dltCostCalculationConfig.contains(ENHANCED_VERB_WEIGHT_CONFIG)) {
			curIC += 2;
		} else {
			curIC += 1;
		}

		return curIC;
	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));

		super.destroy();

		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}
}
