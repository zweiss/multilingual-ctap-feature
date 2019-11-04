package com.ctapweb.feature.featureAE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
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
import com.ctapweb.feature.type.NTokenType;
import com.ctapweb.feature.type.HDD;
import com.ctapweb.feature.type.Token;
import com.ctapweb.feature.type.Lemma;

/**
 * 
 * @author Nadezda Okinina
 * 
 * The code for the calculation of the HD-D measure of lexical diversity
 * was translated in Java by Nadezda Okinina from the Python implementation of John Frens last updated on Apr 26, 2017: https://github.com/jfrens/lexical_diversity
 *	Here is a comment from John Frens's code:
 * In sum, all textual analyses are fraught with difficulty and disagreement, and LD is no exception.
 * There is no agreement in the field as to the form of processing (sequential or nonsequential) or the composition of lexical terms (e.g., words, lemmas, bigrams, etc.);
 * and even a common position with regard to the distinction between the terms lexical diversity, vocabulary diversity, and lexical richness remains unclear (Malvern et al., 2004).
 * In this study, we do not attempt to remedy these issues. Instead, we argue that the field is sufficiently young to be still in need of exploring its potential to inform substantially.
 * Thus, we include in our analyses the most sophisticated indices of LD that are currently available.
 */

public class HDDAE extends JCasAnnotator_ImplBase {
	public static final String PARAM_AEID = "aeID";
	public static final String PARAM_TOKENORLEMMA = "tokenlemma";
	private int aeID;

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "HDD Feature Extractor";

	//for storing the formula passed in from the descriptor
	private String tokenlemma;

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
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
		
		if(aContext.getConfigParameterValue(PARAM_TOKENORLEMMA) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_TOKENORLEMMA});
			logger.throwing(e);
			throw e;
		} else {
			tokenlemma = (String) aContext.getConfigParameterValue(PARAM_TOKENORLEMMA);
			//check if value is correct
			switch(tokenlemma) {
			case "lemma":
			case "token":
				break;
			default: 
				throw new ResourceInitializationException("annotator_parameter_not_valid", 
						new Object[] {tokenlemma,PARAM_TOKENORLEMMA});
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
		
		double hdd;
		Iterator it;
		Lemma lemma;
		Token token;
		ArrayList <String> tokensArray = new ArrayList <String>();
		
		// get annotation indexes and iterator
		if (tokenlemma.equals("token")){
			it = aJCas.getAnnotationIndex(Token.type).iterator();
			//logger.trace(LogMarker.UIMA_MARKER, "hdd for tokens"); 
			while(it.hasNext()) {
				token = (Token) it.next();
				//logger.trace(LogMarker.UIMA_MARKER, token.getCoveredText());
				if(token.getCoveredText().matches("\\p{Punct}")) {
					continue;
				} else {
					tokensArray.add(token.getCoveredText());
				}
			}
		}else{
			it = aJCas.getAnnotationIndex(Lemma.type).iterator();
			//logger.trace(LogMarker.UIMA_MARKER, "hdd for lemmas");
			while(it.hasNext()) {
				lemma = (Lemma) it.next();
				
				//logger.trace(LogMarker.UIMA_MARKER, lemma.getLemma());
				
				if(lemma.getLemma().matches("\\p{Punct}")) {
					continue;
				} else {
					tokensArray.add(lemma.getLemma());
				}
			}
		}
		
		if (tokensArray.size() < 50){
			hdd = -1.0;
		}else{
			double sampleSize = 42.0;
			hdd = hdd(tokensArray, sampleSize);
		}
		
		//output the feature type
		HDD annotation = new HDD(aJCas);
		//set the feature ID
		annotation.setId(aeID);

		//set feature value
		annotation.setValue(hdd);
		annotation.addToIndexes();

		logger.info(new PopulatedFeatureValueMessage(aeID, hdd));
	}
	
	private double hdd(ArrayList <String> tokensArray, double sampleSize){
		HashMap <String, Double> typeCounts = new HashMap  <String, Double> ();
		for(String token: tokensArray){
			if (typeCounts.containsKey(token)){
				typeCounts.replace(token, typeCounts.get(token)+1.0);
			}else{
				typeCounts.put(token, new Double(1.0));
			}
		}
		
		double hddValue = 0.0;
		double contribution;
		for (String type: typeCounts.keySet()){
			contribution = (1.0 - hypergeometric((double)tokensArray.size(), sampleSize, typeCounts.get(type), 0.0)) / sampleSize;
			hddValue += contribution;
		}
		
		return hddValue;
	}
	
	private double hypergeometric(double population, double populationSuccesses, double sample, double sampleSuccesses){
		return (combination(populationSuccesses, sampleSuccesses) *
	            combination(population - populationSuccesses, sample - sampleSuccesses)) /
	            combination(population, sample);
	}
	
	private double combination (double n, double  r){
		double rFact = factorial(r);
		double numerator = 1.0;
		double num = n - r + 1.0;
		while (num < n + 1.0){
			numerator *= num;
			num += 1.0;
		}
		return numerator / rFact;
	}
	
	private double factorial (double x){
		if (x <= 1){
			return 1;
		}else{
			return x * factorial(x - 1);
		}
	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));

		super.destroy();

		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}
}
