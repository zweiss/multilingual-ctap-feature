/*package com.ctapweb.feature.test;
//package com.ctapweb.feature.annotator;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createPrimitiveDescription;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.component.initialize.ConfigurationParameterInitializer;
import org.apache.uima.fit.component.initialize.ExternalResourceInitializer;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.factory.UimaContextFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ExternalResourceDependency;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.ResourceManager;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.XMLInputSource;
import org.apache.uima.util.XMLParser;
import org.apache.xerces.parsers.DOMParser;
import org.apache.uima.UIMAFramework;
import org.apache.uima.UimaContextAdmin;
import org.apache.uima.UimaContextHolder;
import org.junit.Test;

import com.ctapweb.feature.annotator.SentenceAnnotator;
import com.ctapweb.feature.type.Sentence;
import com.ctapweb.feature.type.Token;
//import com.ctapweb.feature.annotator.SyllableAnnotator;
//import com.ctapweb.feature.logging.message.AEType;
import com.google.common.collect.ImmutableList;


import static org.apache.uima.fit.factory.JCasFactory.createJCas;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import org.apache.uima.fit.factory.JCasFactory;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.metadata.TypeSystemDescription;

import org.w3c.dom.*;
import javax.xml.parsers.*;

//import com.ctapweb.feature.type.Sentence;

public class SentenceAnnotatorTest {
	@ConfigurationParameter
	String PARAM_LANGUAGE_CODE = "IT";

	@ExternalResource
	String SentenceSegmenterModelIT = "file:model/it-sent.bin";

	@Test
	public void annotateSentencesTest() throws Exception {
		System.out.println(UimaContextHolder.getContext());
		JCas jCas = JCasFactory.createJCas();
		jCas.setDocumentText("Ecco una prima frase. E questa seconda frase segue. Scriviamo ancora un po'...");
		
	AnalysisEngine analysisEngine = AnalysisEngineFactory.createEngine(
			SentenceAnnotator.class,
	  SentenceAnnotator.PARAM_LANGUAGE_CODE, "IT");
		 	

		
	AnalysisEngineDescription aed = AnalysisEngineFactory.createEngineDescription(SentenceAnnotator.class,
			SentenceAnnotator.PARAM_LANGUAGE_CODE,"IT");
		 

		//System.setProperty("org.apache.uima.fit.type.import_pattern","classpath*:../../src/main/resources/descriptor/type_system/linguistic_type/*");

		XMLParser pars = UIMAFramework.getXMLParser();
		File f = new File("./META-INF/org.apache.uima.fit/config.xml");
		XMLInputSource xmlInputSource = new XMLInputSource(f);
		AnalysisEngineDescription aed = pars.parseAnalysisEngineDescription(xmlInputSource);
		ResourceManager rm = UIMAFramework.newDefaultResourceManager();
		//String dPath = rm.getDataPath();
		//System.out.println("dPath: "+dPath);
		aed.resolveImports(rm);

		//aed.setAttributeValue(SentenceAnnotator.PARAM_LANGUAGE_CODE,"IT");
		//aed.setAttributeValue("SentenceSegmenterModelIT","file:model/it-sent.bin");

		//ExternalResourceDependency[] extResDeps = {"SentenceSegmenterModelIT","file:model/it-sent.bin"};
		//aed.setExternalResourceDependencies(extResDeps);
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("/home/nadiushka/ctap/recent-ctap-docker/file.txt");
			aed.toXML(fileWriter);
		} catch (Exception e) {
			e.printStackTrace();
		}

		SimplePipeline.runPipeline(jCas, aed);;

		//analysisEngine.process(jCas);

		Iterator it = jCas.getAnnotationIndex(Sentence.type).iterator();
		for(Annotation annot : JCasUtil.select(jCas, Annotation.class)){
			System.out.println("Found annotation: " + annot.getCoveredText());
		}
		int n = 0;
		while(it.hasNext()) {
			System.out.println("sentence");
			n += 1;
		}
		assertEquals(3, n);
	}
}
*/