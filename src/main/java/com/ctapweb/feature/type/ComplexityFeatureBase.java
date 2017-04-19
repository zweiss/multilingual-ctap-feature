

/* First created by JCasGen Tue Aug 16 14:32:11 CEST 2016 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.AnnotationBase;


/** All complexity features extend this base type.
 * Updated by JCasGen Wed Apr 19 17:38:47 CEST 2017
 * XML source: /home/sgalasso/complexityCode/ctap/ctap-feature/src/main/resources/descriptor/featureAE/LexicalFrequencyProfile_Band1.xml
 * @generated */
public class ComplexityFeatureBase extends AnnotationBase {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(ComplexityFeatureBase.class);
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
  protected ComplexityFeatureBase() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public ComplexityFeatureBase(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public ComplexityFeatureBase(JCas jcas) {
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
  //* Feature: id

  /** getter for id - gets the ID of the current analysis engine
   * @generated
   * @return value of the feature 
   */
  public int getId() {
    if (ComplexityFeatureBase_Type.featOkTst && ((ComplexityFeatureBase_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "com.ctapweb.feature.type.ComplexityFeatureBase");
    return jcasType.ll_cas.ll_getIntValue(addr, ((ComplexityFeatureBase_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets the ID of the current analysis engine 
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(int v) {
    if (ComplexityFeatureBase_Type.featOkTst && ((ComplexityFeatureBase_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "com.ctapweb.feature.type.ComplexityFeatureBase");
    jcasType.ll_cas.ll_setIntValue(addr, ((ComplexityFeatureBase_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: value

  /** getter for value - gets The value of the feature.
   * @generated
   * @return value of the feature 
   */
  public double getValue() {
    if (ComplexityFeatureBase_Type.featOkTst && ((ComplexityFeatureBase_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "com.ctapweb.feature.type.ComplexityFeatureBase");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((ComplexityFeatureBase_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets The value of the feature. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setValue(double v) {
    if (ComplexityFeatureBase_Type.featOkTst && ((ComplexityFeatureBase_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "com.ctapweb.feature.type.ComplexityFeatureBase");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((ComplexityFeatureBase_Type)jcasType).casFeatCode_value, v);}    
  }

    