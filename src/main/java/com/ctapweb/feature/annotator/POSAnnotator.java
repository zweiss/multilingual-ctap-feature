package com.ctapweb.feature.annotator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.util.InvalidFormatException;

/**
 * Annotates text with POS tags for each token in the input text
 * Requires the following annotations: sentences, tokens (see POSAnnotatorTAE.xml)
 * 
 * POS tagging is done using the POSTagger interface. 
 * To add a new POS tagger, make sure to implement the POSTagger interface.
 * @author zweiss
 *
 */
public class POSAnnotator extends JCasAnnotator_ImplBase {

	//for pos tagger
	private POSTagger posTagger;
	public static String POS_RESOURCE_KEY = "POSModel";

	private static final String PARAM_LANGUAGE_CODE = "LanguageCode";
	private static final Logger logger = LogManager.getLogger();
	private static final AEType aeType = AEType.ANNOTATOR;
	private static final String aeName = "POS Annotator";

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));
		super.initialize(aContext);

//		String tokenModelFilePath = null;
		String POSModelFilePath = null;
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

		//init pos tagger
		String languageSpecificResourceKey = POS_RESOURCE_KEY+lCode;
		try {
			POSModelFilePath = getContext().getResourceFilePath(languageSpecificResourceKey);

			logger.trace(LogMarker.UIMA_MARKER, 
					new LoadLangModelMessage(languageSpecificResourceKey, POSModelFilePath));

			posTagger = new OpenNLPPosTagger(POSModelFilePath);
			// add switch statement here to allow for different instantiations; see example in ParseTreeAnnotator.java

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

			//get POS tags
			String[] tags = posTagger.tag(sentTokens);

			//populate the CAS
			for(int i = 0; i < tags.length; i++) {
				Token token = sentTokens.get(i);
				POS annotation = new POS(aJCas);
				annotation.setBegin(token.getBegin()); 
				annotation.setEnd(token.getEnd());
				annotation.setTag(tags[i]);
				//System.out.println(token.getCoveredText() + " " + tags[i]);
				annotation.addToIndexes();
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
	 * Abstract class for POS tagger; acts as wrapper for any POS tagger that may be 
	 * added to support new languages or to change existing parsing components.
	 * @author zweiss
	 */
	private abstract class POSTagger {
		abstract String[] tag(List<Token> tokenizedSentence);

		//convert the list of tokens in the sentence to a String array
		protected String[] convertTokenListToStringArray(List<Token> tokenList) {
			String[] tokenStrings = new String[tokenList.size()];
			for(int i = 0; i < tokenList.size(); i++) {
				tokenStrings[i] = tokenList.get(i).getCoveredText();
			}
			return tokenStrings;
		}
	}

	/**
	 * Wrapper for use of OpenNLP POS tagger (https://opennlp.apache.org/)
	 * @author zweiss
	 *
	 */
	private class OpenNLPPosTagger extends POSTagger {
		
		private InputStream modelIn;
		private POSModel openNlpModel;
		private POSTaggerME openNlpPOSTagger;
		
		public OpenNLPPosTagger(String modelInFile) throws IOException {
			modelIn = new FileInputStream(new File(modelInFile));
			openNlpModel = new POSModel(modelIn);
			openNlpPOSTagger = new POSTaggerME(openNlpModel);
			modelIn.close();
		}

		@Override
		public String[] tag(List<Token> tokenizedSentence) {
			String[] tokenArray = convertTokenListToStringArray(tokenizedSentence);
			return openNlpPOSTagger.tag(tokenArray);
		}
	}
	
}
