package com.ctapweb.annotator.test;

import com.ctapweb.feature.test.util.DescriptorModifier;
import com.ctapweb.feature.type.Syllable;
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



/**
 * Tests the SyllableAnnotator.
 * @author Nadezda Okinina
 */
public class SyllableAnnotatorTest {
	
	JCas jCas;

	/*
	@Before
	public void setUp() throws Exception {
		XMLParser pars = UIMAFramework.getXMLParser();
		
		TypeSystemDescription tsd = TypeSystemDescriptionFactory.createTypeSystemDescription();
		
		ArrayList<String> locationsList = new ArrayList<String>();
		locationsList.add("src/main/resources/descriptor/type_system/linguistic_type/TokenType.xml");
		
		DescriptorModifier.readXMLTypeDescriptorModifyImports ("src/main/resources/descriptor/type_system/linguistic_type/SyllableType.xml", "./META-INF/org.apache.uima.fit/SyllableTypeForUIMAFitTest.xml", locationsList);
		String syllableTypeDescr = new String(Files.readAllBytes(Paths.get("./META-INF/org.apache.uima.fit/SyllableTypeForUIMAFitTest.xml")));
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new ByteArrayInputStream(syllableTypeDescr.getBytes("UTF-8")));
		
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
		
		File f = DescriptorModifier.readXMLAnnotatorDescriptorAddLanguage ("src/main/resources/descriptor/annotator/SyllableAnnotator.xml", "./META-INF/org.apache.uima.fit/SyllableAnnotatorForUIMAFitTest.xml", "IT");
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		
		//Run the analysis pipeline: SentenceAnnotator, then TokenAnnotator, then SyllableAnnotator
		SimplePipeline.runPipeline(jCas, aedSent, aedToken, aed);
	}
	*/
	
	/*
	 * Checks that the number of syllables in META-INF/cani.txt is 572.
	 */
	/*
	@Test
	public void annotateSyllablesItalianNumberSyllablesTest() throws Exception {
		int n = 0;
		Iterator it = jCas.getAnnotationIndex(Syllable.type).iterator();		
		while(it.hasNext()) {
			Object ob = it.next();
	         n += 1;
	      }
		
		assertEquals(572, n); 
	}
	*/
	
	/*
	 * Checks that the second syllable in META-INF/cani.txt is "don".
	 */	
	/*
	@Test
	public void annotateSyllablesItalianSecondSyllableTest() throws Exception {
		int n = 0;
		Iterator it = jCas.getAnnotationIndex(Syllable.type).iterator();		
		while(it.hasNext()) {
			Syllable ob = (Syllable) it.next();
			n += 1;
			if (n == 2){
				assertEquals("don", ob.getCoveredText().toLowerCase()); 
				break;
			}
	         
	      }
	}
	*/
}
