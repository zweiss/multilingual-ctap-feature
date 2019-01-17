/**
 * 
 */
package com.ctapweb.feature.annotator;

import java.util.Iterator;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.ctapweb.feature.logging.message.SelectingLanguageSpecificResource;
import com.ctapweb.feature.type.Syllable;
import com.ctapweb.feature.type.Token;
import com.ctapweb.feature.util.SupportedLanguages;


/**
 * @author xiaobin
 * Annotates syllables in each token.
 * 
 * zweiss 20/12/18 : added new syllable structures
 */
public class SyllableAnnotator extends JCasAnnotator_ImplBase {

	private JCas aJCas;
	private Token token;
	private String syllablePattern;
	private boolean considerSilentE;

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.ANNOTATOR;
	private static final String aeName = "Syllable Annotator";

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));
		super.initialize(aContext);
		
		// obtain language parameter and access language dependent resources
		Optional<String> lCode = Optional.ofNullable((String) aContext.getConfigParameterValue("LanguageCode"));
		logger.trace(LogMarker.UIMA_MARKER, new SelectingLanguageSpecificResource(aeName, lCode.orElse(SupportedLanguages.DEFAULT)));
		switch (lCode.orElse(SupportedLanguages.DEFAULT)) {
		case SupportedLanguages.GERMAN:
			syllablePattern = SyllablePatterns.GERMAN;
			considerSilentE = false;
			break;
		case SupportedLanguages.ENGLISH:
			syllablePattern = SyllablePatterns.ENGLISH;
			considerSilentE = true;
			break;
		default:  // default to English analysis
			syllablePattern = SyllablePatterns.DEFAULT;
			considerSilentE = true;
			break;
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
			if (considerSilentE && tokenStr.charAt(tokenStr.length()-1) == 'e') {
				if (silente(tokenStr)){  //silent e, so don't annotate.  
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
		Pattern splitter = Pattern.compile(syllablePattern);
		Matcher m = splitter.matcher(tokenStr);

		while (m.find()) {
			//finds a syllable
			Syllable annotation = new Syllable(aJCas);
			annotation.setBegin(m.start() + token.getBegin());
			annotation.setEnd(m.end() + token.getBegin());
			annotation.addToIndexes();
			// TODO LCA: comment this away again
			logger.info("syllable: " + annotation.getBegin() + ", " + annotation.getEnd() + " \'"  + annotation.getCoveredText()+"\'");
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

	/*
	 * Defines syllable patterns which may be chosen upon initialization based on the language parameter transported in the UimaContext
	 * @author zweiss
	 * TODO LCA : is this a good choice?
	 */
	public class SyllablePatterns {
		public static final String ENGLISH = "[^aeiouy]*[aeiouy]+";
		// German syllables: each vowel indicates its own syllable unless it is followed by a) itself or b) e i u y
		public static final String GERMAN = "[^aeiouöüäAEIOUÖÜÄ]*([aeiouöüäyAEIOUÖÜÄY])([eiuy]|\1)?[^aeiouöüäAEIOUÖÜÄ]*";  
		// default
		public static final String DEFAULT = SyllablePatterns.ENGLISH;
	}
}

