
/* First created by JCasGen Mon Nov 28 11:41:21 CET 2016 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;

/** POS density of the document
 * Updated by JCasGen Tue Dec 06 13:50:01 CET 2016
 * @generated */
public class POSDensity_Type extends ComplexityFeatureBase_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = POSDensity.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ctapweb.feature.type.POSDensity");



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public POSDensity_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

  }
}



    