package com.ctapweb.feature.test;

import com.ctapweb.feature.test.util.DescriptorModifier;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.AnnotationBase;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.XMLInputSource;
import org.apache.uima.util.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import com.ctapweb.feature.type.SDSentenceLength;
import com.ctapweb.feature.type.Syllable;

import com.ctapweb.feature.type.ComplexityFeatureBase;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * Tests the SDSentenceLengthInToken Feature.
 * @author Nadezda Okinina
 */

public class SDSentenceLengthFeatureTest {
	JCas jCas;
	XMLParser pars;
	AnalysisEngineDescription aedSent, aedToken, aedSyllable, aedLetter;
	/*
	@Before
	public void setUp() throws Exception {
		pars = UIMAFramework.getXMLParser();
		
		TypeSystemDescription tsd = TypeSystemDescriptionFactory.createTypeSystemDescription();
				
		ArrayList<String> locationsList = new ArrayList<String>();
		locationsList.add("src/main/resources/descriptor/type_system/feature_type/ComplexityFeatureBaseType.xml");
		locationsList.add("src/main/resources/descriptor/type_system/linguistic_type/SentenceType.xml");
		locationsList.add("src/main/resources/descriptor/type_system/linguistic_type/TokenType.xml");
		locationsList.add("src/main/resources/descriptor/type_system/linguistic_type/SyllableType.xml");
		locationsList.add("src/main/resources/descriptor/type_system/linguistic_type/LetterType.xml");
		
		DescriptorModifier.readXMLTypeDescriptorModifyImports ("src/main/resources/descriptor/type_system/feature_type/SDSentenceLengthType.xml", "./META-INF/org.apache.uima.fit/SDSentenceLengthTypeForUIMAFitTest.xml", locationsList);
		String sdSentenceLengthTypeDescr = new String(Files.readAllBytes(Paths.get("./META-INF/org.apache.uima.fit/SDSentenceLengthTypeForUIMAFitTest.xml")));
		
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
		
		File fSyllable = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguage ("src/main/resources/descriptor/annotator/SyllableAnnotator.xml", "./META-INF/org.apache.uima.fit/SyllableAnnotatorForUIMAFitTest.xml", "IT");
		XMLInputSource xmlInputSourceSyllable = new XMLInputSource(fSyllable);
		aedSyllable = pars.parseAnalysisEngineDescription(xmlInputSourceSyllable);
		
		File fLetter = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguage ("src/main/resources/descriptor/annotator/LetterAnnotator.xml", "./META-INF/org.apache.uima.fit/LetterAnnotatorForUIMAFitTest.xml", "IT");
		XMLInputSource xmlInputSourceLetter = new XMLInputSource(fLetter);
		aedLetter = pars.parseAnalysisEngineDescription(xmlInputSourceLetter);
		
	}
	*/
	
	/*
	 * Checks that the Standard Deviation from the sentence length in tokens in META-INF/cani.txt is 18.983246201944397, with the precision of 0.001.
	 */
	/*
	@Test
	public void SDSentenceLengthInTokenFeatureTest() throws Exception {
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddUnitAddaeID ("src/main/resources/descriptor/featureAE/SDSentenceLengthInTokenFeature.xml", "./META-INF/org.apache.uima.fit/SDSentenceLengthInTokenFeatureForUIMAFitTest.xml", "IT", "unit", "token", "9999");
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedSyllable, aedLetter, aed);
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			System.out.println(annot.toString());
			System.out.println(annot.getId());
			System.out.println(annot.getValue());
			if(annot.getId() == 9999){
				assertEquals(18.983246201944397, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the Standard Deviation from the sentence length in tokens in META-INF/cani.txt is 99.31412515118609, with the precision of 0.001.
	 */
	/*
	@Test
	public void SDSentenceLengthInLetterFeatureTest() throws Exception {
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddUnitAddaeID ("src/main/resources/descriptor/featureAE/SDSentenceLengthInLetterFeature.xml", "./META-INF/org.apache.uima.fit/SDSentenceLengthInLetterFeatureForUIMAFitTest.xml", "IT", "unit", "letter", "9999");
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedSyllable, aedLetter, aed);
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 9999){
				assertEquals(99.31412515118609, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the Standard Deviation from the sentence length in tokens in META-INF/cani.txt is 42.58965587884912, with the precision of 0.001.
	 */
	/*
	@Test
	public void SDSentenceLengthInSyllableFeatureTest() throws Exception {
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddUnitAddaeID ("src/main/resources/descriptor/featureAE/SDSentenceLengthInSyllableFeature.xml", "./META-INF/org.apache.uima.fit/SDSentenceLengthInSyllableFeatureForUIMAFitTest.xml", "IT", "unit", "syllable", "9999");
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedSyllable, aedLetter, aed);
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 9999){
				assertEquals(42.58965587884912, annot.getValue(), 0.0000001);
			}	
		}
	}
	*/
}
