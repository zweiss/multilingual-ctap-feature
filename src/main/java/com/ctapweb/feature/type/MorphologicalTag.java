

/* First created by JCasGen Mon Jan 28 13:23:07 CET 2019 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;


/** the morphological tag of a token
 * Updated by JCasGen Mon Jan 28 13:49:27 CET 2019
 * XML source: /Users/zweiss/Documents/Forschung/Projekte/CTAP/git/ctap/ctap-feature/src/main/resources/descriptor/type_system/linguistic_type/MorphologicalTagType.xml
 * @generated */
public class MorphologicalTag extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MorphologicalTag.class);
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
  protected MorphologicalTag() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public MorphologicalTag(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public MorphologicalTag(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public MorphologicalTag(JCas jcas, int begin, int end) {
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
  //* Feature: morphologicalTag

  /** getter for morphologicalTag - gets the morphological tag as a String
   * @generated
   * @return value of the feature 
   */
  public String getMorphologicalTag() {
    if (MorphologicalTag_Type.featOkTst && ((MorphologicalTag_Type)jcasType).casFeat_morphologicalTag == null)
      jcasType.jcas.throwFeatMissing("morphologicalTag", "com.ctapweb.feature.type.MorphologicalTag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MorphologicalTag_Type)jcasType).casFeatCode_morphologicalTag);}
    
  /** setter for morphologicalTag - sets the morphological tag as a String 
   * @generated
   * @param v value to set into the feature 
   */
  public void setMorphologicalTag(String v) {
    if (MorphologicalTag_Type.featOkTst && ((MorphologicalTag_Type)jcasType).casFeat_morphologicalTag == null)
      jcasType.jcas.throwFeatMissing("morphologicalTag", "com.ctapweb.feature.type.MorphologicalTag");
    jcasType.ll_cas.ll_setStringValue(addr, ((MorphologicalTag_Type)jcasType).casFeatCode_morphologicalTag, v);}    
  }

    