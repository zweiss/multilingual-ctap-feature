package com.ctapweb.feature.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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

public class SDTokenLengthFeatureTest {
	JCas jCas;
	XMLParser pars;
	AnalysisEngineDescription aedSent, aedToken, aedSyllable, aedLetter;
	
	@Before
	public void setUp() throws Exception {
		pars = UIMAFramework.getXMLParser();
		
		TypeSystemDescription tsd = TypeSystemDescriptionFactory.createTypeSystemDescription();
				
		ArrayList<String> locationsList = new ArrayList<String>();
		locationsList.add("src/main/resources/descriptor/type_system/feature_type/ComplexityFeatureBaseType.xml");
		locationsList.add("src/main/resources/descriptor/type_system/linguistic_type/TokenType.xml");
		locationsList.add("src/main/resources/descriptor/type_system/linguistic_type/SyllableType.xml");
		locationsList.add("src/main/resources/descriptor/type_system/linguistic_type/LetterType.xml");
		
		DescriptorModifier.readXMLTypeDescriptorModifyImports ("src/main/resources/descriptor/type_system/feature_type/SDTokenLengthType.xml", "./META-INF/org.apache.uima.fit/SDTokenLengthTypeForUIMAFitTest.xml", locationsList);
		String sdTokenLengthTypeDescr = new String(Files.readAllBytes(Paths.get("./META-INF/org.apache.uima.fit/SDTokenLengthTypeForUIMAFitTest.xml")));
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new ByteArrayInputStream(sdTokenLengthTypeDescr.getBytes("UTF-8")));
		
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
	
	/*
	 * Checks that the Standard Deviation from the token length in tokens in META-INF/cani.txt is 2.973243104638609, with the precision of 0.001.
	 */
	@Test
	public void SDTokenLengthInLetterFeatureTest() throws Exception {
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddUnitAddaeID ("src/main/resources/descriptor/featureAE/SDTokenLengthInLetterFeature.xml", "./META-INF/org.apache.uima.fit/SDTokenLengthInLetterFeatureForUIMAFitTest.xml", "IT", "unit", "letter", "8585");
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedSyllable, aedLetter, aed);
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 8585){
				assertEquals(2.973243104638609, annot.getValue(), 0.0000001);
			}
		}
	}
	
	/*
	 * Checks that the Standard Deviation from the token length in tokens in META-INF/cani.txt is 1.1677977497340455, with the precision of 0.001.
	 */
	@Test
	public void SDTokenLengthInSyllableFeatureTest() throws Exception {
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddUnitAddaeID ("src/main/resources/descriptor/featureAE/SDTokenLengthInSyllableFeature.xml", "./META-INF/org.apache.uima.fit/SDTokenLengthInSyllableFeatureForUIMAFitTest.xml", "IT", "unit", "syllable", "8585");
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedSyllable, aedLetter, aed);
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 8585){
				assertEquals(1.1677977497340455, annot.getValue(), 0.0000001);
			}	
		}
	}
}
