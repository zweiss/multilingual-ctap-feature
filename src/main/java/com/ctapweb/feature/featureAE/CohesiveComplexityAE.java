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
import com.ctapweb.feature.type.CohesiveComplexity;
import com.ctapweb.feature.type.NConnectives;
import com.ctapweb.feature.type.NToken;



/**
 * Calculates cohesive complexity measures, momentarily only connectives, will in later stages maybe expanded to
 * also calculate other cohesion related ratios
 * @author zweiss
 */
public class CohesiveComplexityAE extends JCasAnnotator_ImplBase {

	//the analysis engine's id from the database
	//this value needs to be set when initiating the analysis engine
	public static final String PARAM_AEID = "aeID";
	public static final String PARAM_NUMERATOR = "numerator"; 
	public static final String PARAM_DENOMINATOR = "denominator";

	private int aeID;
	private String numeratorStr;
	private String denominatorStr;

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "CohesiveComplexity Feature Extractor";

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
		if(denominatorStr.equals("nWords")) {  
			it = aJCas.getAllIndexedFS(NToken.class);
			if(it.hasNext()) {
				NToken nToken = (NToken)it.next();
				denominatorVal = nToken.getValue();
			}
		} 

		// get annotation indexes and iterator
		it = aJCas.getAllIndexedFS(NConnectives.class);
		while(it.hasNext()) {
			NConnectives nConnectives = (NConnectives)it.next();
			String connectiveType = "n" + nConnectives.getConnectiveType();

			//get the numerator and denominator values
			if(connectiveType.equals(numeratorStr)) {
				numeratorVal = nConnectives.getValue();
			} else if(connectiveType.equals(denominatorStr)) {
				denominatorVal = nConnectives.getValue();
			}
		}

		logger.trace(LogMarker.UIMA_MARKER, "The cohesive complexity {}/{}: {} / {}", numeratorStr, denominatorStr, numeratorVal, denominatorVal);

		//output the feature type
		double cohComplexity = 0.0;
		if(denominatorVal > 0.0) {
			cohComplexity = numeratorVal / denominatorVal;
		}

		CohesiveComplexity annotation = new CohesiveComplexity(aJCas);

		//set the feature ID and feature value
		annotation.setId(aeID);
		annotation.setValue(cohComplexity);
		annotation.addToIndexes();

		logger.info(new PopulatedFeatureValueMessage(aeID, cohComplexity));
	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));

		super.destroy();

		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}
}
