package com.ctapweb.feature.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.XMLInputSource;
import org.apache.uima.util.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import com.ctapweb.feature.test.util.DescriptorModifier;
import com.ctapweb.feature.type.ComplexityFeatureBase;

public class POSDensityFeatureTest {
	JCas jCas;
	XMLParser pars;
	AnalysisEngineDescription aedSent, aedToken, aedPOS, aedNToken;
	HashMap <String, ArrayList <String>> paramsHashMap;
	ArrayList<String> locationsListForTest;
	/*
	@Before
	public void setUp() throws Exception {
		pars = UIMAFramework.getXMLParser();
		
		TypeSystemDescription tsd = TypeSystemDescriptionFactory.createTypeSystemDescription();
		
		ArrayList<String> locationsList = new ArrayList<String>();
		locationsList.add("src/main/resources/descriptor/type_system/feature_type/ComplexityFeatureBaseType.xml");
		locationsList.add("src/main/resources/descriptor/type_system/feature_type/POSDensityType.xml");
		locationsList.add("src/main/resources/descriptor/type_system/feature_type/NTokenType.xml");
		
		DescriptorModifier.readXMLTypeDescriptorModifyImports ("src/main/resources/descriptor/type_system/feature_type/POSDensityType.xml", "./META-INF/org.apache.uima.fit/POSDensityTypeForUIMAFitTest.xml", locationsList);
		String sdSentenceLengthTypeDescr = new String(Files.readAllBytes(Paths.get("./META-INF/org.apache.uima.fit/POSDensityTypeForUIMAFitTest.xml")));
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new ByteArrayInputStream(sdSentenceLengthTypeDescr.getBytes("UTF-8")));
		
		tsd.buildFromXMLElement(doc.getDocumentElement(), pars);
	    jCas = CasCreationUtils.createCas(tsd, null, null).getJCas();
		
	    String contents = new String(Files.readAllBytes(Paths.get("./META-INF/cani.txt")));
		jCas.setDocumentText(contents);
		
		File fSent = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguage ("src/main/resources/descriptor/annotator/SentenceAnnotator.xml", "./META-INF/org.apache.uima.fit/SentenceAnnotatorForUIMAFitTest.xml", "IT");
		XMLInputSource xmlInputSourceSent = new XMLInputSource(fSent);
		aedSent = pars.parseAnalysisEngineDescription(xmlInputSourceSent);
		
		File fToken = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguage ("src/main/resources/descriptor/annotator/TokenAnnotator.xml", "./META-INF/org.apache.uima.fit/TokenAnnotatorForUIMAFitTest.xml", "IT");
		XMLInputSource xmlInputSourceToken = new XMLInputSource(fToken);
		aedToken = pars.parseAnalysisEngineDescription(xmlInputSourceToken);
		
		File fPOS = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguage ("src/main/resources/descriptor/annotator/POSAnnotator.xml", "./META-INF/org.apache.uima.fit/POSAnnotatorForUIMAFitTest.xml", "IT");
		XMLInputSource xmlInputSourcePOS = new XMLInputSource(fPOS);
		aedPOS = pars.parseAnalysisEngineDescription(xmlInputSourcePOS);
		
		File fNToken = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddaeID ("src/main/resources/descriptor/featureAE/NTokenFeature.xml", "./META-INF/org.apache.uima.fit/NTokenFeatureForUIMAFitTest.xml", "IT", "123");
		XMLInputSource xmlInputSourceNToken = new XMLInputSource(fNToken);
		aedNToken = pars.parseAnalysisEngineDescription(xmlInputSourceNToken);
		
		paramsHashMap = new HashMap <String, ArrayList <String>> ();		
		ArrayList dynamicStringArray = new ArrayList(2);
		String[] names = {"integer", "333"};
		dynamicStringArray.addAll(Arrays.asList(names));
		paramsHashMap.put("aeID", dynamicStringArray);
		
		ArrayList dynamicStringArray2 = new ArrayList(2);
		String[] names2 = {"string", "IT"};
		dynamicStringArray2.addAll(Arrays.asList(names2));
		paramsHashMap.put("LanguageCode", dynamicStringArray2);
		
		locationsListForTest = new ArrayList <String> ();
		locationsListForTest.add("../../src/main/resources/descriptor/type_system/feature_type/POSDensityType.xml");
	
	}
	*/	
	
	/*
	 * Checks that the abbreviation density in META-INF/cani.txt is 0.0189393939393939, with the precision of 0.001.
	 */	
	/*
	@Test
	public void POSDensityAbbreviationFeatureTest() throws Exception {
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Abbreviation_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Abbreviation_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the adjective density in META-INF/cani.txt is 0.071969696969697, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityAdjectiveFeatureTest() throws Exception {		
		
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Adjective_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Adjective_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.071969696969697, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the possessive adjective density in META-INF/cani.txt is 0.0265151515151515, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityAdjectivePossessiveFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_AdjectivePossessive_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_AdjectivePossessive_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0265151515151515, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the singular adjective density in META-INF/cani.txt is 0.0265151515151515, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityAdjectiveSingularFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_AdjectiveSingular_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_AdjectiveSingular_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0492424242424242, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the plural adjective density in META-INF/cani.txt is 0.022727272727272728, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityAdjectivePluralFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_AdjectivePlural_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_AdjectivePlural_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.022727272727272728, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the adverb density in META-INF/cani.txt is 0.0606060606060606, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityAdverbFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Adverb_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Adverb_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0606060606060606, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the negation adverb density in META-INF/cani.txt is 0.00757575757575758, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityAdverbNegationFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_AdverbNegation_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_AdverbNegation_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.00757575757575758, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the article density in META-INF/cani.txt is 0.071969696969697, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityArticleFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Article_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Article_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.071969696969697, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the auxiliary verb density in META-INF/cani.txt is 0.0454545454545455, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityAuxiliaryVerbFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_AuxiliaryVerb_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_AuxiliaryVerb_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0454545454545455, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the cardinal number density in META-INF/cani.txt is 0.0189393939393939, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityCardinalNumberFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_CardinalNumber_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_CardinalNumber_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0189393939393939, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the conjunction density in META-INF/cani.txt is 0.0492424242424242, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityConjunctionFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Conjunction_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Conjunction_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0492424242424242, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the coordinating conjunction density in META-INF/cani.txt is 0.0454545454545455, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityCoordinatingConjunctionFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_CoordinatingConjunction_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_CoordinatingConjunction_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0454545454545455, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the coordinating conjunction density in META-INF/cani.txt is 0.00378787878787879, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityDemonstrativePronounFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_DemonstrativePronoun_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_DemonstrativePronoun_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.00378787878787879, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the determiner density in META-INF/cani.txt is 0.0871212121212121, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityDeterminerFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Determiner_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Determiner_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0871212121212121, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the emoticon density in META-INF/cani.txt is 0.0, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityEmoticonFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Emoticon_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Emoticon_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the finite verb density in META-INF/cani.txt is 0.13257575757575757, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityFiniteVerbFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_FiniteVerb_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_FiniteVerb_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.13257575757575757, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the finite verb density in META-INF/cani.txt is 0.0, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityForeignWordFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_ForeignWord_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_ForeignWord_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the functional density in META-INF/cani.txt is 0.481060606060606, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityFunctionalFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Functional_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Functional_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.481060606060606, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the indefinite pronoun density in META-INF/cani.txt is 0.0227272727272727, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityIndefinitePronounFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_IndefinitePronoun_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_IndefinitePronoun_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0227272727272727, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the infinite verb density in META-INF/cani.txt is 0.0340909090909091, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityInfiniteVerbFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_InfiniteVerb_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_InfiniteVerb_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0340909090909091, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the interjection density in META-INF/cani.txt is 0.0, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityInterjectionFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Interjection_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Interjection_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the interrogative pronoun density in META-INF/cani.txt is 0.0, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityInterrogativePronounFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_InterrogativePronoun_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_InterrogativePronoun_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the lexical density in META-INF/cani.txt is 0.522727272727273, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityLexicalFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Lexical_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Lexical_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.522727272727273, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the main verb density in META-INF/cani.txt is 0.174242424242424, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityMainVerbFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_MainVerb_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_MainVerb_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.174242424242424, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the modal density in META-INF/cani.txt is 0.0151515151515152, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityModalFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Modal_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Modal_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0151515151515152, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the modal verb density in META-INF/cani.txt is 0.0151515151515152, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityModalVerbFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_ModalVerb_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_ModalVerb_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0151515151515152, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the modifier density in META-INF/cani.txt is 0.132575757575758, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityModifierFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Modifier_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Modifier_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.132575757575758, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the modifier density in META-INF/cani.txt is 0.102272727272727, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityNonFiniteVerbFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_NonFiniteVerb_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_NonFiniteVerb_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.102272727272727, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the noun density in META-INF/cani.txt is 0.25, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityNounFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Noun_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Noun_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.25, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
		
	/*
	 * Checks that the ordinal number density in META-INF/cani.txt is 0.0, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityOrdinalNumberFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_OrdinalNumber_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_OrdinalNumber_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
		
	/*
	 * Checks that the past participle verb density in META-INF/cani.txt is 0.0454545454545455, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityPastParticipleVerbFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_PastParticipleVerb_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_PastParticipleVerb_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0454545454545455, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the personal pronoun density in META-INF/cani.txt is 0.0189393939393939, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityPersonalPronounFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_PersonalPronoun_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_PersonalPronoun_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0189393939393939, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the plural noun density in META-INF/cani.txt is 0.08333333333333333, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityPluralNounFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_PluralNoun_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_PluralNoun_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.08333333333333333, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the possessive pronoun density in META-INF/cani.txt is 0.0, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityPossessivePronounFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_PossesivePronoun_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_PossesivePronoun_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the possessive pronoun density in META-INF/cani.txt is 0.00378787878787879, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityPredeterminerFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Predeterminer_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Predeterminer_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.00378787878787879, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the preposition density in META-INF/cani.txt is 0.140151515151515, with the precision of 0.0000001.
	 */
	/*
	@Test
	public void POSDensityPrepositionFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Preposition_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Preposition_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.140151515151515, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the pronoun density in META-INF/cani.txt is 0.0833333333333333, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityPronounFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Pronoun_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Pronoun_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0833333333333333, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the proper noun density in META-INF/cani.txt is 0.0340909090909091, with the precision of 0.0000001.
	 */
	/*
	@Test
	public void POSDensityProperNounFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_ProperNoun_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_ProperNoun_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0340909090909091, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the punctuation density in META-INF/cani.txt is 0.117424242424242, with the precision of 0.0000001.
	 */
	/*
	@Test
	public void POSDensityPunctuationFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Punctuation_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Punctuation_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.117424242424242, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the relative pronoun density in META-INF/cani.txt is 0.0265151515151515, with the precision of 0.0000001.
	 */
	/*
	@Test
	public void POSDensityRelativePronounFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_RelativePronoun_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_RelativePronoun_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0265151515151515, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the singular noun density in META-INF/cani.txt is 0.125, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensitySingularNounFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_SingularNoun_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_SingularNoun_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.125, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the subordinating conjunction density in META-INF/cani.txt is 0.00378787878787879, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensitySubordinatingConjunctionFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_SubordinatingConjunction_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_SubordinatingConjunction_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.00378787878787879, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the symbol density in META-INF/cani.txt is 0.0, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensitySymbolFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Symbol_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Symbol_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the twitter tag density in META-INF/cani.txt is 0.0, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityTwitterTagFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_TwitterTag_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_TwitterTag_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the verb density in META-INF/cani.txt is 0.234848484848485, with the precision of 0.0000001.
	 */
	/*
	@Test
	public void POSDensityVerbFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Verb_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Verb_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.234848484848485, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the 3d person verb density in META-INF/cani.txt is 0.106060606060606, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityVerb3dPersonFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_Verb3dPerson_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_Verb3dPerson_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.106060606060606, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the conditional present verb density in META-INF/cani.txt is 0.0, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityVerbConditionalFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_VerbConditional_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_VerbConditional_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the conjunctive verb density in META-INF/cani.txt is 0.00757575757575758, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityVerbConjunctiveFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_VerbConjunctive_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_VerbConjunctive_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.00757575757575758, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the conjunctive imperfect verb density in META-INF/cani.txt is 0.0, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityVerbConjunctiveImperfectFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_VerbConjunctiveImperfect_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_VerbConjunctiveImperfect_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the conjunctive present verb density in META-INF/cani.txt is 0.0, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityVerbConjunctivePresentFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_VerbConjunctivePresent_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_VerbConjunctivePresent_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.00757575757575758, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the imperative verb density in META-INF/cani.txt is 0.0, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityVerbImperativeFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_VerbImperative_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_VerbImperative_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the indicative verb density in META-INF/cani.txt is 0.125, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityVerbIndicativeFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_VerbIndicative_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_VerbIndicative_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.125, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the indicative future verb density in META-INF/cani.txt is 0.0, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityVerbIndicativeFutureFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_VerbIndicativeFuture_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_VerbIndicativeFuture_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.0, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the indicative imperfect verb density in META-INF/cani.txt is 0.00378787878787879, with the precision of 0.0000001.
	 */
	/*
	@Test
	public void POSDensityVerbIndicativeImperfectFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_VerbIndicativeImperfect_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_VerbIndicativeImperfect_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.00378787878787879, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the indicative past verb density in META-INF/cani.txt is 0.00378787878787879, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityVerbIndicativePastFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_VerbIndicativePast_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_VerbIndicativePast_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.00378787878787879, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the indicative past verb density in META-INF/cani.txt is 0.117424242424242, with the precision of 0.0000001.
	 */	
	/*
	@Test
	public void POSDensityVerbIndicativePresentFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/POSDensity_VerbIndicativePresent_Feature.xml", "./META-INF/org.apache.uima.fit/POSDensity_VerbIndicativePresent_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aedNToken, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 333){
				assertEquals(0.117424242424242, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
}
