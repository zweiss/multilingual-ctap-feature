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

public class MeanTokenLengthFeatureTest {
	JCas jCas;
	XMLParser pars;
	AnalysisEngineDescription aedSent, aedToken, aedNToken, aedSyllable, aedNSyllable, aedLetter, aedNLetter;
	/*
	@Before
	public void setUp() throws Exception {
		pars = UIMAFramework.getXMLParser();
		
		TypeSystemDescription tsd = TypeSystemDescriptionFactory.createTypeSystemDescription();
				
		ArrayList<String> locationsList = new ArrayList<String>();
		locationsList.add("src/main/resources/descriptor/type_system/feature_type/ComplexityFeatureBaseType.xml");
		locationsList.add("src/main/resources/descriptor/type_system/feature_type/NTokenType.xml");
		locationsList.add("src/main/resources/descriptor/type_system/feature_type/NSyllableType.xml");
		locationsList.add("src/main/resources/descriptor/type_system/feature_type/NLetterType.xml");
		
		DescriptorModifier.readXMLTypeDescriptorModifyImports ("src/main/resources/descriptor/type_system/feature_type/MeanTokenLengthType.xml", "./META-INF/org.apache.uima.fit/MeanTokenLengthTypeForUIMAFitTest.xml", locationsList);
		String sdSentenceLengthTypeDescr = new String(Files.readAllBytes(Paths.get("./META-INF/org.apache.uima.fit/MeanTokenLengthTypeForUIMAFitTest.xml")));
		
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
		
		File fNToken = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddaeID ("src/main/resources/descriptor/featureAE/NTokenFeature.xml", "./META-INF/org.apache.uima.fit/NTokenFeatureForUIMAFitTest.xml", "IT", "123");
		XMLInputSource xmlInputSourceNToken = new XMLInputSource(fNToken);
		aedNToken = pars.parseAnalysisEngineDescription(xmlInputSourceNToken);
		
		File fSyllable = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguage ("src/main/resources/descriptor/annotator/SyllableAnnotator.xml", "./META-INF/org.apache.uima.fit/SyllableAnnotatorForUIMAFitTest.xml", "IT");
		XMLInputSource xmlInputSourceSyllable = new XMLInputSource(fSyllable);
		aedSyllable = pars.parseAnalysisEngineDescription(xmlInputSourceSyllable);
		
		File fNSyllable = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddaeID ("src/main/resources/descriptor/featureAE/NSyllableFeature.xml", "./META-INF/org.apache.uima.fit/NSyllableFeatureForUIMAFitTest.xml", "IT", "777");
		XMLInputSource xmlInputSourceNSyllable = new XMLInputSource(fNSyllable);
		aedNSyllable = pars.parseAnalysisEngineDescription(xmlInputSourceNSyllable);
		
		File fLetter = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguage ("src/main/resources/descriptor/annotator/LetterAnnotator.xml", "./META-INF/org.apache.uima.fit/LetterAnnotatorForUIMAFitTest.xml", "IT");
		XMLInputSource xmlInputSourceLetter = new XMLInputSource(fLetter);
		aedLetter = pars.parseAnalysisEngineDescription(xmlInputSourceLetter);
		
		File fNLetter = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddaeID ("src/main/resources/descriptor/featureAE/NLetterFeature.xml", "./META-INF/org.apache.uima.fit/NLetterFeatureForUIMAFitTest.xml", "IT", "777");
		XMLInputSource xmlInputSourceNLetter = new XMLInputSource(fNLetter);
		aedNLetter = pars.parseAnalysisEngineDescription(xmlInputSourceNLetter);
		
	}
	*/
		
	/*
	 * Checks that the mean token length in letters in META-INF/cani.txt is 4.988636363636363, with the precision of 0.001.
	 */
	/*
	@Test
	public void MeanTokenLengthInLetterFeatureTest() throws Exception {
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddUnitAddaeID ("src/main/resources/descriptor/featureAE/MeanTokenLengthInLetterFeature.xml", "./META-INF/org.apache.uima.fit/MeanTokenLengthInTokenFeatureForUIMAFitTest.xml", "IT", "unit", "letter", "12222");
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedNToken, aedSyllable, aedNSyllable, aedLetter, aedNLetter, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 12222){
				assertEquals(4.988636363636363, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
	
	/*
	 * Checks that the mean token length in syllables in META-INF/cani.txt is 2.1666666666666665, with the precision of 0.0000001.
	 */
	/*
	@Test
	public void MeanTokenLengthInSyllableFeatureTest() throws Exception {
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguageAddUnitAddaeID ("src/main/resources/descriptor/featureAE/MeanTokenLengthInSyllableFeature.xml", "./META-INF/org.apache.uima.fit/MeanTokenLengthInSyllableFeatureForUIMAFitTest.xml", "IT", "unit", "syllable", "12222");
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aedNToken, aedSyllable, aedNSyllable, aedLetter, aedNLetter, aed);
	
		for(ComplexityFeatureBase annot : JCasUtil.select(jCas, ComplexityFeatureBase.class)){
			if(annot.getId() == 12222){
				assertEquals(2.1666666666666665, annot.getValue(), 0.0000001);
			}
		}
	}
	*/
}
