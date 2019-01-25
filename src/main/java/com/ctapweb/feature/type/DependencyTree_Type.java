
/* First created by JCasGen Fri Jan 25 11:00:43 CET 2019 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** the dependency tree of a sentence
 * Updated by JCasGen Fri Jan 25 11:30:53 CET 2019
 * @generated */
public class DependencyTree_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DependencyTree.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ctapweb.feature.type.DependencyTree");
 
  /** @generated */
  final Feature casFeat_dependencyTree;
  /** @generated */
  final int     casFeatCode_dependencyTree;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getDependencyTree(int addr) {
        if (featOkTst && casFeat_dependencyTree == null)
      jcas.throwFeatMissing("dependencyTree", "com.ctapweb.feature.type.DependencyTree");
    return ll_cas.ll_getRefValue(addr, casFeatCode_dependencyTree);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDependencyTree(int addr, int v) {
        if (featOkTst && casFeat_dependencyTree == null)
      jcas.throwFeatMissing("dependencyTree", "com.ctapweb.feature.type.DependencyTree");
    ll_cas.ll_setRefValue(addr, casFeatCode_dependencyTree, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public String getDependencyTree(int addr, int i) {
        if (featOkTst && casFeat_dependencyTree == null)
      jcas.throwFeatMissing("dependencyTree", "com.ctapweb.feature.type.DependencyTree");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependencyTree), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_dependencyTree), i);
  return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependencyTree), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setDependencyTree(int addr, int i, String v) {
        if (featOkTst && casFeat_dependencyTree == null)
      jcas.throwFeatMissing("dependencyTree", "com.ctapweb.feature.type.DependencyTree");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependencyTree), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_dependencyTree), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependencyTree), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public DependencyTree_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_dependencyTree = jcas.getRequiredFeatureDE(casType, "dependencyTree", "uima.cas.StringArray", featOkTst);
    casFeatCode_dependencyTree  = (null == casFeat_dependencyTree) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_dependencyTree).getCode();

  }
}



    