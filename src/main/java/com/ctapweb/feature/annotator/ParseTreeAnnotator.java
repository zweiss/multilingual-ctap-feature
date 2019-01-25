package com.ctapweb.feature.annotator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;
import org.apache.uima.resource.ResourceInitializationException;

import com.ctapweb.feature.annotator.SyllableAnnotator.SyllablePatterns;
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
import com.ctapweb.feature.type.ParseTree;
import com.ctapweb.feature.type.Sentence;
import com.ctapweb.feature.type.Token;
import com.ctapweb.feature.util.SupportedLanguages;
import com.google.protobuf.DescriptorProtos.GeneratedCodeInfo.Annotation;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.util.InvalidFormatException;

public class ParseTreeAnnotator extends JCasAnnotator_ImplBase {

	private ConstituencyParser parser;

	public static String PARSER_RESOURCE_KEY = "ParserModel";

	private static final String PARAM_LANGUAGE_CODE = "LanguageCode";
	private static final Logger logger = LogManager.getLogger();
	private static final AEType aeType = AEType.ANNOTATOR;
	private static final String aeName = "Parse Annotator";

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
			lCode = (String) aContext.getConfigParameterValue(PARAM_LANGUAGE_CODE);
		}
		String languageSpecificResourceKey = PARSER_RESOURCE_KEY+lCode;

		//init parser 
		try {
			parserModelFilePath = getContext().getResourceFilePath(languageSpecificResourceKey);

			logger.trace(LogMarker.UIMA_MARKER, 
					new LoadLangModelMessage(languageSpecificResourceKey, parserModelFilePath));

			switch (lCode) {
			case SupportedLanguages.GERMAN:
				parser = new StanfordCoreNLPConstituencyParser(parserModelFilePath);  
				break;
				// add new language here
			case SupportedLanguages.ENGLISH:  // English as default
			default:  // TODO LCA think if this default makes sense
				//parser = new OpenNLPConstituencyParser(parserModelFilePath);  // This is how the OpenNLP parser would be called
				parser = new StanfordCoreNLPConstituencyParser(parserModelFilePath);
				break;
			}
		} catch (ResourceAccessException e) {
			logger.throwing(e);
			throw new ResourceInitializationException("could_not_access_data",
					new Object[] {parserModelFilePath}, e);
			//} catch (InvalidFormatException e) { //  Only needed if OpenNLP parser is used 
			//	logger.throwing(e);
			//	throw new ResourceInitializationException(CTAPException.EXCEPTION_DIGEST, 
			//			"incorrect_lang_model_format",
			//			new Object[] {parserModelFilePath}, e);
			//} catch (IOException e) {
			//	logger.throwing(e);
			//	throw new ResourceInitializationException(CTAPException.EXCEPTION_DIGEST, 
			//			"file_io_error",
			//			new Object[] {parserModelFilePath}, e);
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
			List<POS> sentPOS = new ArrayList<POS>();

			//iterate through all POS tags
			Iterator posIter = aJCas.getAnnotationIndex(POS.type).iterator(false);
			while(posIter.hasNext()) {
				POS pos= (POS) posIter.next();
				if(pos.getBegin() >= sentStart && pos.getEnd() <= sentEnd) {
					sentPOS.add(pos);
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
			String tree = parser.parse(sentTokens, sentPOS);

			//populate the CAS
			ParseTree annotation = new ParseTree(aJCas);
			annotation.setBegin(sentStart);
			annotation.setEnd(sentEnd);
			annotation.setParseTree(tree);
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
	 * Abstract class for constituency parser; acts as wrapper for any constituency parser that may be 
	 * added to support new languages or to change existing parsing components.
	 * @author zweiss
	 */
	private abstract class ConstituencyParser {

		protected abstract String parse(List<Token> sentTokens, List<POS> sentPOS);

		/**
		 * Returns flat PTB structure containing POS tags and tokens in case paring fails
		 * @param tokens List of tokens in sentence
		 * @param tags List of tags in sentence
		 * @param root root element, e.g. "(ROOT"
		 * @param end, ending bracket, e.g. ")"
		 * @return tree string in ptb fromat
		 */
		public String getFlatPTBTree(List<Token> tokens, List<POS> tags, String root, String end) {
			StringBuilder sb = new StringBuilder();
			boolean withoutTags = tags.size() != tokens.size();
			sb.append(root);
			for (int i = 0; i < tokens.size(); i++) {
				if (withoutTags) {
					sb.append(tokens.get(i));
					continue;
				}
				sb.append(" (");
				sb.append(tags.get(i).getTag());
				sb.append(" ");
				sb.append(tokens.get(i).getCoveredText());
				sb.append(")");
			}
			sb.append(end);
			logger.warn(LogMarker.UIMA_MARKER, "Failed to generate parse, create placeholder: "+sb.toString());
			return sb.toString();
		}

	}

	/**
	 * Wrapper for use of OpenNLP parser
	 * @author zweiss
	 *
	 */
	@SuppressWarnings("unused")
	private class OpenNLPConstituencyParser extends ConstituencyParser {

		private InputStream modelIn;
		private ParserModel openNlpParserModel;
		private Parser openNlpParser;

		public OpenNLPConstituencyParser(String modelInFile) throws IOException {
			modelIn = new FileInputStream(new File(modelInFile));
			openNlpParserModel = new ParserModel(modelIn);
			openNlpParser = ParserFactory.create(openNlpParserModel);
			modelIn.close();
		}

		// TODO LCA : implement better version, figure out if OpenNLP parser also takes a) POS tags and b) lists of tokens
		@Override
		public String parse(List<Token> tokens, List<POS> tags) {
			int size = tokens.size();
			String tokenizedSent = "";
			for(int i = 0; i < size; i++) {
				tokenizedSent += tokens.get(i).getCoveredText() + " ";
			}
			return parse(String.join(" ", tokenizedSent));
		}

		public String parse(String tokenizedSent) {
			//parse the sentence
			Parse parses[] = ParserTool.parseLine(tokenizedSent, openNlpParser, 1);
			StringBuffer sb = new StringBuffer();
			for(Parse parse: parses) {
				parse.show(sb);
			}
			return sb.toString();
		}
	}

	private class StanfordCoreNLPConstituencyParser extends ConstituencyParser {

		private LexicalizedParser stanfordParser;

		public StanfordCoreNLPConstituencyParser(String modelInFile) {
			stanfordParser = LexicalizedParser.loadModel(modelInFile);
		}

		public String parse(List<Token> tokens) {
			// create Stanford tagged words
			List<Word> stanfordWords = new ArrayList<Word>();
			for (int i=0, l=tokens.size(); i<l; i++) {
				stanfordWords.add(new TaggedWord(tokens.get(i).getCoveredText()));
			}
			// parse
			Tree tree = stanfordParser.parseTree(stanfordWords);
			// in case of failure:
			if (tree == null) {
				return getFlatPTBTree(tokens, new ArrayList<POS>(), "(ROOT", ")");
			}
			return tree.toString();
		}

		@Override
		public String parse(List<Token> tokens, List<POS> tags) {
			// sanity check
			if(tags.size()!=tokens.size()) {
				logger.warn(LogMarker.UIMA_MARKER, "POS Tag parsing issue: "+tags.size()+" tag(s) for "+tokens.size()+" token(s)");
				return parse(tokens);
			}
			// create Stanford tagged words
			List<TaggedWord> stanfordTaggedWords = new ArrayList<TaggedWord>();
			for (int i=0, l=tokens.size(); i<l; i++) {
				stanfordTaggedWords.add(new TaggedWord(tokens.get(i).getCoveredText(), tags.get(i).getTag()));
			}
			// parse
			Tree tree = stanfordParser.parseTree(stanfordTaggedWords);
			// in case of failure:
			if (tree == null)
				return getFlatPTBTree(tokens, tags, "(ROOT", ")");
			return tree.toString();
		}

	}
}
