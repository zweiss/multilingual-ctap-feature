
/* First created by JCasGen Tue Dec 06 13:17:37 CET 2016 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** the parse tree of a sentence
 * Updated by JCasGen Wed Dec 21 15:51:41 CET 2016
 * @generated */
public class ParseTree_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = ParseTree.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ctapweb.feature.type.ParseTree");



  /** @generated */
  final Feature casFeat_parseTree;
  /** @generated */
  final int     casFeatCode_parseTree;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getParseTree(int addr) {
        if (featOkTst && casFeat_parseTree == null)
      jcas.throwFeatMissing("parseTree", "com.ctapweb.feature.type.ParseTree");
    return ll_cas.ll_getStringValue(addr, casFeatCode_parseTree);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setParseTree(int addr, String v) {
        if (featOkTst && casFeat_parseTree == null)
      jcas.throwFeatMissing("parseTree", "com.ctapweb.feature.type.ParseTree");
    ll_cas.ll_setStringValue(addr, casFeatCode_parseTree, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public ParseTree_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_parseTree = jcas.getRequiredFeatureDE(casType, "parseTree", "uima.cas.String", featOkTst);
    casFeatCode_parseTree  = (null == casFeat_parseTree) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_parseTree).getCode();

  }
}



    