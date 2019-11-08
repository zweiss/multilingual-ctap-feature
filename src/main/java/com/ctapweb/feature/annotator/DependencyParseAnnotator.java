package com.ctapweb.feature.annotator;

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

import com.ctapweb.feature.logging.LogMarker;
import com.ctapweb.feature.logging.message.AEType;
import com.ctapweb.feature.logging.message.DestroyAECompleteMessage;
import com.ctapweb.feature.logging.message.DestroyingAEMessage;
import com.ctapweb.feature.logging.message.InitializeAECompleteMessage;
import com.ctapweb.feature.logging.message.InitializingAEMessage;
import com.ctapweb.feature.logging.message.LoadLangModelMessage;
import com.ctapweb.feature.logging.message.ProcessingDocumentMessage;
import com.ctapweb.feature.type.DependencyParse;
import com.ctapweb.feature.type.Lemma;
import com.ctapweb.feature.type.MorphologicalTag;
import com.ctapweb.feature.type.POS;
import com.ctapweb.feature.type.Sentence;
import com.ctapweb.feature.type.Token;
import com.ctapweb.feature.util.DependencyTree;
import com.ctapweb.feature.util.SupportedLanguages;

import is2.data.SentenceData09;
import is2.parser.Options;
import is2.parser.Parser;
import is2.tag.Tagger;

/**
 * Annotates text with dependency parses for each sentence in the input text
 * Requires the following annotations: sentences, tokens, lemmas, morphology, and POS (see DependencyParseAnnotatorTAE.xml)
 * 
 * Dependency parsing is done using the DependencyParser interface. 
 * To add a new dependency parser, make sure to implement the DependencyParser interface.
 * 
 * @author zweiss
 * 
 */
public class DependencyParseAnnotator extends JCasAnnotator_ImplBase {

	private DependencyParser depParser;

	public static String PARSER_RESOURCE_KEY = "DepParserModel";
	
	private static final String PARAM_LANGUAGE_CODE = "LanguageCode";
	private static final Logger logger = LogManager.getLogger();
	private static final AEType aeType = AEType.ANNOTATOR;
	private static final String aeName = "Dependency Parse Annotator";

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));
		super.initialize(aContext);

		String parserModelFilePath = null;

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
		String parseModelLanguageSpecificResourceKey = PARSER_RESOURCE_KEY+lCode;

		//init parser, morphological tagger, and pos tagger 
		try {
			parserModelFilePath = getContext().getResourceFilePath(parseModelLanguageSpecificResourceKey);
			logger.trace(LogMarker.UIMA_MARKER, 
					new LoadLangModelMessage(parseModelLanguageSpecificResourceKey, parserModelFilePath));

			switch (lCode) {
			case SupportedLanguages.GERMAN:
				depParser = new MateDependencyParser(parserModelFilePath);  
				break;
				// add new language here
			}
		} catch (ResourceAccessException e) {
			logger.throwing(e);
			throw new ResourceInitializationException("could_not_access_data",
					new Object[] {parserModelFilePath}, e);
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
			List<Token> sentTokens = new ArrayList<Token>();
			List<Lemma> sentLemmas = new ArrayList<Lemma>();
			List<POS> sentPOS = new ArrayList<POS>();
			List<MorphologicalTag> sentMorphologicalTags= new ArrayList<MorphologicalTag>();  // preferable if this would work
 
			//iterate through all lemmas
			Iterator lemmaIter = aJCas.getAnnotationIndex(Lemma.type).iterator(false);
			while(lemmaIter.hasNext()) {
				Lemma lemma = (Lemma) lemmaIter.next();
				if(lemma.getBegin() >= sentStart && lemma.getEnd() <= sentEnd) {
					sentLemmas.add(lemma);
				}
			}
			
			//iterate through all POS tags
			Iterator posIter = aJCas.getAnnotationIndex(POS.type).iterator(false);
			while(posIter.hasNext()) {
				POS posTag = (POS) posIter.next();
				if(posTag.getBegin() >= sentStart && posTag.getEnd() <= sentEnd) {
					sentPOS.add(posTag);
				}
			}

			// preferable if this would work
			//iterate through all morphological tags
			Iterator morphIter = aJCas.getAnnotationIndex(MorphologicalTag.type).iterator(false);
			while(morphIter.hasNext()) {
				MorphologicalTag mtag = (MorphologicalTag) morphIter.next();
				if(mtag.getBegin() >= sentStart && mtag.getEnd() <= sentEnd) {
					sentMorphologicalTags.add(mtag);
				}
			}

			//iterate through all tokens
			Iterator tokenIter = aJCas.getAnnotationIndex(Token.type).iterator(false);
			while(tokenIter.hasNext()) {
				Token token = (Token) tokenIter.next();
				if(token.getBegin() >= sentStart && token.getEnd() <= sentEnd) {
					sentTokens.add(token);
				}
			}

			// build parse tree based on POS and token list
			String dTree= depParser.parse(sentTokens, sentLemmas, sentPOS, sentMorphologicalTags);  // preferable if this would work
//			logger.trace(LogMarker.UIMA_MARKER, System.getProperty("line.separator")+dTree);  // debugging
			
			//populate the CAS
			DependencyParse annotation = new DependencyParse(aJCas);
			annotation.setBegin(sentStart);
			annotation.setEnd(sentEnd);
			annotation.setDependencyParse(dTree);
			annotation.addToIndexes();
		}

	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));
		super.destroy();
		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}

	/**
	 * Interface for dependency parser; acts as wrapper for any constituency parser that may be 
	 * added to support new languages or to change existing parsing components.
	 * @author zweiss
	 */
	interface DependencyParser {
	
		abstract String parse(List<Token> sentTokens, List<Lemma> sentLemmas, List<POS> sentPOS, List<MorphologicalTag> sentMtags); 
	}


	/**
	 * Implements the DependencyParser interface as a wrapper for the Mate dependency parser 
	 * which is part of the Mate tools (https://www.ims.uni-stuttgart.de/forschung/ressourcen/werkzeuge/matetools.en.html) 
	 * @author zweiss
	 *
	 */
	private class MateDependencyParser implements DependencyParser {

		private Parser mateDependencyParser;

		public MateDependencyParser(String modelInFileParser) {
			// init dependency parser
			String[] optsParser = {"-model", modelInFileParser}; 
			Options optionsParser = new Options(optsParser);
			mateDependencyParser = new Parser(optionsParser);
		}
		
		@Override
		public String parse(List<Token> sentTokens, List<Lemma> sentLemmas, List<POS> sentPOS, List<MorphologicalTag> sentMtags) {
			String[] tokens = new String[sentTokens.size()];
			String[] lemmas = new String[sentLemmas.size()];
			String[] posTags = new String[sentPOS.size()];
			String[] mTags = new String[sentMtags.size()];
			for (int i = 0; i < sentTokens.size(); i++) {
				tokens[i] = sentTokens.get(i).getCoveredText();
				lemmas[i] = sentLemmas.get(i).getLemma();
				posTags[i] = sentPOS.get(i).getTag();
				mTags[i] = sentMtags.get(i).getMorphologicalTag();
			}
			return parse(tokens, lemmas, posTags, mTags);
		}

		/**
		 * Takes input with root element at initial position
		 */
		public String parse(String[] tokens, String[] lemmas, String[] posTags, String[] mTags) {
			SentenceData09 annotatedSentence = new SentenceData09();
			annotatedSentence.init(tokens);
			annotatedSentence.setLemmas(lemmas);  
			annotatedSentence.setPPos(posTags);  
			annotatedSentence.setFeats(mTags);  // does not work for version 3.61 of mate tools
			return parse(annotatedSentence);
		}

		/**
		 * parses (fully) initialized sentence
		 * @param annotatedSentence
		 * @return
		 */
		public String parse(SentenceData09 inputSentenceData) {
			SentenceData09 sentenceToAnalyze = inputSentenceData;
			// add dependency relations and labels to sentence
			sentenceToAnalyze = mateDependencyParser.apply(sentenceToAnalyze);
			// return conll format parse
			return new DependencyTree(sentenceToAnalyze).toString();
		}
	}
}
