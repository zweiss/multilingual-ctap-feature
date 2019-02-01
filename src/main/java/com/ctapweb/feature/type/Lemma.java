

/* First created by JCasGen Mon Jan 28 10:39:46 CET 2019 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** the lemma of a token
 * Updated by JCasGen Mon Jan 28 13:49:27 CET 2019
 * XML source: /Users/zweiss/Documents/Forschung/Projekte/CTAP/git/ctap/ctap-feature/src/main/resources/descriptor/type_system/linguistic_type/MorphologicalTagType.xml
 * @generated */
public class Lemma extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Lemma.class);
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
  protected Lemma() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Lemma(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Lemma(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Lemma(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
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
  //* Feature: lemma

  /** getter for lemma - gets the lemma as a String
   * @generated
   * @return value of the feature 
   */
  public String getLemma() {
    if (Lemma_Type.featOkTst && ((Lemma_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "com.ctapweb.feature.type.Lemma");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Lemma_Type)jcasType).casFeatCode_lemma);}
    
  /** setter for lemma - sets the lemma as a String 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLemma(String v) {
    if (Lemma_Type.featOkTst && ((Lemma_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "com.ctapweb.feature.type.Lemma");
    jcasType.ll_cas.ll_setStringValue(addr, ((Lemma_Type)jcasType).casFeatCode_lemma, v);}    
  }

    