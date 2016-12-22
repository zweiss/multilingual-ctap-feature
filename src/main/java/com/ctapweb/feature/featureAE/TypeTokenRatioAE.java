/**
 * 
 */
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
import com.ctapweb.feature.type.NToken;
import com.ctapweb.feature.type.NTokenType;
import com.ctapweb.feature.type.TTR;
import com.ctapweb.feature.type.TokenType;


public class TypeTokenRatioAE extends JCasAnnotator_ImplBase {

	//	private int nTypes = 0;

	//the analysis engine's id from the database
	//this value needs to be set when initiating the analysis engine
	public static final String PARAM_AEID = "aeID";
	public static final String PARAM_FORMULA = "formula";

	private int aeID;

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "Type Token Ratio Feature Extractor";

	//for storing the formula passed in from the descriptor
	private String formula;

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

		//get the parameter value of formula, which can be one of the following:
		//		TTR		T/N
		//		CTTR	T/sqrt(2*N)
		//		RTTR	T/sqrt(N)
		//		LogTTR	LogT/LogN
		//		Uber		(LogN)^2/Log(N/T)
		// The formula decides how the TTR is culculated
		if(aContext.getConfigParameterValue(PARAM_FORMULA) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_FORMULA});
			logger.throwing(e);
			throw e;
		} else {
			formula = (String) aContext.getConfigParameterValue(PARAM_FORMULA);
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

		double nToken = 0; //number of tokens
		double nType = 0; //number of types
		double ttr = 0; // TTR value
		
		Iterator nTokenIter = aJCas.getAllIndexedFS(NToken.class);
		Iterator nTypeIter = aJCas.getAllIndexedFS(NTokenType.class);

		if(nTokenIter.hasNext()) {
			nToken = ((NToken) nTokenIter.next()).getValue();
		}
		
		if(nTypeIter.hasNext()) {
			nType = ((NTokenType) nTypeIter.next()).getValue();
		}

		logger.trace(LogMarker.UIMA_MARKER, "nType = {}, nToken = {} ", nType, nToken);
		
		switch (formula) {
		case "TTR":
			ttr = nType / nToken; break;
		case "CTTR":
			ttr = nType / Math.sqrt(2 * nToken); break;
		case "RTTR":
			ttr = nType / Math.sqrt(nToken); break;
		case "LogTTR":
			ttr = Math.log(nType) / Math.log(nToken); break;
		case "Uber":
			ttr = Math.pow(Math.log(nType), 2) / Math.log(nToken / nType); break;
		}
		
		logger.trace(LogMarker.UIMA_MARKER, "TTR calculated with formula ({}): {} ", formula, ttr);

		//output the feature type
		TTR annotation = new TTR(aJCas);
		//set the feature ID
		annotation.setId(aeID);

		//set feature value
		annotation.setValue(ttr);
		annotation.addToIndexes();

		logger.info(new PopulatedFeatureValueMessage(aeID, ttr));
	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));

		super.destroy();

		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}
}
