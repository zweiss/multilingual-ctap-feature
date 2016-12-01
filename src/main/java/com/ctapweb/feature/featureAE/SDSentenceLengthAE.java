/**
 * 
 */
package com.ctapweb.feature.featureAE;

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
import com.ctapweb.feature.logging.message.DestroyAECompleteMessage;
import com.ctapweb.feature.logging.message.DestroyingAEMessage;
import com.ctapweb.feature.logging.message.InitializeAECompleteMessage;
import com.ctapweb.feature.logging.message.InitializingAEMessage;
import com.ctapweb.feature.logging.message.PopulatedFeatureValueMessage;
import com.ctapweb.feature.logging.message.ProcessingDocumentMessage;
import com.ctapweb.feature.type.Letter;
import com.ctapweb.feature.type.SDSentenceLength;
import com.ctapweb.feature.type.Sentence;
import com.ctapweb.feature.type.Syllable;
import com.ctapweb.feature.type.Token;

/**
 * Calculates the sd of sentence length in tokens/syllables/letters, depending on the parameter setting.
 * @author xiaobin
 */
public class SDSentenceLengthAE extends JCasAnnotator_ImplBase {

	//the analysis engine's id from the database
	//this value needs to be set when initiating the analysis engine
	public static final String PARAM_AEID = "aeID";
	public static final String PARAM_UNIT = "unit"; //counting unit: token/syllable/letter
	private int aeID;
	private String unit = null;


	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "SDSentenceLength Feature Extractor";

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

		double sdSentLength;

		// Get a DescriptiveStatistics instance
		DescriptiveStatistics stats = new DescriptiveStatistics();

		// get sentence annotation iterator
		Iterator sentIter = aJCas.getAnnotationIndex(Sentence.type).iterator();

		//iterate over all sentences and count the number of tokens in each sentence.
		while(sentIter.hasNext()) {
			Sentence sent = (Sentence) sentIter.next();
			int sentBegin = sent.getBegin();
			int sentEnd = sent.getEnd();
			int unitCount = 0; // for storing the number of entities (tokens/letters/syllables)

			//get unit annotations
			Iterator unitIter = null;
			switch(unit) {
			case "token":
				unitIter = aJCas.getAnnotationIndex(Token.type).iterator();
				break;
			case "syllable":
				unitIter = aJCas.getAnnotationIndex(Syllable.type).iterator();
				break;
			case "letter":
				unitIter = aJCas.getAnnotationIndex(Letter.type).iterator();
				break;
			}

			// iterate over all units 
			while(unitIter.hasNext()) {
				Annotation anno = (Annotation) unitIter.next();
				String annoStr = anno.getCoveredText();
				int unitBegin = anno.getBegin();

				//skip punctuations
				if(annoStr.matches("\\p{Punct}")) {
					continue;
				}

				//count the number of tokens/syllable/letters in the sentence
				if(unitBegin >= sentBegin && unitBegin <sentEnd ) {
					unitCount++;
				}
			}	

			stats.addValue(unitCount);

		}

		sdSentLength = stats.getStandardDeviation();

		//output the feature type
		SDSentenceLength annotation = new SDSentenceLength(aJCas);
		annotation.setId(aeID);
		annotation.setValue(sdSentLength);
		annotation.addToIndexes();

		logger.info(new PopulatedFeatureValueMessage(aeID, sdSentLength));
	}
	
	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));

		super.destroy();

		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}

}