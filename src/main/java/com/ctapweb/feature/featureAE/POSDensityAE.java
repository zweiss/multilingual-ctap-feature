/**
 * 
 */
package com.ctapweb.feature.featureAE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
import com.ctapweb.feature.logging.message.PopulatedFeatureValueMessage;
import com.ctapweb.feature.logging.message.ProcessingDocumentMessage;
import com.ctapweb.feature.logging.message.SelectingLanguageSpecificResource;
import com.ctapweb.feature.type.NToken;
import com.ctapweb.feature.type.POS;
import com.ctapweb.feature.type.POSDensity;


public class POSDensityAE extends JCasAnnotator_ImplBase {

	//the analysis engine's id from the database
	//this value needs to be set when initiating the analysis engine
	public static final String PARAM_AEID = "aeID";
	public static final String PARAM_POS_TYPE= "POSType";
	private int aeID;
	private POSMapping posMap;

	//list of pos tags to be counted
	List<String> posList = new ArrayList<>();

	private static final Logger logger = LogManager.getLogger();
	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "POS Density Feature Extractor";

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));
		super.initialize(aContext);

		// obtain language parameter and access language dependent resources
		Optional<String> lCode = Optional.ofNullable((String) aContext.getConfigParameterValue("LanguageCode"));
		logger.trace(LogMarker.UIMA_MARKER, new SelectingLanguageSpecificResource(aeName, lCode.orElse("DEFAULT")));  // TODO debugging remove
		switch (lCode.orElse("")) {
		case "DE":
			posMap = new TigerPOSMapping();
			break;
		case "EN":
			posMap = new PTBPOSMapping();
			break;
		default:  // default to English analysis
			posMap = new PTBPOSMapping();
			break;
		}

		// get the mandatory pamameter
		String POSType = (String) aContext.getConfigParameterValue(PARAM_POS_TYPE);
		posList = getPOSTagList(POSType);
		//		logger.trace(LogMarker.UIMA_MARKER, "The following POS density will be calculated: ");
		//		for(String pos: posList) {
		//			logger.trace(LogMarker.UIMA_MARKER, pos);
		//		}

		//get the parameter value of analysis id
		if(aContext.getConfigParameterValue(PARAM_AEID) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_AEID});
			logger.throwing(e);
			throw e;
		} else {
			aeID = (Integer) aContext.getConfigParameterValue(PARAM_AEID);
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

		// get annotation indexes and iterator
		Iterator posIter = aJCas.getAnnotationIndex(POS.type).iterator();

		//count number of occurrences 
		int nPOSTypes = 0;
		while(posIter.hasNext()) {
			POS pos = (POS) posIter.next();
			String tag = pos.getTag();
			if(posList.contains(tag)) {
				nPOSTypes++;
				//				logger.trace(LogMarker.UIMA_MARKER, "found a target tag: " + tag);
			}
		}

		int nTokens = getNTokens(aJCas);
		double density = 0;
		if(nTokens != 0 ) {
			density = (double)nPOSTypes / nTokens;
		}

		//output the feature type
		POSDensity annotation = new POSDensity(aJCas);

		//set the feature ID
		annotation.setId(aeID);

		//set feature value
		annotation.setValue(density);
		annotation.addToIndexes();

		logger.info(new PopulatedFeatureValueMessage(aeID, density));
	}

	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));

		super.destroy();

		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}

	//gets the parts of speech that this feature is looking for
	private List<String> getPOSTagList(String POSType) {
		List<String> tagList = new ArrayList<>();

		switch(POSType) {
		case "lexical": 
			Collections.addAll(tagList, posMap.getLexicalWordPOS());
			break;
		case "functional":
			Collections.addAll(tagList, posMap.getFunctionalWordPOS());
			break;
		case "conjunction":
			Collections.addAll(tagList, posMap.getConjunctionPOS());
			break;
		case "determiner":
			Collections.addAll(tagList, posMap.getDeterminerPOS());
			break;
		case "adjective":
			Collections.addAll(tagList, posMap.getAdjectivePOS());
			break;
		case "noun":
			Collections.addAll(tagList, posMap.getNounPOS());
			break;
		case "pronoun":
			Collections.addAll(tagList, posMap.getPronounPOS());
			break;
		case "adverb":
			Collections.addAll(tagList, posMap.getAdverbPOS());
			break;
		case "verb":
			Collections.addAll(tagList, posMap.getVerbPOS());
			break;
		default:
			//deal with space separated POS type list
			String[] types = POSType.split("\\b");
			Collections.addAll(tagList, types);
		}

		return tagList;
	}

	// TODO delete out-dated code
	//	//gets the parts of speech that this feature is looking for
	//	private List<String> getPOSTagList(String POSType) {
	//		List<String> tagList = new ArrayList<>();
	//		String[] lexical = {
	//				"JJ", "JJR", "JJS", //adj
	//				"RB", "RBR", "RBS", "WRB", //adv
	//				"VB", "VBD", "VBG", "VBN", "VBP", "VBZ", //verb
	//				"NN", "NNS", "NNP", "NNPS" //noun
	//		};
	//		String[] functional = {
	//				"CC", "IN", "PDT", "DT", "WDT", "PRP", "PRP$", "WP", "WP$",
	//				"CD", "EX", "FW", "LS", "MD", "POS", "RP", "SYM", "TO", "UH"
	//		};
	//		String[] conjunction = {"CC", "IN"};
	//		String[] determiner = { "PDT", "DT", "WDT" };
	//		String[] adjective = {"JJ", "JJR", "JJS"};
	//		String[] noun = { "NN", "NNS", "NNP", "NNPS" };
	//		String[] pronoun = {"PRP", "PRP$", "WP", "WP$"};
	//		String[] adverb = {"RB", "RBR", "RBS", "WRB"};
	//		String[] verb = {"VB", "VBD", "VBG", "VBN", "VBP", "VBZ"};
	//		
	//		switch(POSType) {
	//		case "lexical": 
	//			Collections.addAll(tagList, lexical);
	//			break;
	//		case "functional":
	//			Collections.addAll(tagList, functional);
	//			break;
	//		case "conjunction":
	//			Collections.addAll(tagList, conjunction);
	//			break;
	//		case "determiner":
	//			Collections.addAll(tagList, determiner);
	//			break;
	//		case "adjective":
	//			Collections.addAll(tagList, adjective);
	//			break;
	//		case "noun":
	//			Collections.addAll(tagList, noun);
	//			break;
	//		case "pronoun":
	//			Collections.addAll(tagList, pronoun);
	//			break;
	//		case "adverb":
	//			Collections.addAll(tagList, adverb);
	//			break;
	//		case "verb":
	//			Collections.addAll(tagList, verb);
	//			break;
	//		default:
	//			//deal with space separated POS type list
	//			String[] types = POSType.split("\\b");
	//			Collections.addAll(tagList, types);
	//		}
	//		
	//		return tagList;
	//	}

	// get number of tokens from the CAS
	private int getNTokens(JCas aJCas) {
		int n = 0;
		//		Iterator posIter = aJCas.getAnnotationIndex(NToken.type).iterator();
		Iterator posIter = aJCas.getAllIndexedFS(NToken.class);
		if(posIter.hasNext()) {
			NToken nToken = (NToken)posIter.next();
			n = (int) nToken.getValue();
		}
		return n;
	}


	/**
	 * Abstract method to define POS tag set dependent POS mapping to generalizable linguistic categories
	 * e.g. adjectives, nouns, verbs, ... 
	 * @author zweiss
	 */
	public abstract class POSMapping {

		public String[] adjectivePOS;
		public String[] adverbPOS;
		public String[] nounPOS;
		public String[] verbPOS;
		public String[] conjunctionPOS;
		public String[] determinerPOS;
		public String[] pronounPOS;
		public String[] lexicalWordPOS;
		public String[] functionalWordPOS;

		public String[] getAdjectivePOS() {
			return adjectivePOS;
		}
		public String[] getAdverbPOS() {
			return adverbPOS;
		}
		public String[] getConjunctionPOS() {
			return conjunctionPOS;
		}
		public String[] getDeterminerPOS() {
			return determinerPOS;
		}
		public String[] getNounPOS() {
			return nounPOS;
		}
		public String[] getPronounPOS() {
			return pronounPOS;
		}
		public String[] getVerbPOS() {
			return verbPOS;
		}
		public String[] getLexicalWordPOS() {
			return lexicalWordPOS;
		}
		public String[] getFunctionalWordPOS() {
			return functionalWordPOS;
		}

	}

	/**
	 * Maps Penn Treebank POS tags to generalizable word categories
	 * @author zweiss
	 *
	 */
	public class PTBPOSMapping extends POSMapping {
		public PTBPOSMapping() {
			// lexical words
			adjectivePOS = new String[]{"JJ", "JJR", "JJS"};
			adverbPOS = new String[]{"RB", "RBR", "RBS", "WRB"};
			nounPOS = new String[]{"NN", "NNS", "NNP", "NNPS"};
			verbPOS = new String[]{"VB", "VBD", "VBG", "VBN", "VBP", "VBZ"};
			// function words
			conjunctionPOS = new String[]{"CC", "IN"};
			determinerPOS = new String[]{"PDT", "DT", "WDT"};
			pronounPOS = new String[]{"PRP", "PRP$", "WP", "WP$"};
			// collections
			lexicalWordPOS = new String[]{
					"JJ", "JJR", "JJS", //adj
					"RB", "RBR", "RBS", "WRB", //adv
					"VB", "VBD", "VBG", "VBN", "VBP", "VBZ", //verb
					"NN", "NNS", "NNP", "NNPS" //noun
			};
			functionalWordPOS = new String[]{
					"CC", "IN", "PDT", "DT", "WDT", "PRP", "PRP$", "WP", "WP$",
					"CD",  // TODO should cardinals be functional words? 
					"EX", 
					"FW",  // TODO should a foreign word be considered a functional word?
					"LS",  // TODO should a list marker be a functional word? 
					"MD", "POS", "RP", 
					"SYM",  // TODO should a symbol be a functional word? 
					"TO", "UH"
			};
		
		}
	}

	/**
	 * Maps Tiger POS tags to generalizable word categories
	 * @author zweiss
	 *
	 */
	public class TigerPOSMapping extends POSMapping {
		public TigerPOSMapping() {
			// lexical words
			adjectivePOS = new String[]{"ADJA", "ADJD"};
			adverbPOS = new String[]{"ADV"};
			nounPOS = new String[]{"NN", "NE"};
			verbPOS = new String[]{
					"VVFIN", "VVIMP", "VVINF", "VVIZU", "VVPP",  // main verbs
					"VMFIN", "VMIMP", "VMINF", "VMIZU", "VMPP",  // modal verbs
					"VAFIN", "VAIMP", "VAINF", "VAIZU", "VAPP"  // auxiliary verbs
			};
			// function words
			conjunctionPOS = new String[]{"KOUI", "KOUS", "KON", "KOKOM"};
			determinerPOS = new String[]{"ART", "PDAT", "PIAT", "PPOSAT", "PRELAT", "PWAT"};
			pronounPOS = new String[]{
					"PDS", "PDAT", "PIS", "PIAT", "PIDAT", "PPER", "PPOSS", "PPOSAT", 
					"PRELS", "PRELSAT", "PRF", "PWS", "PWAT", "PWAV", "PAV"};
			// collections
			lexicalWordPOS = new String[]{
					"ADJA", "ADJD",  // adjectives
					"ADV",  // adverbs
					"NN", "NE",  // nouns
					"VVFIN", "VVIMP", "VVINF", "VVIZU", "VVPP",  // main verbs
					"VMFIN", "VMIMP", "VMINF", "VMIZU", "VMPP",  // modal verbs
					"FM", 
					"XY"  // TODO should a non word be a lexical word?
			};
			functionalWordPOS = new String[]{
					"CARD",  // TODO should cardinals be function words?
					"ITJ", "KOUI", "KOUS", "KON", "KOKOM",
					"PDS", "PDAT", "PIS", "PIAT", "PIDAT", "PPER", "PPOSS", "PPOSAT", 
					"PRELS", "PRELSAT", "PRF", "PWS", "PWAT", "PWAV", "PAV",
					"PTKZU", "PTKNEG", "PTKVZ", "PTKANT", "PTKA",
					"VAFIN", "VAIMP", "VAINF", "VAIZU", "VAPP",
					"TRUNC"  // TODO should a compounding first element ("An- und Abreise") be a functional word?
			};
		}
	}
}
