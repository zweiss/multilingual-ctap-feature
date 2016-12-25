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
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;
import org.apache.uima.resource.ResourceInitializationException;

import com.ctapweb.feature.logging.LogMarker;
import com.ctapweb.feature.logging.message.AEType;
import com.ctapweb.feature.logging.message.DestroyAECompleteMessage;
import com.ctapweb.feature.logging.message.DestroyingAEMessage;
import com.ctapweb.feature.logging.message.InitializeAECompleteMessage;
import com.ctapweb.feature.logging.message.InitializingAEMessage;
import com.ctapweb.feature.logging.message.PopulatedFeatureValueMessage;
import com.ctapweb.feature.logging.message.ProcessingDocumentMessage;
import com.ctapweb.feature.type.LexicalSophistication;
import com.ctapweb.feature.type.NToken;
import com.ctapweb.feature.type.POS;
import com.ctapweb.feature.type.POSDensity;
import com.ctapweb.feature.type.Token;
import com.ctapweb.feature.util.LookUpTableResource;


public class LexicalSophisticationAE extends JCasAnnotator_ImplBase {

	//the analysis engine's id from the database
	//this value needs to be set when initiating the analysis engine
	public static final String PARAM_AEID = "aeID";
	public static final String PARAM_SCOPE = "scope";
	public static final String RESOURCE_KEY = "lookUpTable";
	private int aeID;
	private String scope;
	private LookUpTableResource lookUpTable;

	//list of pos tags to be included in the calculation, depending on scope setting
	List<String> posList = new ArrayList<>();

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "Lexical Sophistication Feature Extractor";

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));
		super.initialize(aContext);

		// get the mandatory pamameter
		if(aContext.getConfigParameterValue(PARAM_SCOPE) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_SCOPE});
			logger.throwing(e);
			throw e;
		} else {
			scope = (String) aContext.getConfigParameterValue(PARAM_SCOPE);
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

		//get the lookup table
		try {
			lookUpTable = (LookUpTableResource) aContext.getResourceObject(RESOURCE_KEY);
		} catch (ResourceAccessException e) {
			logger.throwing(e);
			throw new ResourceInitializationException(e);
		}
		
		posList = getPOSTagList(scope);

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

		//calculate sophistication value
		double sum = 0;
		int count = 0;

		while(posIter.hasNext()) {
			POS pos = (POS) posIter.next();
			String tag = pos.getTag();
			String word = pos.getCoveredText().toLowerCase();
			
			//lookup sophistication value from look up table
			if("AW".equals(scope) || posList.contains(tag)) {
				sum += lookUpTable.lookup(word);
				count ++;
			} 
		}
		
		logger.trace(LogMarker.UIMA_MARKER, 
				"Calculated total sophistication value {} from scope {} on {} words.", sum, scope, count);

		//average sophistication
		double sophistication = 0;
		if(count != 0 ) {
			sophistication = sum / count;
		}

		//output the feature type
		LexicalSophistication annotation = new LexicalSophistication(aJCas);

		//set the feature ID
		annotation.setId(aeID);

		//set feature value
		annotation.setValue(sophistication);
		annotation.addToIndexes();

		logger.info(new PopulatedFeatureValueMessage(aeID, sophistication));
	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));

		super.destroy();

		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}

	//gets the parts of speech that this feature is looking for
	private List<String> getPOSTagList(String POSType) {
		List<String> tagList = new ArrayList<>();

		String[] lexical = {
				"JJ", "JJR", "JJS", //adj
				"RB", "RBR", "RBS", "WRB", //adv
				"VB", "VBD", "VBG", "VBN", "VBP", "VBZ", //verb
				"NN", "NNS", "NNP", "NNPS" //noun
		};
		String[] functional = {
				"CC", "IN", "PDT", "DT", "WDT", "PRP", "PRP$", "WP", "WP$",
				"CD", "EX", "FW", "LS", "MD", "POS", "RP", "SYM", "TO", "UH"
		};

		switch(POSType) {
		case "LW": 
			Collections.addAll(tagList, lexical);
			break;
		case "FW":
			Collections.addAll(tagList, functional);
			break;
		}

		return tagList;
	}
}
