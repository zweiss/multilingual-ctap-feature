package com.ctapweb.annotator.test;

import com.ctapweb.feature.test.util.DescriptorModifier;
import com.ctapweb.feature.type.Lemma;

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
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.XMLInputSource;
import org.apache.uima.util.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;


public class LemmaAnnotatorTest {
	
	JCas jCas;
	/*
	@Before
	public void setUp() throws Exception {
		XMLParser pars = UIMAFramework.getXMLParser();
		
		TypeSystemDescription tsd = TypeSystemDescriptionFactory.createTypeSystemDescription();
		
		ArrayList<String> locationsList = new ArrayList<String>();
		locationsList.add("src/main/resources/descriptor/type_system/linguistic_type/TokenType.xml");
		
		DescriptorModifier.readXMLTypeDescriptorModifyImports ("src/main/resources/descriptor/type_system/linguistic_type/LemmaType.xml", "./META-INF/org.apache.uima.fit/LemmaTypeForUIMAFitTest.xml", locationsList);
		String lemmaTypeDescr = new String(Files.readAllBytes(Paths.get("./META-INF/org.apache.uima.fit/LemmaTypeForUIMAFitTest.xml")));
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new ByteArrayInputStream(lemmaTypeDescr.getBytes("UTF-8")));
		
		tsd.buildFromXMLElement(doc.getDocumentElement(), pars);
	    jCas = CasCreationUtils.createCas(tsd, null, null).getJCas();
		
	    String contents = new String(Files.readAllBytes(Paths.get("./META-INF/cani.txt")));
		jCas.setDocumentText(contents);
		
		File fSent = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguage ("src/main/resources/descriptor/annotator/SentenceAnnotator.xml", "./META-INF/org.apache.uima.fit/SentenceAnnotatorForUIMAFitTest.xml", "IT");
		XMLInputSource xmlInputSourceSent = new XMLInputSource(fSent);
		AnalysisEngineDescription aedSent = pars.parseAnalysisEngineDescription(xmlInputSourceSent);
		
		File fToken = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguage ("src/main/resources/descriptor/annotator/TokenAnnotator.xml", "./META-INF/org.apache.uima.fit/TokenAnnotatorForUIMAFitTest.xml", "IT");
		XMLInputSource xmlInputSourceToken = new XMLInputSource(fToken);
		AnalysisEngineDescription aedToken = pars.parseAnalysisEngineDescription(xmlInputSourceToken);
		
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguage ("src/main/resources/descriptor/annotator/LemmaAnnotator.xml", "./META-INF/org.apache.uima.fit/LemmaAnnotatorForUIMAFitTest.xml", "IT");
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aed);
	}
	*/
	
	/*
	 * Checks that the number of lemmas in the file META-INF/cani.txt is 295.
	 */
	/*
	@Test
	public void annotateLemmasItalianNumberLemmasTest() throws Exception {		
		int n = 0;
		Iterator it = jCas.getAnnotationIndex(Lemma.type).iterator();
		while(it.hasNext()) {
			Lemma ob = (Lemma) it.next();
	         n += 1;
	      }		
		assertEquals(295, n); 
	}
	*/
	
	/*
	 * Checks that the fourth lemma in the file META-INF/cani.txt is "salvare".
	 */
	/*
	@Test
	public void annotateLemmasItalianFourthLemmaTest() throws Exception {		
		int n = 0;
		Iterator it = jCas.getAnnotationIndex(Lemma.type).iterator();
		while(it.hasNext()) {
			Lemma ob = (Lemma) it.next();
	         n += 1;
	         if(n == 4){
	        	 assertEquals("salvare", ob.getLemma().toLowerCase()); 
	         }
	      }

	}
	*/
	
	/*
	 * Checks that the 6th lemma in the file META-INF/cani.txt is "cane".
	 */
	/*
	@Test
	public void annotateLemmasItalianSixthLemmaTest() throws Exception {		
		int n = 0;
		Iterator it = jCas.getAnnotationIndex(Lemma.type).iterator();
		while(it.hasNext()) {
			Lemma ob = (Lemma) it.next();
	         n += 1;
	         if(n == 6){
	        	 assertEquals("cane", ob.getLemma().toLowerCase()); 
	         }
	      }

	}
	*/
}
