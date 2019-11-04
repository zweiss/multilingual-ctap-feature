package com.ctapweb.feature.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.XMLInputSource;
import org.apache.uima.util.XMLParser;
import org.junit.Test;
import org.w3c.dom.Document;

public class LetterAnnotatorTest {
	@Test
	public void annotateLettersItalianTest() throws Exception {
		
		XMLParser pars = UIMAFramework.getXMLParser();
		
		TypeSystemDescription tsd = TypeSystemDescriptionFactory.createTypeSystemDescription();		
				
		String letterTypeDescr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
				"<typeSystemDescription xmlns=\"http://uima.apache.org/resourceSpecifier\">"+
				"<name>Letter Linguistic Type</name>"+
				"<description>This is the linguistic type of Letter, which extends the UIMA Annotation type."+
				"As a result, it contains the features of Begin and End."+
				"It is used for annotating a alphabetical letter in the English language."+
				"Dependency:"+
				"Linguistic_type: Token</description>"+
				"<version>1.0</version>"+
				"<vendor>xiaobin</vendor>"+
				"<imports>"+
				"<import location=\"src/main/resources/descriptor/type_system/linguistic_type/TokenType.xml\"/>"+
				"</imports>"+
				"<types>"+
					"<typeDescription>"+
						"<name>com.ctapweb.feature.type.Letter</name>"+
						"<description>The letter type.</description>"+
						"<supertypeName>uima.tcas.Annotation</supertypeName>"+
					"</typeDescription>"+
				"</types>"+
				"</typeSystemDescription>";
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new ByteArrayInputStream(letterTypeDescr.getBytes("UTF-8")));
		
		tsd.buildFromXMLElement(doc.getDocumentElement(), pars);
	    JCas jCas = CasCreationUtils.createCas(tsd, null, null).getJCas();
		
	    String contents = new String(Files.readAllBytes(Paths.get("./META-INF/cani.txt")));
		jCas.setDocumentText(contents);
		
		File fSent = new File("./META-INF/org.apache.uima.fit/SentenceAnnotatorForUIMAFitTest.xml");
		XMLInputSource xmlInputSourceSent = new XMLInputSource(fSent);
		AnalysisEngineDescription aedSent = pars.parseAnalysisEngineDescription(xmlInputSourceSent);
		
		File fToken = new File("./META-INF/org.apache.uima.fit/TokenAnnotatorForUIMAFitTest.xml");
		XMLInputSource xmlInputSourceToken = new XMLInputSource(fToken);
		AnalysisEngineDescription aedToken = pars.parseAnalysisEngineDescription(xmlInputSourceToken);
		
		File f = new File("./META-INF/org.apache.uima.fit/LetterAnnotatorForUIMAFitTest.xml");
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aed);

		int n = 0;
		for(Annotation annot : JCasUtil.select(jCas, Annotation.class)){
			System.out.println("Found annotation: " + annot.getCoveredText());
			System.out.println("annot.getTypeIndexID(): " + annot.getTypeIndexID());
			if(annot.getTypeIndexID() == 25){
				System.out.println(annot.getCoveredText());
				n += 1;
			}
		}
		
		assertEquals(1317, n); 
	}
}
