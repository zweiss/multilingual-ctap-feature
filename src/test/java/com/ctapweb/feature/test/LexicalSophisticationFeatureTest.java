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

public class LexicalSophisticationFeatureTest {
	
	JCas jCas;
	XMLParser pars;
	AnalysisEngineDescription aedSent, aedToken, aedPOS;
	HashMap <String, ArrayList <String>> paramsHashMap;
	ArrayList<String> locationsListForTest;
	
	@Before
	public void setUp() throws Exception {
		
		pars = UIMAFramework.getXMLParser();
		
		TypeSystemDescription tsd = TypeSystemDescriptionFactory.createTypeSystemDescription();
		
		ArrayList<String> locationsList = new ArrayList<String>();
		locationsList.add("src/main/resources/descriptor/type_system/feature_type/ComplexityFeatureBaseType.xml");
		locationsList.add("src/main/resources/descriptor/type_system/linguistic_type/POSType.xml");
		
		DescriptorModifier.readXMLTypeDescriptorModifyImports ("src/main/resources/descriptor/type_system/feature_type/LexicalSophisticationType.xml", "./META-INF/org.apache.uima.fit/LexicalSophisticationTypeForUIMAFitTest.xml", locationsList);
		String sdSentenceLengthTypeDescr = new String(Files.readAllBytes(Paths.get("./META-INF/org.apache.uima.fit/LexicalSophisticationTypeForUIMAFitTest.xml")));
		
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
		
		paramsHashMap = new HashMap <String, ArrayList <String>> ();		
		ArrayList dynamicStringArray = new ArrayList(2);
		String[] names = {"integer", "123"};
		dynamicStringArray.addAll(Arrays.asList(names));
		paramsHashMap.put("aeID", dynamicStringArray);
		
		ArrayList dynamicStringArray2 = new ArrayList(2);
		String[] names2 = {"string", "IT"};
		dynamicStringArray2.addAll(Arrays.asList(names2));
		paramsHashMap.put("LanguageCode", dynamicStringArray2);
		
		locationsListForTest = new ArrayList <String> ();
		locationsListForTest.add("../../src/main/resources/descriptor/type_system/linguistic_type/POSType.xml");
	}
	
	//---------------Google 2012  Familiarity----------------
	/*
	 * Checks that the Lexical Sophistication Feature: Google Books Word Familiarity Per Million Words (AW Token) for META-INF/cani.txt is 3.789217471480068E8, with the precision of 0.0000001.
	 */	
	@Test
	public void LexicalSophisticationGoogle00FamiliarityAWTokenFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Familiarity_AW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Familiarity_AW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 123){
				assertEquals(3.789217471480068E8, annot.getValue(), 0.0000001); // 378921747.148007
			}
		}
	}
	
	/*
	 * Checks that the Lexical Sophistication Feature: Google Books Word Familiarity Per Million Words (AW Type) for META-INF/cani.txt is 1.5713620613951483E8, with the precision of 0.0000001.
	 */	
	@Test
	public void LexicalSophisticationGoogle00FamiliarityAWTypeFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Familiarity_AW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Familiarity_AW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 123){
				assertEquals(1.5713620613951483E8, annot.getValue(), 0.0000001);
			}
		}
	}
	
	/*
	 * Checks that the Lexical Sophistication Feature: Google Books Word Familiarity Per Million Words (FW Token) for META-INF/cani.txt is 7.374125502753642E8, with the precision of 0.0000001.
	 */	
	@Test
	public void LexicalSophisticationGoogle00FamiliarityFWTokenFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Familiarity_FW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Familiarity_FW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 123){
				assertEquals(7.374125502753642E8, annot.getValue(), 0.0000001);
			}
		}
	}
	
	
	/*
	 * Checks that the Lexical Sophistication Feature: Google Books Word Familiarity Per Million Words (FW Type) for META-INF/cani.txt is 3.695195652914551E8, with the precision of 0.0000001.
	 */	
	@Test
	public void LexicalSophisticationGoogle00FamiliarityFWTypeFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Familiarity_FW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Familiarity_FW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 123){
				assertEquals(3.695195652914551E8, annot.getValue(), 0.0000001);
			}
		}
	}
	
	
	/*
	 * Checks that the Lexical Sophistication Feature: Google Books Word Familiarity Per Million Words (LW Token) for META-INF/cani.txt is 3.953182800122403E7, with the precision of 0.0000001.
	 */	
	@Test
	public void LexicalSophisticationGoogle00FamiliarityLWTokenFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Familiarity_LW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Familiarity_LW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 123){
				assertEquals(3.953182800122403E7, annot.getValue(), 0.0000001);
			}
		}
	}
	
	
	/*
	 * Checks that the Lexical Sophistication Feature: Google Books Word Familiarity Per Million Words (LW Type) for META-INF/cani.txt is 3.3062107890640084E7, with the precision of 0.0000001.
	 */	
	@Test
	public void LexicalSophisticationGoogle00FamiliarityLWTypeFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Familiarity_LW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Familiarity_LW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 123){
				assertEquals(3.3062107890640084E7, annot.getValue(), 0.0000001);
			}
		}
	}
	
	
	//---------------Google 2012  Informativeness----------------
	/*
	 * Checks that the Lexical Sophistication Feature: Google Books Word Informativeness Per Million Words (AW Token) for META-INF/cani.txt is 91.27076381897876, with the precision of 0.0000001.
	 */	
	@Test
	public void LexicalSophisticationGoogle00InformativenessAWTokenFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Informativeness_AW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Informativeness_AW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 123){
				assertEquals(91.27076381897876, annot.getValue(), 0.0000001);
			}
		}
	}
	
	/*
	 * Checks that the Lexical Sophistication Feature: Google Books Word Informativeness Per Million Words (AW Type) for META-INF/cani.txt is 117.7134747143073, with the precision of 0.0000001.
	 */	
	@Test
	public void LexicalSophisticationGoogle00InformativenessAWTypeFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Informativeness_AW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Informativeness_AW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 123){
				assertEquals(117.7134747143073, annot.getValue(), 0.0000001);
			}
		}
	}
	
	/*
	 * Checks that the Lexical Sophistication Feature: Google Books Word Informativeness Per Million Words (FW Token) for META-INF/cani.txt is 11.464919427389937, with the precision of 0.0000001.
	 */	
	@Test
	public void LexicalSophisticationGoogle00InformativenessFWTokenFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Informativeness_FW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Informativeness_FW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 123){
				assertEquals(11.464919427389937, annot.getValue(), 0.0000001);
			}
		}
	}
	
	
	/*
	 * Checks that the Lexical Sophistication Feature: Google Books Word Informativeness Per Million Words (FW Type) for META-INF/cani.txt is 20.374627715807016, with the precision of 0.0000001.
	 */	
	@Test
	public void LexicalSophisticationGoogle00InformativenessFWTypeFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Informativeness_FW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Informativeness_FW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 123){
				assertEquals(20.374627715807016, annot.getValue(), 0.0000001);
			}
		}
	}
	
	
	/*
	 * Checks that the Lexical Sophistication Feature: Google Books Word Informativeness Per Million Words (LW Token) for META-INF/cani.txt is 165.88767438813557, with the precision of 0.0000001.
	 */	
	@Test
	public void LexicalSophisticationGoogle00InformativenessLWTokenFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Informativeness_LW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Informativeness_LW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 123){
				assertEquals(165.88767438813557, annot.getValue(), 0.0000001);
			}
		}
	}
	
	
	/*
	 * Checks that the Lexical Sophistication Feature: Google Books Word Informativeness Per Million Words (LW Type) for META-INF/cani.txt is 173.35201668131904, with the precision of 0.0000001.
	 */	
	@Test
	public void LexicalSophisticationGoogle00InformativenessLWTypeFeatureTest() throws Exception {		
	
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Informativeness_LW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Informativeness_LW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 123){
				assertEquals(173.35201668131904, annot.getValue(), 0.0000001);
			}
		}
	}
	
	//---------------Google 2012  Log10WFInMillion----------------
		/*
		 * Checks that the Lexical Sophistication Feature: Google Books Word Log10 FW In Million Words (AW Token) for META-INF/cani.txt is 7.283676476436987, with the precision of 0.0000001.
		 */	
		@Test
		public void LexicalSophisticationGoogle00Log10WFInMillionAWTokenFeatureTest() throws Exception {		
		
			File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Log10WFInMillion_AW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Log10WFInMillion_AW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
			XMLInputSource xmlInputSource = new XMLInputSource(f);
			AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
			
			//Run the analysis pipeline
			SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
		
			for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
				if(annot.getId() == 123){
					assertEquals(7.283676476436987, annot.getValue(), 0.0000001); 
				}
			}
		}
		
		/*
		 * Checks that the Lexical Sophistication Feature: Google Books Word Log10 FW In Million Words (AW Type) for META-INF/cani.txt is 6.808967777362547, with the precision of 0.0000001.
		 */	
		@Test
		public void LexicalSophisticationGoogle00Log10WFInMillionAWTypeFeatureTest() throws Exception {		
		
			File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Log10WFInMillion_AW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Log10WFInMillion_AW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
			XMLInputSource xmlInputSource = new XMLInputSource(f);
			AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
			
			//Run the analysis pipeline
			SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
		
			for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
				if(annot.getId() == 123){
					assertEquals(6.808967777362547, annot.getValue(), 0.0000001);
				}
			}
		}
		
		/*
		 * Checks that the Lexical Sophistication Feature: Google Books Word Log10 FW In Million Words (FW Token) for META-INF/cani.txt is 8.361300153457918, with the precision of 0.0000001.
		 */	
		@Test
		public void LexicalSophisticationGoogle00Log10WFInMillionFWTokenFeatureTest() throws Exception {		
		
			File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Log10WFInMillion_FW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Log10WFInMillion_FW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
			XMLInputSource xmlInputSource = new XMLInputSource(f);
			AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
			
			//Run the analysis pipeline
			SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
		
			for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
				if(annot.getId() == 123){
					assertEquals(8.361300153457918, annot.getValue(), 0.0000001);
				}
			}
		}
		
		
		/*
		 * Checks that the Lexical Sophistication Feature: Google Books Word Log10 FW In Million Words (FW Type) for META-INF/cani.txt is 7.925231615479594, with the precision of 0.0000001.
		 */	
		@Test
		public void LexicalSophisticationGoogle00Log10WFInMillionFWTypeFeatureTest() throws Exception {		
		
			File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Log10WFInMillion_FW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Log10WFInMillion_FW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
			XMLInputSource xmlInputSource = new XMLInputSource(f);
			AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
			
			//Run the analysis pipeline
			SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
		
			for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
				if(annot.getId() == 123){
					assertEquals(7.925231615479594, annot.getValue(), 0.0000001);
				}
			}
		}
		
		
		/*
		 * Checks that the Lexical Sophistication Feature: Google Books Word Log10 FW In Million Words (LW Token) for META-INF/cani.txt is 6.25645736477395, with the precision of 0.0000001.
		 */	
		@Test
		public void LexicalSophisticationGoogle00Log10WFInMillionLWTokenFeatureTest() throws Exception {		
		
			File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Log10WFInMillion_LW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Log10WFInMillion_LW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
			XMLInputSource xmlInputSource = new XMLInputSource(f);
			AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
			
			//Run the analysis pipeline
			SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
		
			for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
				if(annot.getId() == 123){
					assertEquals(6.25645736477395, annot.getValue(), 0.0000001);
				}
			}
		}
		
		
		/*
		 * Checks that the Lexical Sophistication Feature: Google Books Word Log10 FW In Million Words (LW Type) for META-INF/cani.txt is 6.149629204366376, with the precision of 0.0000001.
		 */	
		@Test
		public void LexicalSophisticationGoogle00Log10WFInMillionLWTypeFeatureTest() throws Exception {		
		
			File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00_Log10WFInMillion_LW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00_Log10WFInMillion_LW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
			XMLInputSource xmlInputSource = new XMLInputSource(f);
			AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
			
			//Run the analysis pipeline
			SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
		
			for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
				if(annot.getId() == 123){
					assertEquals(6.149629204366376, annot.getValue(), 0.0000001);
				}
			}
		}
		
		//---------------Google 2012  Log10WF----------------
				/*
				 * Checks that the Lexical Sophistication Feature: Google Books Word Log10 WF (AW Token) for META-INF/cani.txt is 7.238204078990767, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationGoogle00Log10WFAWTokenFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00Log10WF_AW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00Log10WF_AW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(7.238204078990767, annot.getValue(), 0.0000001); 
						}
					}
				}
				
				/*
				 * Checks that the Lexical Sophistication Feature: Google Books Word Log10 WF (AW Type) for META-INF/cani.txt is 6.763495379916329, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationGoogle00Log10WFAWTypeFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00Log10WF_AW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00Log10WF_AW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(6.763495379916329, annot.getValue(), 0.0000001);
						}
					}
				}
				
				/*
				 * Checks that the Lexical Sophistication Feature: Google Books Word Log10 WF (FW Token) for META-INF/cani.txt is 8.315827756011696, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationGoogle00Log10WFFWTokenFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00Log10WF_FW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00Log10WF_FW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(8.315827756011696, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				/*
				 * Checks that the Lexical Sophistication Feature: Google Books Word Log10 WF (FW Type) for META-INF/cani.txt is 7.879759218033367, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationGoogle00Log10WFFWTypeFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00Log10WF_FW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00Log10WF_FW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(7.879759218033367, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				/*
				 * Checks that the Lexical Sophistication Feature: Google Books Word Log10 WF (LW Token) for META-INF/cani.txt is 6.210984967327724, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationGoogle00Log10WFLWTokenFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00Log10WF_LW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00Log10WF_LW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(6.210984967327724, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				/*
				 * Checks that the Lexical Sophistication Feature: Google Books Word Log10 WF (LW Type) for META-INF/cani.txt is 6.149629204366376, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationGoogle00Log10WFLWTypeFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00Log10WF_LW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00Log10WF_LW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(6.104156806920154, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				//---------------Google 2012   WF----------------
				/*
				 * Checks that the Lexical Sophistication Feature: Google Books WF (AW Token) for META-INF/cani.txt is 3.624385937336369E8, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationGoogle00WFAWTokenFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00WF_AW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00WF_AW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(3.624385937336369E8, annot.getValue(), 0.0000001); 
						}
					}
				}
				
				/*
				 * Checks that the Lexical Sophistication Feature: Google Books WF (AW Type) for META-INF/cani.txt is 1.3463907860349092E8, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationGoogle00WFAWTypeFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00WF_AW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00WF_AW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(1.3463907860349092E8, annot.getValue(), 0.0000001);
						}
					}
				}
				
				/*
				 * Checks that the Lexical Sophistication Feature: Google Books WF (FW Token) for META-INF/cani.txt is 7.191037479697275E8, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationGoogle00WFFWTokenFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00WF_FW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00WF_FW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(7.191037479697275E8, annot.getValue(), 0.0000001); // 719103747.969728
						}
					}
				}
				
				
				/*
				 * Checks that the Lexical Sophistication Feature: Google Books Word Log10 WF (FW Type) for META-INF/cani.txt is 3.3556183569967175E8, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationGoogle00WFFWTypeFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00WF_FW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00WF_FW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(3.3556183569967175E8, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				/*
				 * Checks that the Lexical Sophistication Feature: Google Books Word WF (LW Token) for META-INF/cani.txt is 2.4789218175908413E7, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationGoogle00WFLWTokenFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00WF_LW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00WF_LW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(2.4789218175908413E7, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				/*
				 * Checks that the Lexical Sophistication Feature: Google Books WF (LW Type) for META-INF/cani.txt is 1.7275882191081878E7, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationGoogle00WFLWTypeFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_Google00WF_LW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_Google00WF_LW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(1.7275882191081878E7, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				//---------------SUBTLEX  Familiarity----------------
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Word Familiarity (AW Token) for META-INF/cani.txt is 911597.132527755, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXFamiliarityAWTokenFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Familiarity_AW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Familiarity_AW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(911597.132527755, annot.getValue(), 0.0000001); // 911597.132527755
						}
					}
				}
				
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Word Familiarity (AW Type) for META-INF/cani.txt is 418056.6265819095, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXFamiliarityAWTypeFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Familiarity_AW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Familiarity_AW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(418056.6265819095, annot.getValue(), 0.0000001);
						}
					}
				}
				
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Word Familiarity (FW Token) for META-INF/cani.txt is 1635406.434504271, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXFamiliarityFWTokenFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Familiarity_FW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Familiarity_FW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(1635406.434504271, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Word Familiarity (FW Type) for META-INF/cani.txt is 861610.9125104927, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXFamiliarityFWTypeFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Familiarity_FW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Familiarity_FW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(861610.9125104927, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Word Familiarity (LW Token) for META-INF/cani.txt is 215064.3532570539, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXFamiliarityLWTokenFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Familiarity_LW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Familiarity_LW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(215064.3532570539, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Word Familiarity (LW Type) for META-INF/cani.txt is 154985.28082863658, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXFamiliarityLWTypeFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Familiarity_LW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Familiarity_LW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(154985.28082863658, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				//---------------SUBTLEX  Informativeness----------------
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Word Informativeness (AW Token) for META-INF/cani.txt is 48.91335076180772, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXInformativenessAWTokenFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Informativeness_AW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Informativeness_AW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(48.91335076180772, annot.getValue(), 0.0000001);
						}
					}
				}
				
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Word Informativeness (AW Type) for META-INF/cani.txt is 62.661115774186186, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXInformativenessAWTypeFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Informativeness_AW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Informativeness_AW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(62.661115774186186, annot.getValue(), 0.0000001);
						}
					}
				}
				
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Word Informativeness (FW Token) for META-INF/cani.txt is 6.3233286564121025, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXInformativenessFWTokenFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Informativeness_FW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Informativeness_FW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(6.3233286564121025, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Word Informativeness(FW Type) for META-INF/cani.txt is 10.283908164326132, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXInformativenessFWTypeFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Informativeness_FW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Informativeness_FW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(10.283908164326132, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Word Informativeness (LW Token) for META-INF/cani.txt is 89.54729597289108, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXInformativenessLWTokenFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Informativeness_LW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Informativeness_LW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(89.54729597289108, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Word Informativeness (LW Type) for META-INF/cani.txt is 93.23429948978692, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXInformativenessLWTypeFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Informativeness_LW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Informativeness_LW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(93.23429948978692, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				//---------------SUBTLEX  Log10WF In Million----------------
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Log10 WF In Million (AW Token) for META-INF/cani.txt is 4.908699559377197, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXLog10WFInMillionAWTokenFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Log10WFInMillion_AW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Log10WFInMillion_AW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(4.908699559377197, annot.getValue(), 0.0000001); 
						}
					}
				}
				
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Log10 WF In Million (AW Type) for META-INF/cani.txt is 4.43863858471746, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXLog10WFInMillionAWTypeFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Log10WFInMillion_AW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Log10WFInMillion_AW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(4.43863858471746, annot.getValue(), 0.0000001);
						}
					}
				}
				
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Log10 WF In Million (FW Token) for META-INF/cani.txt is 5.810062917577086, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXLog10WFInMillionFWTokenFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Log10WFInMillion_FW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Log10WFInMillion_FW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(5.810062917577086, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Log10 WF In Million (FW Type) for META-INF/cani.txt is 5.385605125683745, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXLog10WFInMillionFWTypeFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Log10WFInMillion_FW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Log10WFInMillion_FW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(5.385605125683745, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Log10 WF In Million (LW Token) for META-INF/cani.txt is 4.032161968501652, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXLog10WFInMillionLWTokenFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Log10WFInMillion_LW_Token_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Log10WFInMillion_LW_Token_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(4.032161968501652, annot.getValue(), 0.0000001);
						}
					}
				}
				
				
				/*
				 * Checks that the Lexical Sophistication Feature: SUBTLEX Log10 WF In Million (LW Type) for META-INF/cani.txt is 3.867910544488611, with the precision of 0.0000001.
				 */	
				@Test
				public void LexicalSophisticationSUBTLEXLog10WFInMillionLWTypeFeatureTest() throws Exception {		
				
					File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports ("src/main/resources/descriptor/featureAE/LexicalSophistication_SUBTLEX_Log10WFInMillion_LW_Type_Feature.xml", "./META-INF/org.apache.uima.fit/LexicalSophistication_SUBTLEX_Log10WFInMillion_LW_Type_FeatureForUIMAFitTest.xml", paramsHashMap, locationsListForTest);
					XMLInputSource xmlInputSource = new XMLInputSource(f);
					AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
					
					//Run the analysis pipeline
					SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedPOS, aed);
				
					for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
						if(annot.getId() == 123){
							assertEquals(3.867910544488611, annot.getValue(), 0.0000001);
						}
					}
				}
}
