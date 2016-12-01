
/* First created by JCasGen Tue Aug 16 14:30:54 CEST 2016 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.cas.AnnotationBase_Type;

/** The token type.
 * Updated by JCasGen Tue Aug 16 14:47:24 CEST 2016
 * @generated */
public class TokenType_Type extends AnnotationBase_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (TokenType_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = TokenType_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new TokenType(addr, TokenType_Type.this);
  			   TokenType_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new TokenType(addr, TokenType_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = TokenType.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.xiaobin.ctap.type.TokenType");
 
  /** @generated */
  final Feature casFeat_wordString;
  /** @generated */
  final int     casFeatCode_wordString;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getWordString(int addr) {
        if (featOkTst && casFeat_wordString == null)
      jcas.throwFeatMissing("wordString", "ch.xiaobin.ctap.type.TokenType");
    return ll_cas.ll_getStringValue(addr, casFeatCode_wordString);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setWordString(int addr, String v) {
        if (featOkTst && casFeat_wordString == null)
      jcas.throwFeatMissing("wordString", "ch.xiaobin.ctap.type.TokenType");
    ll_cas.ll_setStringValue(addr, casFeatCode_wordString, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public TokenType_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_wordString = jcas.getRequiredFeatureDE(casType, "wordString", "uima.cas.String", featOkTst);
    casFeatCode_wordString  = (null == casFeat_wordString) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_wordString).getCode();

  }
}



    