/**
 * 
 */
package com.ctapweb.feature.featureAE;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.ctapweb.feature.type.NSyntacticConstituent;
import com.ctapweb.feature.type.NToken;
import com.ctapweb.feature.type.ParseTree;
import com.ctapweb.feature.type.Sentence;
import com.ctapweb.feature.type.SyntacticComplexity;
import com.ctapweb.feature.type.Token;

import edu.stanford.nlp.trees.PennTreeReader;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeReader;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;


public class SyntacticComplexityAE extends JCasAnnotator_ImplBase {

	//the analysis engine's id from the database
	//this value needs to be set when initiating the analysis engine
	public static final String PARAM_AEID = "aeID";
	public static final String PARAM_NUMERATOR = "numerator"; 
	public static final String PARAM_DENOMINATOR = "denominator";

	private int aeID;

	//syntactic complexity usually takes the form of 
	// # of structures / # production units
	//so it involves a numerator and denominator
	//the numerator can only be one of:
	//	nToken		number of tokens
	//	nC		number of clauses
	//	nCT		number of complex T-units
	//	nDC		number of dependent clauses
	//	nCP		number of coordinate phrases
	//	nT		number of T-units
	//	nCN		number of complex nominals
	//	nVP		number of verb phrases
	//the denominator can only be one of:
	//		nC		number of clauses
	//		nT		number of T-units
	//		nS		number of sentences
	private String numeratorStr;
	private String denominatorStr;

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "SyntacticComplexity Feature Extractor";

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));
		super.initialize(aContext);

		// get the pamameters
		//get the parameter value of analysis id
		if(aContext.getConfigParameterValue(PARAM_AEID) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_AEID});
			logger.throwing(e);
			throw e;
		} else {
			aeID = (Integer) aContext.getConfigParameterValue(PARAM_AEID);
		}

		//get the numerator 
		if(aContext.getConfigParameterValue(PARAM_NUMERATOR) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_NUMERATOR});
			logger.throwing(e);
			throw e;
		} else {
			numeratorStr = (String) aContext.getConfigParameterValue(PARAM_NUMERATOR);
		}

		//get denominator
		if(aContext.getConfigParameterValue(PARAM_DENOMINATOR) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_DENOMINATOR});
			logger.throwing(e);
			throw e;
		} else {
			denominatorStr = (String) aContext.getConfigParameterValue(PARAM_DENOMINATOR);
		}

		logger.trace(LogMarker.UIMA_MARKER, "Formula to use: {}/{}", numeratorStr, denominatorStr);
		logger.trace(LogMarker.UIMA_MARKER, new InitializeAECompleteMessage(aeType, aeName));
	}

	/* (non-Javadoc)
	 * @see org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
	 */
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		logger.trace(LogMarker.UIMA_MARKER, 
				new ProcessingDocumentMessage(aeType, aeName, aJCas.getDocumentText()));

		double numeratorVal = 0;
		double denominatorVal = 0;

		Iterator it;
		//if the numerator is nToken
		if(numeratorStr.equals("nToken")) {
			it = aJCas.getAllIndexedFS(NToken.class);
			if(it.hasNext()) {
				NToken nToken = (NToken)it.next();
				numeratorVal = nToken.getValue();
			}
		} 

		// get annotation indexes and iterator
		it = aJCas.getAllIndexedFS(NSyntacticConstituent.class);
		while(it.hasNext()) {
			NSyntacticConstituent nSyntacticConstituent = (NSyntacticConstituent)it.next();
			String constituentType = "n" + nSyntacticConstituent.getContituentType();

			//get the numerator and denominator values
			if(constituentType.equals(numeratorStr)) {
				numeratorVal = nSyntacticConstituent.getValue();
			} else if(constituentType.equals(denominatorStr)) {
				denominatorVal = nSyntacticConstituent.getValue();
			}
		}

		logger.trace(LogMarker.UIMA_MARKER, "The syntactic complexity: {} / {}", numeratorVal, denominatorVal);

		//output the feature type
		double synComplexity = 0;
		if(denominatorVal != 0) {
			synComplexity = numeratorVal / denominatorVal;
		}

		SyntacticComplexity annotation = new SyntacticComplexity(aJCas);

		//set the feature ID and feature value
		annotation.setId(aeID);
		annotation.setValue(synComplexity);
		annotation.addToIndexes();

		logger.info(new PopulatedFeatureValueMessage(aeID, synComplexity));
	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));

		super.destroy();

		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}
}
