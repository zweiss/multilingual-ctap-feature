/**
 * 
 */
package com.ctapweb.feature.featureAE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
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
import com.ctapweb.feature.type.ComplexityFeatureBase;
import com.ctapweb.feature.type.LexicalSophistication;
import com.ctapweb.feature.type.NToken;
import com.ctapweb.feature.type.Token;
import com.ctapweb.feature.util.LookUpTableResource;

public class LexicalFrequencyProfileAE extends JCasAnnotator_ImplBase {

	// the analysis engine's id from the database
	// this value needs to be set when initiating the analysis engine
	public static final String PARAM_AEID = "aeID";
	public static final String PARAM_TYPE = "type";
	public static final String RESOURCE_KEY = "lookUpTable";
	public static final String PARAM_BANDNUM = "bandnum";

	private int aeID;
	private boolean type = false; // whether to count word types or tokens:
									// "true" means to calculate word types
									// instead of tokens
	private LookUpTableResource lookUpTable;
	private Integer bandnum = null;

	// list of pos tags to be included in the calculation, depending on scope
	// setting
	List<String> posList = new ArrayList<>();

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "Lexical Sophistication Feature Extractor";

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType,
				aeName));
		super.initialize(aContext);

		// get the optional pamameter of "type" (boolean)
		if (aContext.getConfigParameterValue(PARAM_TYPE) != null) {
			type = (Boolean) aContext.getConfigParameterValue(PARAM_TYPE);
		}

		// get the parameter value of analysis id
		if (aContext.getConfigParameterValue(PARAM_AEID) == null) {
			ResourceInitializationException e = new ResourceInitializationException(
					"mandatory_value_missing", new Object[] { PARAM_AEID });
			logger.throwing(e);
			throw e;
		} else {
			aeID = (Integer) aContext.getConfigParameterValue(PARAM_AEID);
		}

		// get the parameter value of bandnum
		if (aContext.getConfigParameterValue(PARAM_BANDNUM) == null) {
			ResourceInitializationException e = new ResourceInitializationException(
					"mandatory_value_missing", new Object[] { PARAM_BANDNUM });
			logger.throwing(e);
			throw e;
		} else {
			bandnum = (Integer) aContext.getConfigParameterValue(PARAM_BANDNUM);
		}

		// get the lookup table
		try {
			lookUpTable = (LookUpTableResource) aContext
					.getResourceObject(RESOURCE_KEY);
		} catch (ResourceAccessException e) {
			logger.throwing(e);
			throw new ResourceInitializationException(e);
		}

		logger.trace(LogMarker.UIMA_MARKER, new InitializeAECompleteMessage(
				aeType, aeName));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org
	 * .apache.uima.jcas.JCas)
	 */
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		logger.trace(LogMarker.UIMA_MARKER, new ProcessingDocumentMessage(
				aeType, aeName, aJCas.getDocumentText()));

		double percentage = 0;
		double numTokens = 0;
		double numTokensInBand = 0;

		Iterator tokenNumIter = aJCas.getAllIndexedFS(NToken.class);
		if (tokenNumIter.hasNext()) {
			numTokens = ((ComplexityFeatureBase) tokenNumIter.next())
					.getValue();
		}

		// get annotation indexes and iterator
		Iterator tokenIter = aJCas.getAnnotationIndex(Token.type).iterator();

		while (tokenIter.hasNext()) {
			Token tok = (Token) tokenIter.next();
			String word = tok.getCoveredText().toLowerCase();
			if (lookUpTable.containsWord(word)) {
				numTokensInBand++;
			}

		}

		logger.trace(
				LogMarker.UIMA_MARKER,
				"Calculated lfp for band {}: {} percent of in total {} tokens are in the considered band.",
				bandnum, percentage, numTokens);

		// output the feature type
		LexicalSophistication annotation = new LexicalSophistication(aJCas);

		// set the feature ID
		annotation.setId(aeID);

		// set feature value
		annotation.setValue(percentage);
		annotation.addToIndexes();

		logger.info(new PopulatedFeatureValueMessage(aeID, percentage));
	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType,
				aeName));

		super.destroy();

		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(
				aeType, aeName));
	}

}
