/**
 * 
 */
package com.ctapweb.feature.annotator;

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
import com.ctapweb.feature.logging.message.ProcessingDocumentMessage;
import com.ctapweb.feature.type.Letter;
import com.ctapweb.feature.type.Token;

//This is new change

/**
 * @author xiaobin The AE takes a Token as input and annotates the
 *         non-punctuation letters of each token.
 */
public class LetterAnnotator extends JCasAnnotator_ImplBase {

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.ANNOTATOR;
	private static final String aeName = "Letter Annotator";

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType,
				aeName));
		super.initialize(aContext);
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

		// get annotation iterator
		Iterator tokenIter = aJCas.getAnnotationIndex(Token.type).iterator();

		while (tokenIter.hasNext()) {
			Token token = (Token) tokenIter.next();

			String tokenStr = token.getCoveredText();
			int tokenBegin = token.getBegin();
			int tokenEnd = token.getEnd();

			// iterate through the letters in the token
			for (int i = 0; i < tokenStr.length(); i++) {
				String character = tokenStr.substring(i, i + 1);
				if (character.matches("\\p{Punct}")) {
					continue;
				} else {
					// annotate the letter
					Letter annotation = new Letter(aJCas);
					annotation.setBegin(tokenBegin + i);
					annotation.setEnd(tokenBegin + i + 1);
					annotation.addToIndexes();
					// logger.info("Letter: " + annotation.getCoveredText());
				}
			}
		}
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
