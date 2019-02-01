
/* First created by JCasGen Mon Jan 28 13:23:07 CET 2019 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** the morphological tag of a token
 * Updated by JCasGen Mon Jan 28 13:49:27 CET 2019
 * @generated */
public class MorphologicalTag_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = MorphologicalTag.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ctapweb.feature.type.MorphologicalTag");
 
  /** @generated */
  final Feature casFeat_morphologicalTag;
  /** @generated */
  final int     casFeatCode_morphologicalTag;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMorphologicalTag(int addr) {
        if (featOkTst && casFeat_morphologicalTag == null)
      jcas.throwFeatMissing("morphologicalTag", "com.ctapweb.feature.type.MorphologicalTag");
    return ll_cas.ll_getStringValue(addr, casFeatCode_morphologicalTag);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMorphologicalTag(int addr, String v) {
        if (featOkTst && casFeat_morphologicalTag == null)
      jcas.throwFeatMissing("morphologicalTag", "com.ctapweb.feature.type.MorphologicalTag");
    ll_cas.ll_setStringValue(addr, casFeatCode_morphologicalTag, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public MorphologicalTag_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_morphologicalTag = jcas.getRequiredFeatureDE(casType, "morphologicalTag", "uima.cas.String", featOkTst);
    casFeatCode_morphologicalTag  = (null == casFeat_morphologicalTag) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_morphologicalTag).getCode();

  }
}



    