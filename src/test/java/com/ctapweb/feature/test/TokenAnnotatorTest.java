package com.ctapweb.feature.test;

import com.ctapweb.feature.test.util.DescriptorModifier;
import com.ctapweb.feature.type.Token;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.XMLInputSource;
import org.apache.uima.util.XMLParser;
import org.apache.uima.UIMAFramework;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import org.w3c.dom.*;
import javax.xml.parsers.*;

/**
 * Tests the TokenAnnotator.
 * @author Nadezda Okinina
 */
public class TokenAnnotatorTest{
	
	JCas jCas;
	
	@Before
	public void setUp() throws Exception {
		XMLParser pars = UIMAFramework.getXMLParser();
		
		TypeSystemDescription tsd = TypeSystemDescriptionFactory.createTypeSystemDescription();
		
		DescriptorModifier.readXMLTypeDescriptorModifyImports ("src/main/resources/descriptor/type_system/linguistic_type/TokenType.xml", "./META-INF/org.apache.uima.fit/TokenTypeForUIMAFitTest.xml", "src/main/resources/descriptor/type_system/linguistic_type/SentenceType.xml");
		String tokenTypeDescr = new String(Files.readAllBytes(Paths.get("./META-INF/org.apache.uima.fit/TokenTypeForUIMAFitTest.xml")));
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new ByteArrayInputStream(tokenTypeDescr.getBytes("UTF-8")));
		
		
		tsd.buildFromXMLElement(doc.getDocumentElement(), pars);
	    jCas = CasCreationUtils.createCas(tsd, null, null).getJCas();
		
	    String contents = new String(Files.readAllBytes(Paths.get("./META-INF/cani.txt")));
		jCas.setDocumentText(contents);
		
		File fSent = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguage ("src/main/resources/descriptor/annotator/SentenceAnnotator.xml", "./META-INF/org.apache.uima.fit/SentenceAnnotatorForUIMAFitTest.xml");
		
		XMLInputSource xmlInputSourceSent = new XMLInputSource(fSent);
		AnalysisEngineDescription aedSent = pars.parseAnalysisEngineDescription(xmlInputSourceSent);
		
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguage ("src/main/resources/descriptor/annotator/TokenAnnotator.xml", "./META-INF/org.apache.uima.fit/TokenAnnotatorForUIMAFitTest.xml");
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		SimplePipeline.runPipeline(jCas, aedSent, aed);
	}
	
	/*
	 * Checks that the number of tokens in the file META-INF/cani.txt is 295.
	 */	
	@Test
	public void annotateTokensItalianNumberTokensTest() throws Exception {
		int n = 0;
		Iterator it = jCas.getAnnotationIndex(Token.type).iterator();
		while(it.hasNext()) {
			Token ob = (Token) it.next();
	         n += 1;
	      }		
		assertEquals(295, n); // 295, because I didn't exclude punctuations. Otherwise it would be 264.
	}
	
	/*
	 * Checks that the second token in the file META-INF/cani.txt is "donna" (converts it to lowercase).
	 */	
	@Test
	public void annotateTokensItalianSecondTokenTest() throws Exception {
		int n = 0;
		Iterator it = jCas.getAnnotationIndex(Token.type).iterator();
		while(it.hasNext()) {
			Token ob = (Token) it.next();
	         n += 1;
	         if(n == 2){
	        	 assertEquals("donna", ob.getCoveredText().toLowerCase());
	        	 break;
	         }
	      }		
		
	}
	
}
