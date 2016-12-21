
/* First created by JCasGen Tue Dec 06 13:51:53 CET 2016 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;

import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** 
 * Updated by JCasGen Wed Dec 21 15:51:41 CET 2016
 * @generated */
public class NSyntacticConstituent_Type extends ComplexityFeatureBase_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = NSyntacticConstituent.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ctapweb.feature.type.NSyntacticConstituent");



  /** @generated */
  final Feature casFeat_contituentType;
  /** @generated */
  final int     casFeatCode_contituentType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getContituentType(int addr) {
        if (featOkTst && casFeat_contituentType == null)
      jcas.throwFeatMissing("contituentType", "com.ctapweb.feature.type.NSyntacticConstituent");
    return ll_cas.ll_getStringValue(addr, casFeatCode_contituentType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setContituentType(int addr, String v) {
        if (featOkTst && casFeat_contituentType == null)
      jcas.throwFeatMissing("contituentType", "com.ctapweb.feature.type.NSyntacticConstituent");
    ll_cas.ll_setStringValue(addr, casFeatCode_contituentType, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public NSyntacticConstituent_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_contituentType = jcas.getRequiredFeatureDE(casType, "contituentType", "uima.cas.String", featOkTst);
    casFeatCode_contituentType  = (null == casFeat_contituentType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_contituentType).getCode();

  }
}



    