/**
 * 
 */
package com.ctapweb.feature.featureAE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
import com.ctapweb.feature.type.NToken;
import com.ctapweb.feature.type.POS;
import com.ctapweb.feature.type.POSDensity;
import com.ctapweb.feature.util.EnglishWordCategories;
import com.ctapweb.feature.util.GermanWordCategories;
import com.ctapweb.feature.util.WordCategories;


public class POSDensityAE extends JCasAnnotator_ImplBase {

	//the analysis engine's id from the database
	//this value needs to be set when initiating the analysis engine
	public static final String PARAM_AEID = "aeID";
	public static final String PARAM_POS_TYPE= "POSType";
	public static final String PARAM_LANGUAGE_CODE = "LanguageCode";

	private int aeID;

	//list of pos tags to be counted
	List<String> posList = new ArrayList<>();

	private static final Logger logger = LogManager.getLogger();
	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "POS Density Feature Extractor";
	private WordCategories posMapping;

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));
		super.initialize(aContext);

		// obtain mandatory language parameter and access language dependent resources
		String lCode = "";
		if(aContext.getConfigParameterValue(PARAM_LANGUAGE_CODE) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_LANGUAGE_CODE});
			logger.throwing(e);
			throw e;
		} else {
			lCode = (String) aContext.getConfigParameterValue(PARAM_LANGUAGE_CODE);
			switch (lCode) {
			case "DE":
				posMapping = new GermanWordCategories();
				break;
			case "EN":
			default:  // See if this is a reasonable default
				posMapping = new EnglishWordCategories();
				break;
			}
		}
		String languageSpecificResourceKey = PARAM_POS_TYPE+lCode;

		String POSType = (String) aContext.getConfigParameterValue(languageSpecificResourceKey);
		posList = getPOSTagList(POSType);
		//logger.trace(LogMarker.UIMA_MARKER, "The following POS density ("+PARAM_POS_TYPE + lCode.orElse(SupportedLanguages.DEFAULT)+") will be calculated for POSType "+POSType+": ");
		for(String pos: posList) {
			logger.trace(LogMarker.UIMA_MARKER, pos);
		}

		//get the parameter value of analysis id
		if(aContext.getConfigParameterValue(PARAM_AEID) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_AEID});
			logger.throwing(e);
			throw e;
		} else {
			aeID = (Integer) aContext.getConfigParameterValue(PARAM_AEID);
		}

		logger.trace(LogMarker.UIMA_MARKER, new InitializeAECompleteMessage(aeType, aeName));
	}

	/* (non-Javadoc)
	 * @see org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
	 */
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		logger.trace(LogMarker.UIMA_MARKER, 
				new ProcessingDocumentMessage(aeType, aeName, aJCas.getDocumentText()));

		// get annotation indexes and iterator
		Iterator posIter = aJCas.getAnnotationIndex(POS.type).iterator();

		//count number of occurrences 
		int nPOSTypes = 0;
		while(posIter.hasNext()) {
			POS pos = (POS) posIter.next();
			String tag = pos.getTag();
			if(posList.contains(tag)) {
				nPOSTypes++;
				//logger.trace(LogMarker.UIMA_MARKER, "found a target tag: " + tag);
			}
		}

		int nTokens = getNTokens(aJCas);
		double density = 0;
		if(nTokens != 0 ) {
			density = (double)nPOSTypes / nTokens;
		}

		//output the feature type
		POSDensity annotation = new POSDensity(aJCas);

		//set the feature ID
		annotation.setId(aeID);

		//set feature value
		annotation.setValue(density);
		annotation.addToIndexes();

		logger.info(new PopulatedFeatureValueMessage(aeID, density));
	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));

		super.destroy();

		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}

	// TODO see if this works
	//gets the parts of speech that this feature is looking for
	//uses for common categories the definitions from the word Categories class
	private List<String> getPOSTagList(String POSType) {
		List<String> tagList = new ArrayList<>();

		switch(POSType) {
		case "lexical": 
			Collections.addAll(tagList, posMapping.getLexicalWords());
			break;
		case "functional": 
			Collections.addAll(tagList, posMapping.getFunctionalWords());
			break;
		case "adjective":
			Collections.addAll(tagList, posMapping.getAdjectives());
			break;
		case "noun":
			Collections.addAll(tagList, posMapping.getNouns());
			break;
		case "adverb":
			Collections.addAll(tagList, posMapping.getAdverbs());
			break;
		case "verb":
			Collections.addAll(tagList, posMapping.getVerbs());
			break;
		case "pronouns":
			Collections.addAll(tagList, posMapping.getPronouns());
			break;
		case "modifier":
			Collections.addAll(tagList, posMapping.getAdverbs());
			Collections.addAll(tagList, posMapping.getAdjectives());
			break;
		default:
			//deal with space separated POS type list
			String[] types = POSType.split("\\b");
			Collections.addAll(tagList, types);
		}

		return tagList;
	}

	// get number of tokens from the CAS
	private int getNTokens(JCas aJCas) {
		int n = 0;
		//		Iterator posIter = aJCas.getAnnotationIndex(NToken.type).iterator();
		Iterator posIter = aJCas.getAllIndexedFS(NToken.class);
		if(posIter.hasNext()) {
			NToken nToken = (NToken)posIter.next();
			n = (int) nToken.getValue();
		}
		return n;
	}
}
