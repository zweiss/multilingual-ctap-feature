package com.ctapweb.feature.annotator;

import java.io.IOException;
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
import com.ctapweb.feature.type.Lemma;
import com.ctapweb.feature.type.MorphologicalTag;
import com.ctapweb.feature.type.Sentence;
import com.ctapweb.feature.type.Token;

import is2.data.SentenceData09;

/**
 * Annotates text with morphological features for each word in the input text
 * Requires the following annotations: sentences, tokens, lemmas (see MorphologicalTagTAE.xml)
 * 
 * Morphological tagging is done using the CTAPMorphologicalTagger interface. 
 * To add a new morphological tagger, make sure to implement the CTAPMorphologicalTagger interface.
 * 
 * @author zweiss
 *
 */
public class MorphologicalTagAnnotator extends JCasAnnotator_ImplBase {

	//for pos tagger
	private CTAPMorphologicalTagger morphologicalTagger;
	public static String MTAG_RESOURCE_KEY = "MorphologicalModel";

	private static final String PARAM_LANGUAGE_CODE = "LanguageCode";
	private static final Logger logger = LogManager.getLogger();
	private static final AEType aeType = AEType.ANNOTATOR;
	private static final String aeName = "Morphological Tag Annotator";

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));
		super.initialize(aContext);

		String morpholigicalModelFilePath = null;
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
		String languageSpecificResourceKey = MTAG_RESOURCE_KEY+lCode;
		try {
			morpholigicalModelFilePath = getContext().getResourceFilePath(languageSpecificResourceKey);

			logger.trace(LogMarker.UIMA_MARKER, 
					new LoadLangModelMessage(languageSpecificResourceKey, morpholigicalModelFilePath));

			morphologicalTagger = new MateMorphologicalTagger(morpholigicalModelFilePath);
			// add switch statement here to allow for different instantiations; see example in ParseTreeAnnotator.java

		} catch (ResourceAccessException e) {
			logger.throwing(e);
			throw new ResourceInitializationException("could_not_access_data",
					new Object[] {morpholigicalModelFilePath}, e);
		} catch (IOException e) {
			logger.throwing(e);
			throw new ResourceInitializationException(CTAPException.EXCEPTION_DIGEST, 
					"file_io_error",
					new Object[] {morpholigicalModelFilePath}, e);
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
			List<Lemma> sentLemmas = new ArrayList<>();

			//iterate through all tokens
			Iterator tokenIter = aJCas.getAnnotationIndex(Token.type).iterator(false);
			while(tokenIter.hasNext()) {
				Token token = (Token) tokenIter.next();
				if(token.getBegin() >= sentStart && token.getEnd() <= sentEnd) {
					sentTokens.add(token);
				}
			}

			//iterate through all lemmas
			Iterator lemmaIter = aJCas.getAnnotationIndex(Lemma.type).iterator(false);
			while(lemmaIter.hasNext()) {
				Lemma lemma = (Lemma) lemmaIter.next();
				if(lemma.getBegin() >= sentStart && lemma.getEnd() <= sentEnd) {
					sentLemmas.add(lemma);
				}
			}

			//get morphological tags
			String[] morphologicalTags = morphologicalTagger.mtag(sentTokens, sentLemmas);

			//populate the CAS
			for(int i = 0; i < morphologicalTags.length; i++) {
				Token token = sentTokens.get(i);
				MorphologicalTag annotation = new MorphologicalTag(aJCas);
				annotation.setBegin(token.getBegin()); 
				annotation.setEnd(token.getEnd());
				annotation.setMorphologicalTag(morphologicalTags[i]);
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
	 * Interface for morphological tagger; acts as wrapper for any morphological tagger that may be 
	 * added to support new languages or to change existing morphological tagging components.
	 * @author zweiss
	 */
	interface CTAPMorphologicalTagger {
		abstract String[] mtag(List<Token> sentTokens, List<Lemma> sentLemmas);
	}

	/**
	 * Wrapper for use of Mate morphological tagger
	 * which is part of the Mate tools (https://www.ims.uni-stuttgart.de/forschung/ressourcen/werkzeuge/matetools.en.html)
	 * @author zweiss
	 */
	private class MateMorphologicalTagger implements CTAPMorphologicalTagger {

		private is2.mtag.Tagger mateMorphologicalTagger;

		public MateMorphologicalTagger(String modelInFile) throws IOException {
			is2.mtag.Options morphologicTaggerOptions = new is2.mtag.Options(new String[]{"-model", modelInFile}); 
			mateMorphologicalTagger = new is2.mtag.Tagger(morphologicTaggerOptions); 
		}

		@Override
		public String[] mtag(List<Token> sentTokens, List<Lemma> sentLemmas) {
			String[] tokens = new String[sentTokens.size()];
			String[] lemmas = new String[sentLemmas.size()];
			for (int i = 0; i < sentTokens.size(); i++) {
				tokens[i] = sentTokens.get(i).getCoveredText();
				lemmas[i] = sentLemmas.get(i).getLemma();
			}
			return mtag(tokens, lemmas);
		}

		/**
		 * Takes input with root element at initial position
		 */
		public String[] mtag(String[] tokens, String[] lemmas) {
			SentenceData09 inputSentenceData = new SentenceData09();
			inputSentenceData.init(tokens);
			inputSentenceData.setLemmas(lemmas);
			return mtag(inputSentenceData);
		}

		public String[] mtag(SentenceData09 inputSentenceData) {
			SentenceData09 mtaggedSentence = mateMorphologicalTagger.apply(inputSentenceData);
			return mtaggedSentence.pfeats;
		}
	}
}
