package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;


public class NLemma_Type extends ComplexityFeatureBase_Type {
	/** @generated */
	  @SuppressWarnings ("hiding")
	  public final static int typeIndexID = NLemma.typeIndexID;
	  /** @generated 
	     @modifiable */
	  @SuppressWarnings ("hiding")
	  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ctapweb.feature.type.NLemma");



	  /** initialize variables to correspond with Cas Type and Features
		 * @generated
		 * @param jcas JCas
		 * @param casType Type 
		 */
	  public NLemma_Type(JCas jcas, Type casType) {
	    super(jcas, casType);
	    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

	  }
}
