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
import org.apache.uima.resource.ResourceInitializationException;

import com.ctapweb.feature.logging.LogMarker;
import com.ctapweb.feature.logging.message.AEType;
import com.ctapweb.feature.logging.message.DestroyAECompleteMessage;
import com.ctapweb.feature.logging.message.DestroyingAEMessage;
import com.ctapweb.feature.logging.message.InitializeAECompleteMessage;
import com.ctapweb.feature.logging.message.InitializingAEMessage;
import com.ctapweb.feature.logging.message.PopulatedFeatureValueMessage;
import com.ctapweb.feature.logging.message.ProcessingDocumentMessage;
import com.ctapweb.feature.type.LexicalVariation;
import com.ctapweb.feature.type.NToken;
import com.ctapweb.feature.type.POS;
import com.ctapweb.feature.type.POSDensity;
import com.ctapweb.feature.type.Token;


public class LexicalVariationAE extends JCasAnnotator_ImplBase {

	//the analysis engine's id from the database
	//this value needs to be set when initiating the analysis engine
	public static final String PARAM_AEID = "aeID";
	public static final String PARAM_LEXICAL_TYPE= "LexicalType";
	private int aeID;

	//list of pos tags to be counted
	String POSType;
	List<String> posList = new ArrayList<>();

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "Lexical Variation Feature Extractor";

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));
		super.initialize(aContext);

		//get the parameter value of analysis id
		if(aContext.getConfigParameterValue(PARAM_AEID) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_AEID});
			logger.throwing(e);
			throw e;
		} else {
			aeID = (Integer) aContext.getConfigParameterValue(PARAM_AEID);
		}

		// get the lexical type mandatory pamameter value
		if(aContext.getConfigParameterValue(PARAM_LEXICAL_TYPE) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_LEXICAL_TYPE});
			logger.throwing(e);
			throw e;
		} else {
			POSType = (String) aContext.getConfigParameterValue(PARAM_LEXICAL_TYPE);
			posList = getPOSTagList(POSType);
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

		//the list of lexical pos
		List<String> lexicalPoSList = new ArrayList<>();
		String[] lexical = {
				"JJ", "JJR", "JJS", //adj
				"RB", "RBR", "RBS", "WRB", //adv
				"VB", "VBD", "VBG", "VBN", "VBP", "VBZ", //verb
				"NN", "NNS", "NNP", "NNPS" //noun
		};
		Collections.addAll(lexicalPoSList, lexical);
		
		int nLexicalTokens = 0;
		Set<String> targetTypes = new HashSet<>();
		
		// get annotation indexes and iterator
		Iterator posIter = aJCas.getAnnotationIndex(POS.type).iterator();

		//count number of occurrences 
//		int nPOSTypes = 0;
		while(posIter.hasNext()) {
			POS pos = (POS) posIter.next();
			String tag = pos.getTag();
			
			//count lexical tokens
			if(lexicalPoSList.contains(tag)) {
				nLexicalTokens ++;
			}

			//count lexical types
			if(posList.contains(tag)) {
				targetTypes.add(pos.getCoveredText().toLowerCase());
			}
		}

		double variation = 0;
		if(nLexicalTokens != 0) {
			variation = (double) targetTypes.size() / nLexicalTokens;
		}
		
		logger.trace(LogMarker.UIMA_MARKER, "{} type count: {}; lexical token count: {}", 
				POSType, targetTypes.size(), nLexicalTokens);

		//output the feature type
		LexicalVariation annotation = new LexicalVariation(aJCas);
		
		//set the feature ID
		annotation.setId(aeID);

		//set feature value
		annotation.setValue(variation);
		annotation.addToIndexes();

		logger.info(new PopulatedFeatureValueMessage(aeID, variation));
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
		String[] adjective = {"JJ", "JJR", "JJS"};
		String[] noun = { "NN", "NNS", "NNP", "NNPS" };
		String[] adverb = {"RB", "RBR", "RBS", "WRB"};
		String[] verb = {"VB", "VBD", "VBG", "VBN", "VBP", "VBZ"};


		switch(POSType) {
		case "lexical": 
			Collections.addAll(tagList, lexical);
			break;
		case "adjective":
			Collections.addAll(tagList, adjective);
			break;
		case "noun":
			Collections.addAll(tagList, noun);
			break;
		case "adverb":
			Collections.addAll(tagList, adverb);
			break;
		case "verb":
			Collections.addAll(tagList, verb);
			break;
		case "modifier":
			Collections.addAll(tagList, adjective);
			Collections.addAll(tagList, adverb);
			break;
		default:
			//deal with space separated POS type list
			String[] types = POSType.split("\\b");
			Collections.addAll(tagList, types);
		}

		return tagList;
	}

}
