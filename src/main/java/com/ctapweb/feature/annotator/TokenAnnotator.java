/**
 * 
 */
package com.ctapweb.feature.annotator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

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

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

/**
 * @author xiaobin The AE takes a Sentence as input and tokenize the sentence.
 */
public class TokenAnnotator extends JCasAnnotator_ImplBase {

	private InputStream modelIn;
	private TokenizerModel model;
	private opennlp.tools.tokenize.Tokenizer tokenizer;

	public static String RESOURCE_KEY = "TokenModel";

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.ANNOTATOR;
	private static final String aeName = "Token Annotator";

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));
		super.initialize(aContext);

		String modelFilePath = null;

		try {
			modelFilePath = getContext().getResourceFilePath(RESOURCE_KEY);

			logger.trace(LogMarker.UIMA_MARKER, 
					new LoadLangModelMessage(RESOURCE_KEY, modelFilePath));

			modelIn = getContext().getResourceAsStream(RESOURCE_KEY);
			model = new TokenizerModel(modelIn);
			tokenizer = new TokenizerME(model);
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
			Span[] spans = tokenizer.tokenizePos(sent.getCoveredText());

			for (Span span : spans) {
				Token annotation = new Token(aJCas);
				annotation.setBegin(span.getStart() + sent.getBegin()); // the offset is absolute, so adds the sentence begin position.
				annotation.setEnd(span.getEnd() + sent.getBegin());
				annotation.addToIndexes();
			}
		}

	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));

		if (modelIn != null) {
			try {
				modelIn.close();
			}
			catch (IOException e) {
				logger.throwing(e);
			}
		}
		super.destroy();
		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}

}
