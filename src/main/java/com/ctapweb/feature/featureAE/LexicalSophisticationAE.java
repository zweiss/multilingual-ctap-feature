/**
 * 
 */
package com.ctapweb.feature.featureAE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import com.ctapweb.feature.util.EnglishWordCategories;
import com.ctapweb.feature.util.GermanWordCategories;
import com.ctapweb.feature.util.LookUpTableResource;
import com.ctapweb.feature.util.WordCategories;


public class LexicalSophisticationAE extends JCasAnnotator_ImplBase {

	//the analysis engine's id from the database
	//this value needs to be set when initiating the analysis engine
	public static final String PARAM_AEID = "aeID";
	public static final String PARAM_SCOPE = "Scope";
	public static final String PARAM_TYPE = "type";
	public static final String RESOURCE_KEY = "lookUpTable";
	public static final String PARAM_LANGUAGE_CODE = "LanguageCode";
	private int aeID;
	private String scope;
	private boolean type = false; //whether to count word types or tokens: "true" means to calculate word types instead of tokens
	private LookUpTableResource lookUpTable;

	//list of pos tags to be included in the calculation, depending on scope setting
	List<String> posList = new ArrayList<>();
	private WordCategories posMapping;

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "Lexical Sophistication Feature Extractor";
	
	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));
		super.initialize(aContext);

		// get the mandatory parameter
		if(aContext.getConfigParameterValue(PARAM_SCOPE) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_SCOPE});
			logger.throwing(e);
			throw e;
		} else {
			scope = (String) aContext.getConfigParameterValue(PARAM_SCOPE);
		}

		// get the optional parameter of "type" (boolean)
		if(aContext.getConfigParameterValue(PARAM_TYPE) != null) {
			type = (Boolean) aContext.getConfigParameterValue(PARAM_TYPE);
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
				break;
			case "EN":
			default:  // See if this is a reasonable default
				posMapping = new EnglishWordCategories();
				break;
			}
		}
		String languageSpecificResourceKey = RESOURCE_KEY + lCode;

		//get the lookup table
		try {
			lookUpTable = (LookUpTableResource) aContext.getResourceObject(languageSpecificResourceKey);
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

		//for storing unique word types
		Set<String> wordTypes = new HashSet<>();

		while(posIter.hasNext()) {
			POS pos = (POS) posIter.next();
			String tag = pos.getTag();
			String word = pos.getCoveredText().toLowerCase();

			//only count unique words
			if(type && wordTypes.contains(word)) {
				continue; //skip repeated tokens
			} else {
				wordTypes.add(word);
			}	

			//lookup sophistication value from look up table
			if("AW".equals(scope) || posList.contains(tag)) {
				double value = lookUpTable.lookup(word);
				if(value != 0) {
					sum += value;
					count ++;
				}
			} 
		}

		logger.trace(LogMarker.UIMA_MARKER, 
				"Calculated total sophistication value {} from scope {} on {} words (word type? {}).", 
				sum, scope, count, type);

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

		switch(POSType) {
		case "LW": 
			Collections.addAll(tagList, posMapping.getLexicalWords());
			break;
		case "FW":
			Collections.addAll(tagList, posMapping.getFunctionalWords());
			break;
		}

		return tagList;
	}
}
