package com.ctapweb.feature.featureAE;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.uima.UimaContext;
import com.ctapweb.feature.logging.LogMarker;
import com.ctapweb.feature.logging.message.AEType;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import com.ctapweb.feature.logging.message.DestroyAECompleteMessage;
import com.ctapweb.feature.logging.message.DestroyingAEMessage;
import com.ctapweb.feature.logging.message.InitializeAECompleteMessage;
import com.ctapweb.feature.logging.message.InitializingAEMessage;
import com.ctapweb.feature.logging.message.PopulatedFeatureValueMessage;
import com.ctapweb.feature.logging.message.ProcessingDocumentMessage;
import com.ctapweb.feature.type.ParseTree;

import edu.stanford.nlp.trees.PennTreeReader;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeReader;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;

import com.ctapweb.feature.type.MeanParseTreeDepth;
import com.ctapweb.feature.type.NSyntacticConstituent;

public class MeanParseTreeDepthAE extends JCasAnnotator_ImplBase {

	//the analysis engine's id from the database
	//this value needs to be set when initiating the analysis engine
	public static final String PARAM_AEID = "aeID";
	private int aeID;
	private String unit = null;

	public static String lCode;

	private static final Logger logger = LogManager.getLogger();

	private static final String PARAM_LANGUAGE_CODE = "LanguageCode";

	private static final AEType aeType = AEType.FEATURE_EXTRACTOR;
	private static final String aeName = "MeanParseTreeDepth Feature Extractor";


	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new InitializingAEMessage(aeType, aeName));

		super.initialize(aContext);

		//get the parameter value of analysis id
		if(aContext.getConfigParameterValue(PARAM_AEID) == null) {
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_AEID});
			logger.throwing(e);
			throw e;
		} else {
			aeID = (Integer) aContext.getConfigParameterValue(PARAM_AEID);
		}

		if(aContext.getConfigParameterValue(PARAM_LANGUAGE_CODE) == null) { 
			ResourceInitializationException e = new ResourceInitializationException("mandatory_value_missing", 
					new Object[] {PARAM_LANGUAGE_CODE});
			logger.throwing(e);
			throw e;
		} else {
			lCode = ((String) aContext.getConfigParameterValue(PARAM_LANGUAGE_CODE)).toUpperCase();
		}

		logger.trace(LogMarker.UIMA_MARKER, new InitializeAECompleteMessage(aeType, aeName));
	}

	/* (non-Javadoc)
	 * @see org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
	 */
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		logger.trace(LogMarker.UIMA_MARKER, 
				new ProcessingDocumentMessage(aeType, aeName, aJCas.getDocumentText()));
		
		// get annotation indexes and iterator
		//iterate through all parse trees (sentences).
		Iterator it = aJCas.getAnnotationIndex(ParseTree.type).iterator();

		int numberOfTrees = 0;
		int sumOfDepths = 0;
		while(it.hasNext()) {

			ParseTree parseTree = (ParseTree) it.next();
			
			//the matcher requires a tree object, so create a tree object from the 
			//parse tree string
			TreeReader treeReader =  new PennTreeReader(new StringReader(parseTree.getParseTree()));
			Tree tree;
			try {
				tree = treeReader.readTree();
				if (tree == null) {
					logger.warn(LogMarker.UIMA_MARKER, "ParseTree could not be converted to Tree and is skipped line 114: "+parseTree.getParseTree().toString());

				} else {
					numberOfTrees += 1;
					if (lCode.equals("IT")){
						sumOfDepths += tree.depth()-1;
					}else{
						sumOfDepths += tree.depth();
					}

				}
				treeReader.close();
			} catch (IOException e) {
				logger.throwing(e);
			}
		}
		
		double meanDepth = 0 ;		
		if (numberOfTrees > 0){
			meanDepth = (double) sumOfDepths / numberOfTrees ;
		}
		
		MeanParseTreeDepth annotation = new MeanParseTreeDepth(aJCas);

		//set the feature ID and feature value
		annotation.setId(aeID);
		annotation.setValue(meanDepth);
		annotation.addToIndexes();

		logger.info(new PopulatedFeatureValueMessage(aeID, meanDepth));
	}


	@Override
	public void destroy() {
		logger.trace(LogMarker.UIMA_MARKER, new DestroyingAEMessage(aeType, aeName));

		super.destroy();

		logger.trace(LogMarker.UIMA_MARKER, new DestroyAECompleteMessage(aeType, aeName));
	}

}
