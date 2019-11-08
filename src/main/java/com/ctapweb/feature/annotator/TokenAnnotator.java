/**
 * 
 */
package com.ctapweb.feature.annotator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;
import org.apache.uima.resource.ResourceInitializationException;

import com.ctapweb.feature.exception.CTAPException;
import com.ctapweb.feature.logging.LogMarker;
import com.ctapweb.feature.logging.message.AEType;
import com.ctapweb.feature.logging.message.DestroyAECompleteMessage;
import com.ctapweb.feature.logging.message.DestroyingAEMessage;
import com.ctapweb.feature.logging.message.InitializeAECompleteMessage;
import com.ctapweb.feature.logging.message.InitializingAEMessage;
import com.ctapweb.feature.logging.message.LoadLangModelMessage;
import com.ctapweb.feature.logging.message.ProcessingDocumentMessage;
import com.ctapweb.feature.type.Sentence;
import com.ctapweb.feature.type.Token;
import com.ctapweb.feature.util.SupportedLanguages;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

/**
 * Annotates text with tokens for each sentence in the input text
 * Requires the following annotations: sentences (see TokenAnnotatorTAE.xml)
 * 
 * Tokenization is done using the CTAPTokenizer interface. 
 * To add a new tokenizer, make sure to implement the CTAPTokenizer interface.
 * 
 * @author xiaobin 
 * 
 * Change log:
 * zweiss 19/01/10:	added CTAPTokenizer class
 * zweiss 18/12/18:	switch between resource keys based on UimaContext information on language
 */
public class TokenAnnotator extends JCasAnnotator_ImplBase {

	private CTAPTokenizer tokenizer;

	public static String RESOURCE_KEY = "TokenModel";

	private static final String PARAM_LANGUAGE_CODE = "LanguageCode";
	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.ANNOTATOR;
	private static final String aeName = "Token Annotator";

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));
		super.initialize(aContext);

		String modelFilePath = null;
		
		// define the model to be loaded based on the mandatory LanguageCode config parameter
		String lCode = "";
		if(aContext.getConfigParameterValue(PARAM_LANGUAGE_CODE) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_LANGUAGE_CODE});
			logger.throwing(e);
			throw e;
		} else {
			lCode = ((String) aContext.getConfigParameterValue(PARAM_LANGUAGE_CODE)).toUpperCase();
		}

		String languageSpecificResourceKey = RESOURCE_KEY+lCode;
		try {
			modelFilePath = getContext().getResourceFilePath(languageSpecificResourceKey);

			logger.trace(LogMarker.UIMA_MARKER, 
					new LoadLangModelMessage(languageSpecificResourceKey, modelFilePath));

			tokenizer = new OpenNLPTokenizer(modelFilePath);
			// add switch statement here to allow for different instantiations; see example in ParseTreeAnnotator.java

		} catch (ResourceAccessException e) {
			logger.throwing(e);
			throw new ResourceInitializationException("could_not_access_data",
					new Object[] {modelFilePath}, e);
		} catch (InvalidFormatException e) {
			logger.throwing(e);
			throw new ResourceInitializationException(CTAPException.EXCEPTION_DIGEST, 
					"incorrect_lang_model_format",
					new Object[] {modelFilePath}, e);
		} catch (IOException e) {
			logger.throwing(e);
			throw new ResourceInitializationException(CTAPException.EXCEPTION_DIGEST, 
					"file_io_error",
					new Object[] {modelFilePath}, e);
		} 

		logger.trace(LogMarker.UIMA_MARKER, new InitializeAECompleteMessage(aeType, aeName));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org.
	 * apache.uima.jcas.JCas)
	 */
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		logger.trace(LogMarker.UIMA_MARKER, 
				new ProcessingDocumentMessage(aeType, aeName, aJCas.getDocumentText()));

		// get annotation indexes and iterator
		Iterator sentIter = aJCas.getAnnotationIndex(Sentence.type).iterator();

		while (sentIter.hasNext()) {
			Sentence sent = (Sentence) sentIter.next();

			// Detect token spans
			Span[] spans = tokenizer.tokenize(sent.getCoveredText());

			for (Span span : spans) {
				Token annotation = new Token(aJCas);
				annotation.setBegin(span.getStart() + sent.getBegin()); // the offset is absolute, so adds the sentence begin position.
				annotation.setEnd(span.getEnd() + sent.getBegin());
				annotation.addToIndexes();
//				//logger.info("token: " + annotation.getBegin() + ", " + annotation.getEnd() + " "  + annotation.getCoveredText());
				//System.out.println(" token: " + annotation.getCoveredText());
			}
		}

	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));
		super.destroy();
		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}

	/**
	 * Interface for tokenizer; acts as wrapper for any tokenizer that may be 
	 * added to support new languages or to change existing parsing components.
	 * @author zweiss
	 */
	interface CTAPTokenizer {
		abstract Span[] tokenize(String sentence);
	}

	/**
	 * Wrapper for use of OpenNLP tokenizer (https://opennlp.apache.org/)
	 * @author zweiss
	 *
	 */
	private class OpenNLPTokenizer implements CTAPTokenizer {

		private InputStream modelIn;
		private TokenizerModel openNlpModel;
		private opennlp.tools.tokenize.Tokenizer openNlpTokenizer;

		public OpenNLPTokenizer(String modelInFile) throws FileNotFoundException, IOException {
			modelIn = new FileInputStream(new File(modelInFile));
			openNlpModel = new TokenizerModel(modelIn);
			openNlpTokenizer = new TokenizerME(openNlpModel);
			modelIn.close();
		}

		@Override
		public Span[] tokenize(String sentence) {
			return openNlpTokenizer.tokenizePos(sentence);
		}
		
	}
}
