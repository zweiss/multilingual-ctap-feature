

/* First created by JCasGen Tue Aug 16 14:30:54 CEST 2016 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.AnnotationBase;


/** The token type.
 * Updated by JCasGen Tue Aug 16 14:47:24 CEST 2016
 * XML source: /home/xiaobin/sync/projects/eclipse/FeatureUIMA/src/main/descriptor/type_system/feature_type/NTokenType.xml
 * @generated */
public class TokenType extends AnnotationBase {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(TokenType.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected TokenType() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public TokenType(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public TokenType(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: wordString

  /** getter for wordString - gets The spelling of the word type.
   * @generated
   * @return value of the feature 
   */
  public String getWordString() {
    if (TokenType_Type.featOkTst && ((TokenType_Type)jcasType).casFeat_wordString == null)
      jcasType.jcas.throwFeatMissing("wordString", "ch.xiaobin.ctap.type.TokenType");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TokenType_Type)jcasType).casFeatCode_wordString);}
    
  /** setter for wordString - sets The spelling of the word type. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setWordString(String v) {
    if (TokenType_Type.featOkTst && ((TokenType_Type)jcasType).casFeat_wordString == null)
      jcasType.jcas.throwFeatMissing("wordString", "ch.xiaobin.ctap.type.TokenType");
    jcasType.ll_cas.ll_setStringValue(addr, ((TokenType_Type)jcasType).casFeatCode_wordString, v);}    
  }

    