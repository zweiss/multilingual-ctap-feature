/**
 * 
 */
package com.ctapweb.feature.annotator;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;

import opennlp.tools.util.Span;

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
import com.ctapweb.feature.logging.message.ProcessingDocumentMessage;
import com.ctapweb.feature.type.Token;
import com.ctapweb.feature.type.TokenType;


/**
 * Populates the CAS with token types, ignoring repetitive tokens.
 * Requires the following annotations: sentences, tokens (see TokenTypeAnnotatorTAE.xml)
 * 
 * @author xiaobin
 */
public class TokenTypeAnnotator extends JCasAnnotator_ImplBase {

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.ANNOTATOR;
	private static final String aeName = "Token Type Annotator";

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));
		super.initialize(aContext);
		logger.trace(LogMarker.UIMA_MARKER, new InitializeAECompleteMessage(aeType, aeName));
	}

	/* (non-Javadoc)
	 * @see org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
	 */
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		logger.trace(LogMarker.UIMA_MARKER, 
				new ProcessingDocumentMessage(aeType, aeName, aJCas.getDocumentText()));

		// get annotation indexes and iterator
		Iterator tokenIter = aJCas.getAnnotationIndex(Token.type).iterator();

		//create a set for storing types
		HashSet<String> tTypes = new HashSet<String>();

		while(tokenIter.hasNext()) {
			Token token = (Token)tokenIter.next();
			// TODO LCA : decide if we want to make words lower case
			String tokenStr = token.getCoveredText().toLowerCase();
			// TODO LCA : decide if we want to keep this equivalence mapping
			tokenStr = tokenStr.replace("ö", "oe");
			tokenStr = tokenStr.replace("ü", "ue");
			tokenStr = tokenStr.replace("ä", "ae");
			tokenStr = tokenStr.replace("ß", "ss");

			// ignore punctuation marks
			if(!Pattern.matches("\\p{Punct}", tokenStr) && !tTypes.contains(tokenStr)) {
				tTypes.add(tokenStr);
				TokenType annotation  = new TokenType(aJCas);
				annotation.setWordString(tokenStr);
				annotation.addToIndexes();
				//logger.info("tType: " + tokenStr);
				//System.out.println("tType: " + tokenStr);
			}
		}
	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));
		super.destroy();
		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}

}
