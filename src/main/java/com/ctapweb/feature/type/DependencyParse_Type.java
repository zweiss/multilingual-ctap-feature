
/* First created by JCasGen Tue Jan 29 11:05:21 CET 2019 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** the dependency parse
 of a sentence
 * Updated by JCasGen Tue Jan 29 16:16:36 CET 2019
 * @generated */
public class DependencyParse_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DependencyParse.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ctapweb.feature.type.DependencyParse");
 
  /** @generated */
  final Feature casFeat_dependencyParse;
  /** @generated */
  final int     casFeatCode_dependencyParse;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getDependencyParse(int addr) {
        if (featOkTst && casFeat_dependencyParse == null)
      jcas.throwFeatMissing("dependencyParse", "com.ctapweb.feature.type.DependencyParse");
    return ll_cas.ll_getStringValue(addr, casFeatCode_dependencyParse);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDependencyParse(int addr, String v) {
        if (featOkTst && casFeat_dependencyParse == null)
      jcas.throwFeatMissing("dependencyParse", "com.ctapweb.feature.type.DependencyParse");
    ll_cas.ll_setStringValue(addr, casFeatCode_dependencyParse, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public DependencyParse_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_dependencyParse = jcas.getRequiredFeatureDE(casType, "dependencyParse", "uima.cas.String", featOkTst);
    casFeatCode_dependencyParse  = (null == casFeat_dependencyParse) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_dependencyParse).getCode();

  }
}



    