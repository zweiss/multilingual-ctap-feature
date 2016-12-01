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
import com.ctapweb.feature.type.ComplexityFeatureBase;
import com.ctapweb.feature.type.MeanSentenceLength;
import com.ctapweb.feature.type.NLetter;
import com.ctapweb.feature.type.NSentence;
import com.ctapweb.feature.type.NSyllable;
import com.ctapweb.feature.type.NToken;

/**
 * @author xiaobin
 * Calculates the average sentence length in tokens/syllables/letters, depending on the parameter setting.
 */
public class MeanSentenceLengthAE extends JCasAnnotator_ImplBase {

	//the analysis engine's id from the database
	//this value needs to be set when initiating the analysis engine
	public static final String PARAM_AEID = "aeID";
	public static final String PARAM_UNIT = "unit"; //counting unit: token/syllable/letter
	private int aeID;
	private String unit = null;

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "MeanSentenceLength Feature Extractor";

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

		//get the unit parameter value
		if(aContext.getConfigParameterValue(PARAM_UNIT) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_UNIT});
			logger.throwing(e);
			throw e;
		} else {
			unit = (String) aContext.getConfigParameterValue(PARAM_UNIT);

			//check if unit value is correct
			switch(unit) {
			case "token":
			case "syllable":
			case "letter":
				break;
			default: 
				throw new ResourceInitializationException("annotator_parameter_not_valid", 
						new Object[] {unit, PARAM_UNIT});
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
		
		double nSentence = 0; //number of sentences
		double entityCount = 0; //number of tokens/syllables/letters
		double meanSentLength = 0;

		//get number of sentences
		Iterator it = aJCas.getAllIndexedFS(NSentence.class);
		if(it.hasNext()) {
			nSentence = ((NSentence) it.next()).getValue();
		}

		switch(unit) {
		case "token": 
			it = aJCas.getAllIndexedFS(NToken.class);
			break;
		case "syllable":
			it = aJCas.getAllIndexedFS(NSyllable.class);
			break;
		case "letter":
			it = aJCas.getAllIndexedFS(NLetter.class);
			break;
		}

		if(it.hasNext()) {
			entityCount = ((ComplexityFeatureBase)it.next()).getValue();
		}

		if(nSentence != 0) {
			meanSentLength = entityCount / nSentence;
		}

		//		logger.info("NSentence: " + nSentence);
		//		logger.info("entityCount: " + entityCount);
		//output the feature type
		MeanSentenceLength annotation = new MeanSentenceLength(aJCas);

		annotation.setId(aeID);
		annotation.setValue(meanSentLength);
		annotation.addToIndexes();

		logger.info(new PopulatedFeatureValueMessage(aeID, meanSentLength));
	}
	
	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));

		super.destroy();

		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}

}