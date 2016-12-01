/**
 * 
 */
package com.ctapweb.feature.annotator;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.uima.UIMAException;
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

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

/**
 * Annotates the sentences in a text.
 * 
 * @author xiaobin
 */
public class SentenceAnnotator extends JCasAnnotator_ImplBase {

	private InputStream modelIn;
	private SentenceDetectorME sentenceDetector;

	private static final String RESOURCE_KEY = "SentenceSegmenterModel";

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.ANNOTATOR;
	private static final String aeName = "Sentence Annotator";
	/**
	 * Loads the English sentence detector model.
	 */
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));

		super.initialize(aContext);

		String modelFilePath = null;

		// gets the model resource, which is declared in the annotator xml
		try {
			modelFilePath = getContext().getResourceFilePath(RESOURCE_KEY);

			logger.trace(LogMarker.UIMA_MARKER, 
					new LoadLangModelMessage(RESOURCE_KEY, modelFilePath));

			modelIn = getContext().getResourceAsStream(RESOURCE_KEY);
			sentenceDetector = new SentenceDetectorME(new SentenceModel(modelIn));
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
		// Get document text
		String docText = aJCas.getDocumentText();

		logger.trace(LogMarker.UIMA_MARKER, 
				new ProcessingDocumentMessage(aeType, aeName, docText));

		// Detect sentence spans
		Span[] spans = sentenceDetector.sentPosDetect(docText);

		for (Span span : spans) {
			Sentence annotation = new Sentence(aJCas);
			// System.out.println("begin: " + span.getStart() +" end: " +
			// span.getEnd());
			annotation.setBegin(span.getStart());
			annotation.setEnd(span.getEnd());
			annotation.addToIndexes();
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
