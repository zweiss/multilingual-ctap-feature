package com.ctapweb.feature.test;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.XMLInputSource;
import org.apache.uima.util.XMLParser;
import org.apache.uima.UIMAFramework;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.tcas.Annotation;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class TokenAnnotatorTest{
	
	@Test
	public void annotateTokensItalianTest() throws Exception {
		
		XMLParser pars = UIMAFramework.getXMLParser();
		
		TypeSystemDescription tsd = TypeSystemDescriptionFactory.createTypeSystemDescription();		
				
		String tokenTypeDescr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
		"<typeSystemDescription xmlns=\"http://uima.apache.org/resourceSpecifier\">"+
			"<name>Token Linguistic Type</name>"+
			"<description>This is the linguistic type of Token, which extends the"+
				"UIMA Annotation type."+
				"As a result, it contains the features of Begin and End."+
				"It is used for annotating a token in in the following languages:"+
				"English, German, Italian"+
				"Dependency:"+
				"Linguistic_type: Sentence</description>"+
			"<version>1.0</version>"+
			"<vendor>xiaobin</vendor>"+
			"<imports>"+
				"<import location=\"src/main/resources/descriptor/type_system/linguistic_type/SentenceType.xml\"/>"+
			"</imports>"+
			"<types>"+
				"<typeDescription>"+
					"<name>com.ctapweb.feature.type.Token</name>"+
					"<description>The token type.</description>"+
					"<supertypeName>uima.tcas.Annotation</supertypeName>"+
				"</typeDescription>"+
			"</types>"+
		"</typeSystemDescription>";
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new ByteArrayInputStream(tokenTypeDescr.getBytes("UTF-8")));
		
		tsd.buildFromXMLElement(doc.getDocumentElement(), pars);
	    JCas jCas = CasCreationUtils.createCas(tsd, null, null).getJCas();
		
	    String contents = new String(Files.readAllBytes(Paths.get("./META-INF/cani.txt")));
		jCas.setDocumentText(contents);
		
		File fSent = new File("./META-INF/org.apache.uima.fit/SentenceAnnotatorForUIMAFitTest.xml");
		XMLInputSource xmlInputSourceSent = new XMLInputSource(fSent);
		AnalysisEngineDescription aedSent = pars.parseAnalysisEngineDescription(xmlInputSourceSent);
		
		File f = new File("./META-INF/org.apache.uima.fit/TokenAnnotatorForUIMAFitTest.xml");
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		SimplePipeline.runPipeline(jCas, aedSent, aed);

		int n = 0;
		for(Annotation annot : JCasUtil.select(jCas, Annotation.class)){
			System.out.println("Found annotation: " + annot.getCoveredText());
			System.out.println("annot.getTypeIndexID(): " + annot.getTypeIndexID());
			if(annot.getTypeIndexID() == 26){
				n += 1;
			}
		}
		
		assertEquals(295, n); // 295, because I didn't exclude punctuations. Otherwise it would be 264.
	}
}
