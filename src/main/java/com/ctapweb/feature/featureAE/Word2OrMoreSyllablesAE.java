/**
 * 
 */
package com.ctapweb.feature.featureAE;

import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import com.ctapweb.feature.logging.LogMarker;
import com.ctapweb.feature.logging.message.AEType;
import com.ctapweb.feature.logging.message.InitializeAECompleteMessage;
import com.ctapweb.feature.logging.message.InitializingAEMessage;
import com.ctapweb.feature.logging.message.PopulatedFeatureValueMessage;
import com.ctapweb.feature.logging.message.ProcessingDocumentMessage;
import com.ctapweb.feature.type.Letter;
import com.ctapweb.feature.type.Syllable;
import com.ctapweb.feature.type.Token;
import com.ctapweb.feature.type.Word2OrMoreSyllables;

/**
 * @author xiaobin
 * Calculates the number/percentage of words with more than 2 syllables.
 */
public class Word2OrMoreSyllablesAE extends JCasAnnotator_ImplBase {

	//the analysis engine's id from the database
	//this value needs to be set when initiating the analysis engine
	public static final String PARAM_AEID = "aeID";
	public static final String PARAM_NUMORPERCENTAGE = "numberOrPercent"; //calculate by counting number or percentage
	public static final String PARAM_TYPEORTOKEN = "typeOrToken"; //count type or token
	private int aeID;
	private String numOrPercentage = null;
	private String typeOrToken = null;

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "Word2OrMoreSyllables Feature Extractor";

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

		//get the numOrPercentage parameter value
		if(aContext.getConfigParameterValue(PARAM_NUMORPERCENTAGE) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_NUMORPERCENTAGE});
			logger.throwing(e);
			throw e;
		} else {
			numOrPercentage = (String) aContext.getConfigParameterValue(PARAM_NUMORPERCENTAGE);

			//check if value is correct
			switch(numOrPercentage) {
			case "number":
			case "percent":
				break;
			default: 
				throw new ResourceInitializationException("annotator_parameter_not_valid", 
						new Object[] {numOrPercentage,PARAM_NUMORPERCENTAGE});
			}
		}

		//get the typeOrToken parameter value
		if(aContext.getConfigParameterValue(PARAM_TYPEORTOKEN) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_TYPEORTOKEN});
			logger.throwing(e);
			throw e;
		} else {
			typeOrToken = (String) aContext.getConfigParameterValue(PARAM_TYPEORTOKEN);

			//check if value is correct
			switch(typeOrToken) {
			case "type":
			case "token":
				break;
			default: 
				throw new ResourceInitializationException("annotator_parameter_not_valid", 
						new Object[] {typeOrToken,PARAM_TYPEORTOKEN});
			}
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
		
		int countToken2OrMoreSylla = 0;
		int countType2OrMoreSylla = 0;
		int countNToken = 0;
		int countNType = 0;
		HashSet<String> typeSet = new HashSet<>();

		// get token and syllable annotation iterators
		Iterator tokenIter = aJCas.getAnnotationIndex(Token.type).iterator();

		//iterate over all tokens and count the number of syllables in each token
		while(tokenIter.hasNext()) {
			Token token = (Token) tokenIter.next();
			String tokenStr = token.getCoveredText();
			int tokenBegin = token.getBegin();
			int tokenEnd = token.getEnd();
			int syllableCount = 0; // for storing the number of syllables

			//skip punctuations
			if(token.getCoveredText().matches("\\p{Punct}")) {
				continue;
			}

			Iterator syllableIter = aJCas.getAnnotationIndex(Syllable.type).iterator();
			// iterate over all syllables 
			while(syllableIter.hasNext()) {
				Annotation syllableAnnotation = (Annotation) syllableIter.next();
				int syllableBegin = syllableAnnotation.getBegin();

				//count the number of syllable in the token
				if(syllableBegin >= tokenBegin && syllableBegin <= tokenEnd ) {
					syllableCount++;
				}
			}	

			//count total token and number of tokens with more than 2 syllables
			if(syllableCount >=2) {
				countToken2OrMoreSylla++;
			}

			countNToken++;

			//count total types and number of types with more than 2 syllables
			if(!typeSet.contains(tokenStr)) {
				countNType++;
				typeSet.add(tokenStr);
				if(syllableCount >=2) {
					countType2OrMoreSylla++;
				}
			}
		}

		//output the feature type
		Word2OrMoreSyllables annotation = new Word2OrMoreSyllables(aJCas);
		annotation.setId(aeID);
		if(numOrPercentage.equals("number")) {
			if(typeOrToken.equals("type")) {
				annotation.setValue(countType2OrMoreSylla);
			} else if(typeOrToken.equals("token")) {
				annotation.setValue(countToken2OrMoreSylla);
			}
		} else if (numOrPercentage.equals("percent")) {
			if(typeOrToken.equals("type")) {
				annotation.setValue((double)countType2OrMoreSylla / countNType);
			} else if(typeOrToken.equals("token")) {
				annotation.setValue((double)countToken2OrMoreSylla / countNToken);
			}
		}
		annotation.addToIndexes();

		logger.info(new PopulatedFeatureValueMessage(aeID, annotation.getValue()));
	}
}