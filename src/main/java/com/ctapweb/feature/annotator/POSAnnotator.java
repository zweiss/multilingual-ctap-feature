package com.ctapweb.feature.annotator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.ctapweb.feature.exception.CTAPException;
import com.ctapweb.feature.logging.LogMarker;
import com.ctapweb.feature.logging.message.AEType;
import com.ctapweb.feature.logging.message.DestroyAECompleteMessage;
import com.ctapweb.feature.logging.message.DestroyingAEMessage;
import com.ctapweb.feature.logging.message.InitializeAECompleteMessage;
import com.ctapweb.feature.logging.message.InitializingAEMessage;
import com.ctapweb.feature.logging.message.LoadLangModelMessage;
import com.ctapweb.feature.logging.message.ProcessingDocumentMessage;
import com.ctapweb.feature.type.POS;
import com.ctapweb.feature.type.Sentence;
import com.ctapweb.feature.type.Token;
import com.ctapweb.feature.util.TokenComparator;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

public class POSAnnotator extends JCasAnnotator_ImplBase {

	//for tokenizer
//	private InputStream tokenModelIn;
//	private TokenizerModel tokenizerModel;
//	private opennlp.tools.tokenize.Tokenizer tokenizer;
//	public static String TOKEN_RESOURCE_KEY = "TokenModel";

	//for pos tagger
	private InputStream posModelIn;
	private POSModel POSmodel;
	private POSTaggerME posTagger;
	public static String POS_RESOURCE_KEY = "POSModel";

	private static final Logger logger = LogManager.getLogger();

	private static final AEType aeType = AEType.ANNOTATOR;
	private static final String aeName = "POS Annotator";

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));
		super.initialize(aContext);

//		String tokenModelFilePath = null;
		String POSModelFilePath = null;

//		//init tokenizer
//		try {
//			tokenModelFilePath = getContext().getResourceFilePath(TOKEN_RESOURCE_KEY);
//			tokenModelIn = getContext().getResourceAsStream(TOKEN_RESOURCE_KEY);
//			tokenizerModel = new TokenizerModel(tokenModelIn);
//			tokenizer = new TokenizerME(tokenizerModel);
//		} catch (ResourceAccessException e) {
//			logger.throwing(e);
//			throw new ResourceInitializationException("could_not_access_data",
//					new Object[] {tokenModelFilePath}, e);
//		} catch (InvalidFormatException e) {
//			logger.throwing(e);
//			throw new ResourceInitializationException(CTAPException.EXCEPTION_DIGEST, 
//					"incorrect_lang_model_format",
//					new Object[] {tokenModelFilePath}, e);
//		} catch (IOException e) {
//			logger.throwing(e);
//			throw new ResourceInitializationException(CTAPException.EXCEPTION_DIGEST, 
//					"file_io_error",
//					new Object[] {tokenModelFilePath}, e);
//		}

		//init pos tagger
		try {
			POSModelFilePath = getContext().getResourceFilePath(POS_RESOURCE_KEY);

			logger.trace(LogMarker.UIMA_MARKER, 
					new LoadLangModelMessage(POS_RESOURCE_KEY, POSModelFilePath));
			logger.trace(LogMarker.UIMA_MARKER, 
					new LoadLangModelMessage(POS_RESOURCE_KEY, POSModelFilePath));

			posModelIn = getContext().getResourceAsStream(POS_RESOURCE_KEY);
			POSmodel = new POSModel(posModelIn);
			posTagger = new POSTaggerME(POSmodel);

		} catch (ResourceAccessException e) {
			logger.throwing(e);
			throw new ResourceInitializationException("could_not_access_data",
					new Object[] {POSModelFilePath}, e);
		} catch (InvalidFormatException e) {
			logger.throwing(e);
			throw new ResourceInitializationException(CTAPException.EXCEPTION_DIGEST, 
					"incorrect_lang_model_format",
					new Object[] {POSModelFilePath}, e);
		} catch (IOException e) {
			logger.throwing(e);
			throw new ResourceInitializationException(CTAPException.EXCEPTION_DIGEST, 
					"file_io_error",
					new Object[] {POSModelFilePath}, e);
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

		

		//iterate through all sentences
		Iterator sentIter = aJCas.getAnnotationIndex(Sentence.type).iterator();
		while (sentIter.hasNext()) {
			Sentence sent = (Sentence) sentIter.next();
			int sentStart = sent.getBegin();
			int sentEnd = sent.getEnd();
			List<Token> sentTokens = new ArrayList<>();
			
			//iterate through all tokens
			Iterator tokenIter = aJCas.getAnnotationIndex(Token.type).iterator(false);
			while(tokenIter.hasNext()) {
				Token token = (Token) tokenIter.next();

				if(token.getBegin() >= sentStart && token.getEnd() <= sentEnd) {
					sentTokens.add(token);
				}
			}
			
			//convert the list of tokens in the sentence to a String array
			int size = sentTokens.size();
			String[] tokenStrings = new String[size];
			for(int i = 0; i < size; i++) {
				tokenStrings[i] = sentTokens.get(i).getCoveredText();
			}

			//get POS tags
			String tags[] = posTagger.tag(tokenStrings);

			//populate the CAS
			for(int i = 0; i < size; i++) {
				Token token = sentTokens.get(i);
				POS annotation = new POS(aJCas);
				annotation.setBegin(token.getBegin()); 
				annotation.setEnd(token.getEnd());
				annotation.setTag(tags[i]);
				annotation.addToIndexes();
			}
		}

	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));

		if (posModelIn != null) {
			try {
				posModelIn.close();
			}
			catch (IOException e) {
				logger.throwing(e);
			}
		}
		super.destroy();
		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}
}
