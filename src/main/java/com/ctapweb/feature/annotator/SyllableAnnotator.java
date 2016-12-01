/**
 * 
 */
package com.ctapweb.feature.annotator;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.ctapweb.feature.type.Syllable;
import com.ctapweb.feature.type.Token;


/**
 * @author xiaobin
 * Annotates syllables in each token.
 */
public class SyllableAnnotator extends JCasAnnotator_ImplBase {

	private JCas aJCas;
	private Token token;

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.ANNOTATOR;
	private static final String aeName = "Syllable Annotator";

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

		this.aJCas = aJCas;

		// get annotation indexes and iterator
		Iterator it = aJCas.getAnnotationIndex(Token.type).iterator();

		//annotate syllables for each token 
		while(it.hasNext()) {
			//get the token
			token = (Token)it.next();
			String tokenStr = token.getCoveredText().toLowerCase();

			//annotate syllables
			//solution from http://stackoverflow.com/questions/33425070/how-to-calculate-syllables-in-text-with-regex-and-java
			if (tokenStr.charAt(tokenStr.length()-1) == 'e') {
				if (silente(tokenStr)){						//silent e, so don't annotate.  
					String newToken= tokenStr.substring(0, tokenStr.length()-1); //deal with the rest of word
					annotateSyllables(newToken);
				} else {
					//not silent e, annotate it as a syllable
					Syllable annotation = new Syllable(aJCas);
					annotation.setBegin(token.getBegin());
					annotation.setEnd(token.getEnd());
					annotation.addToIndexes();
//					logger.info("syllable: " + annotation.getBegin() + ", " + annotation.getEnd() + " "  + annotation.getCoveredText());
				}
			} else {
				annotateSyllables(tokenStr);
			}
		}
	}

	/**
	 * Annotates the syllables in the token string. 
	 * 
	 * @param tokenStr the token string to be annotated
	 * @return
	 */
	private void annotateSyllables(String tokenStr) {
		Pattern splitter = Pattern.compile("[^aeiouy]*[aeiouy]+");
		Matcher m = splitter.matcher(tokenStr);

		while (m.find()) {
			//finds a syllable
			Syllable annotation = new Syllable(aJCas);
			annotation.setBegin(m.start() + token.getBegin());
			annotation.setEnd(m.end() + token.getBegin());
			annotation.addToIndexes();
//			logger.info("syllable: " + annotation.getBegin() + ", " + annotation.getEnd() + " "  + annotation.getCoveredText());
		}
	}

	private boolean silente(String word) {
		word = word.substring(0, word.length()-1);

		Pattern yup = Pattern.compile("[aeiouy]");
		Matcher m = yup.matcher(word);

		return m.find() ? true: false;
	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));
		super.destroy();
		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}
}

