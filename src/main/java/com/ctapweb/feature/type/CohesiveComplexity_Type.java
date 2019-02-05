
/* First created by JCasGen Tue Feb 05 10:21:08 CET 2019 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;

/** 
 * Updated by JCasGen Tue Feb 05 10:21:08 CET 2019
 * @generated */
public class CohesiveComplexity_Type extends ComplexityFeatureBase_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = CohesiveComplexity.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ctapweb.feature.type.CohesiveComplexity");



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public CohesiveComplexity_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

  }
}



    