/**
 * 
 */
package com.ctapweb.feature.featureAE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
import com.ctapweb.feature.type.NSentence;
import com.ctapweb.feature.type.POS;
import com.ctapweb.feature.type.POSDensityPerSent;

public class POSDensityPerSentAE extends JCasAnnotator_ImplBase {

	// the analysis engine's id from the database
	// this value needs to be set when initiating the analysis engine
	public static final String PARAM_AEID = "aeID";
	public static final String PARAM_LEXICAL_TYPE = "POSType";
	private int aeID;
	boolean checkDefiniteArticle = false;
	boolean checkIndefiniteArticle = false;

	// list of pos tags to be counted
	String POSType;
	List<String> posList = new ArrayList<>();

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "POS Density per Sentence Feature Extractor";

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType,
				aeName));
		super.initialize(aContext);

		// get the parameter value of analysis id
		if (aContext.getConfigParameterValue(PARAM_AEID) == null) {
			ResourceInitializationException e = new ResourceInitializationException(
					"mandatory_value_missing", new Object[] { PARAM_AEID });
			logger.throwing(e);
			throw e;
		} else {
			aeID = (Integer) aContext.getConfigParameterValue(PARAM_AEID);
		}

		// get the lexical type mandatory pamameter value
		if (aContext.getConfigParameterValue(PARAM_LEXICAL_TYPE) == null) {
			ResourceInitializationException e = new ResourceInitializationException(
					"mandatory_value_missing",
					new Object[] { PARAM_LEXICAL_TYPE });
			logger.throwing(e);
			throw e;
		} else {
			POSType = (String) aContext
					.getConfigParameterValue(PARAM_LEXICAL_TYPE);
			posList = getPOSTagList(POSType);
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

		// get annotation indexes and iterator
		Iterator posIter = aJCas.getAnnotationIndex(POS.type).iterator();

		// count number of occurrences
		int nPOSTypes = 0;
		while (posIter.hasNext()) {
			POS pos = (POS) posIter.next();
			String tag = pos.getTag();
			if (tag.equals("DT")) {
				logger.trace(LogMarker.UIMA_MARKER, "tag equlal DET: " + tag);
				if (posList.contains(tag)) {
					logger.trace(LogMarker.UIMA_MARKER,
							"posList contains tag: " + tag);
					if (checkDefiniteArticle) {
						logger.trace(
								LogMarker.UIMA_MARKER,
								"in checkDefiniteArticle: "
										+ pos.getCoveredText());
						if (!pos.getCoveredText().equals("a")
								&& !pos.getCoveredText().equals("an")) {
							nPOSTypes++;
						}

					} else if (checkIndefiniteArticle) {
						logger.trace(
								LogMarker.UIMA_MARKER,
								"in checkIndefiniteArticle: "
										+ pos.getCoveredText());
						if (pos.getCoveredText().equals("a")
								|| pos.getCoveredText().equals("an")) {
							nPOSTypes++;
						}
					} else {
						nPOSTypes++;
					}
					// logger.trace(LogMarker.UIMA_MARKER,
					// "found a target tag: " +
					// tag);
				}
			} else {
				if (posList.contains(tag)) {
					nPOSTypes++;
					// logger.trace(LogMarker.UIMA_MARKER,
					// "found a target tag: " +
					// tag);
				}
			}
		}

		int nSentences = getNSentences(aJCas);
		double density = 0;
		logger.trace(LogMarker.UIMA_MARKER, "nPOSTypes " + nPOSTypes
				+ ", nSentences " + nSentences);
		if (nSentences != 0) {
			density = (double) nPOSTypes / nSentences;
		}

		// output the feature type
		POSDensityPerSent annotation = new POSDensityPerSent(aJCas);

		// set the feature ID
		annotation.setId(aeID);

		// set feature value
		annotation.setValue(density);
		annotation.addToIndexes();

		logger.info(new PopulatedFeatureValueMessage(aeID, density));
	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType,
				aeName));

		super.destroy();

		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(
				aeType, aeName));
	}

	// gets the parts of speech that this feature is looking for
	private List<String> getPOSTagList(String POSType) {
		List<String> tagList = new ArrayList<>();

		String[] lexical = { "JJ", "JJR", "JJS", // adj
				"RB", "RBR", "RBS", "WRB", // adv
				"VB", "VBD", "VBG", "VBN", "VBP", "VBZ", // verb
				"NN", "NNS", "NNP", "NNPS" // noun
		};
		String[] adjective = { "JJ", "JJR", "JJS" };
		String[] noun = { "NN", "NNS", "NNP", "NNPS" };
		String[] adverb = { "RB", "RBR", "RBS", "WRB" };
		String[] verb = { "VB", "VBD", "VBG", "VBN", "VBP", "VBZ" };
		String[] pronoun = { "PRP", "PRP$", "WP", "WP$" };
		String[] personalPronoun = { "PRP", "WP" };
		String[] possessivePronoun = { "PRP$", "WP$" };
		String[] whPronoun = { "WP" }; // ignoring WP$
		String[] article = { "DT" };

		switch (POSType) {
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
		case "pronoun":
			Collections.addAll(tagList, pronoun);
			break;
		case "personalPronoun":
			Collections.addAll(tagList, personalPronoun);
			break;
		case "possessivePronoun":
			Collections.addAll(tagList, possessivePronoun);
			break;
		case "whPronoun":
			Collections.addAll(tagList, whPronoun);
			break;
		case "definiteArticle":
			Collections.addAll(tagList, article);
			checkDefiniteArticle = true;
			break;
		case "indefiniteArticle":
			Collections.addAll(tagList, article);
			checkIndefiniteArticle = true;
			break;
		default:
			// deal with space separated POS type list
			String[] types = POSType.split("\\b");
			Collections.addAll(tagList, types);
		}

		return tagList;
	}

	// get number of tokens from the CAS
	private int getNSentences(JCas aJCas) {
		int n = 0;
		// Iterator posIter = aJCas.getAnnotationIndex(NToken.type).iterator();
		Iterator posIter = aJCas.getAllIndexedFS(NSentence.class);
		if (posIter.hasNext()) {
			NSentence nSent = (NSentence) posIter.next();
			n = (int) nSent.getValue();
		}
		return n;
	}

}
