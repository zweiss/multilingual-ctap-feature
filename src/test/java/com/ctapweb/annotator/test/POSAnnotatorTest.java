package com.ctapweb.annotator.test;

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
import com.ctapweb.feature.test.util.DescriptorModifier;
import com.ctapweb.feature.type.Letter;
import com.ctapweb.feature.type.POS;


/**
 * Tests the POSAnnotator.
 * @author Nadezda Okinina
 */
public class POSAnnotatorTest {
	JCas jCas;

	/*
	@Before
	public void setUp() throws Exception {
		XMLParser pars = UIMAFramework.getXMLParser();
		
		TypeSystemDescription tsd = TypeSystemDescriptionFactory.createTypeSystemDescription();
		
		ArrayList<String> locationsList = new ArrayList<String>();
		locationsList.add("src/main/resources/descriptor/type_system/linguistic_type/TokenType.xml");
		
		DescriptorModifier.readXMLTypeDescriptorModifyImports("src/main/resources/descriptor/type_system/linguistic_type/POSType.xml", "./META-INF/org.apache.uima.fit/POSTypeForUIMAFitTest.xml", locationsList);
		String posTypeDescr = new String(Files.readAllBytes(Paths.get("./META-INF/org.apache.uima.fit/POSTypeForUIMAFitTest.xml")));
		
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new ByteArrayInputStream(posTypeDescr.getBytes("UTF-8")));
		
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
		
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguage ("src/main/resources/descriptor/annotator/POSAnnotator.xml", "./META-INF/org.apache.uima.fit/POSAnnotatorForUIMAFitTest.xml", "IT");
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then POSAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aed);
	}
	*/
	
	/*
	 * Tests that the number of POS tags in the text cani.txt is equal to 295
	 */
	/*
	@Test
	public void annotatePOSNumberItalianTest() throws Exception {		
		int n = 0;
		Iterator it = jCas.getAnnotationIndex(POS.type).iterator();		
		while(it.hasNext()) {
			POS ob = (POS)it.next();
	         n += 1;
	      }

		assertEquals(295, n); 
	}
	*/
	
	/*
	 * Tests that the first word of the text cani.txt "La" is tagged as an article ("RD")
	 */
	/*
	@Test
	public void annotatePOSItalianFirstWordTest() throws Exception {	
		int n = 0;
		Iterator it = jCas.getAnnotationIndex(POS.type).iterator();		
		while(it.hasNext()) {
			POS ob = (POS) it.next();
	         n += 1;
	         assertEquals('r', ob.getTag().toLowerCase().charAt(0));
	         break;
	      }

	}
	*/
	
	/*
	 * Tests that the word "hanno" of the text cani.txt  is tagged as a verb
	 */
	/*
	@Test
	public void annotatePOSItalian287WordTest() throws Exception {	
		int n = 0;
		char firstLetterOfPOSTag = 'v';
		Iterator it = jCas.getAnnotationIndex(POS.type).iterator();		
		while(it.hasNext()) {
			POS ob = (POS) it.next();
	         n += 1;
	         //if (n == 287){
	        if (ob.getCoveredText().equals("hanno")){
	        	 assertEquals(ob.getTag().toLowerCase().charAt(0), firstLetterOfPOSTag);
	        	 break;
	         }
	      }
	}
	*/
	
	/*
	 * Tests the number of articles in the text cani.txt
	 */
	/*
	@Test
	public void annotatePOSItalianNumberArticlesTest() throws Exception {	
		int n = 0;
		Iterator it = jCas.getAnnotationIndex(POS.type).iterator();
		char firstLetterOfPOSTag = 'r';
		while(it.hasNext()) {
			POS ob = (POS) it.next();
			if (ob.getTag().toLowerCase().charAt(0)  == firstLetterOfPOSTag){
				n += 1;
			}
	         
	      }
		assertEquals(19, n);
	}
	*/
	
	/*
	 * Tests the number of adverbs in the text cani.txt
	 */
	/*
	@Test
	public void annotatePOSItalianNumberAdverbsTest() throws Exception {	
		int n = 0;
		Iterator it = jCas.getAnnotationIndex(POS.type).iterator();	
		char firstLetterOfPOSTag = 'b';
		while(it.hasNext()) {
			POS ob = (POS) it.next();
			if (ob.getTag().toLowerCase().charAt(0)  == firstLetterOfPOSTag){
				n += 1;
			}
	         
	      }
		assertEquals(16, n);
	}
	*/
	
	/*
	 * Tests the number of singular nouns in the text cani.txt
	 */
	/*
	@Test
	public void annotatePOSItalianNumberNounsTest() throws Exception {	
		int n = 0;
		Iterator it = jCas.getAnnotationIndex(POS.type).iterator();
		char firstLetterOfPOSTag = 's';
		while(it.hasNext()) {
			POS ob = (POS) it.next();
			if (ob.getTag().toLowerCase().charAt(0)  == firstLetterOfPOSTag){
				n += 1;
			}
	         
	      }
		assertEquals(66, n);
	}
	*/
		
	/*
	 * Tests the number of verbs in the text cani.txt
	 */
	/*
	@Test
	public void annotatePOSItalianNumberVerbsTest() throws Exception {	
		int n = 0;
		Iterator it = jCas.getAnnotationIndex(POS.type).iterator();
		char firstLetterOfPOSTag = 'v';
		while(it.hasNext()) {
			POS ob = (POS) it.next();
			if (ob.getTag().toLowerCase().charAt(0)  == firstLetterOfPOSTag){
				n += 1;
			}
	         
	      }
		assertEquals(62, n);
	}
	*/
	
	/*
	 * Tests the number of pronouns in the text cani.txt
	 */
	/*
	@Test
	public void annotatePOSItalianNumberPronounsTest() throws Exception {	
		int n = 0;
		int i = 0;
		Iterator it = jCas.getAnnotationIndex(POS.type).iterator();
		char firstLetterOfPOSTag = 'p';
		while(it.hasNext()) {
			POS ob = (POS) it.next();
			i += 1;
			if (ob.getTag().toLowerCase().charAt(0)  == firstLetterOfPOSTag){
				n += 1;
			}
	         
	      }
		assertEquals(22, n);
	}
	*/
}
