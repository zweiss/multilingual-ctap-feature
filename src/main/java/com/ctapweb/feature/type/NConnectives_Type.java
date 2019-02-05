
/* First created by JCasGen Fri Feb 01 16:11:03 CET 2019 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;

import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** 
 * Updated by JCasGen Tue Feb 05 10:21:08 CET 2019
 * @generated */
public class NConnectives_Type extends ComplexityFeatureBase_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = NConnectives.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ctapweb.feature.type.NConnectives");



  /** @generated */
  final Feature casFeat_connectiveType;
  /** @generated */
  final int     casFeatCode_connectiveType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getConnectiveType(int addr) {
        if (featOkTst && casFeat_connectiveType == null)
      jcas.throwFeatMissing("connectiveType", "com.ctapweb.feature.type.NConnectives");
    return ll_cas.ll_getStringValue(addr, casFeatCode_connectiveType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setConnectiveType(int addr, String v) {
        if (featOkTst && casFeat_connectiveType == null)
      jcas.throwFeatMissing("connectiveType", "com.ctapweb.feature.type.NConnectives");
    ll_cas.ll_setStringValue(addr, casFeatCode_connectiveType, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Scope;
  /** @generated */
  final int     casFeatCode_Scope;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getScope(int addr) {
        if (featOkTst && casFeat_Scope == null)
      jcas.throwFeatMissing("Scope", "com.ctapweb.feature.type.NConnectives");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Scope);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setScope(int addr, String v) {
        if (featOkTst && casFeat_Scope == null)
      jcas.throwFeatMissing("Scope", "com.ctapweb.feature.type.NConnectives");
    ll_cas.ll_setStringValue(addr, casFeatCode_Scope, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public NConnectives_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_connectiveType = jcas.getRequiredFeatureDE(casType, "connectiveType", "uima.cas.String", featOkTst);
    casFeatCode_connectiveType  = (null == casFeat_connectiveType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_connectiveType).getCode();

 
    casFeat_Scope = jcas.getRequiredFeatureDE(casType, "Scope", "uima.cas.String", featOkTst);
    casFeatCode_Scope  = (null == casFeat_Scope) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Scope).getCode();

  }
}



    