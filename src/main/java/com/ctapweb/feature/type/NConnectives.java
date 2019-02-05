

/* First created by JCasGen Fri Feb 01 16:11:03 CET 2019 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Tue Feb 05 10:21:08 CET 2019
 * XML source: /Users/zweiss/Documents/Forschung/Projekte/CTAP/git/ctap/ctap-feature/src/main/resources/descriptor/type_system/feature_type/CohesiveComplexityType.xml
 * @generated */
public class NConnectives extends ComplexityFeatureBase {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(NConnectives.class);
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
  protected NConnectives() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public NConnectives(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public NConnectives(JCas jcas) {
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
  //* Feature: connectiveType

  /** getter for connectiveType - gets Combination of semantic and origin, e.g., Breindl_Temp
   * @generated
   * @return value of the feature 
   */
  public String getConnectiveType() {
    if (NConnectives_Type.featOkTst && ((NConnectives_Type)jcasType).casFeat_connectiveType == null)
      jcasType.jcas.throwFeatMissing("connectiveType", "com.ctapweb.feature.type.NConnectives");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NConnectives_Type)jcasType).casFeatCode_connectiveType);}
    
  /** setter for connectiveType - sets Combination of semantic and origin, e.g., Breindl_Temp 
   * @generated
   * @param v value to set into the feature 
   */
  public void setConnectiveType(String v) {
    if (NConnectives_Type.featOkTst && ((NConnectives_Type)jcasType).casFeat_connectiveType == null)
      jcasType.jcas.throwFeatMissing("connectiveType", "com.ctapweb.feature.type.NConnectives");
    jcasType.ll_cas.ll_setStringValue(addr, ((NConnectives_Type)jcasType).casFeatCode_connectiveType, v);}    
   
    
  //*--------------*
  //* Feature: Scope

  /** getter for Scope - gets Can be one of the following:
                    ALL count single and multi word connectives
                    SINGLE count only single word connectives
                    MULTI count only multi word connectives
   * @generated
   * @return value of the feature 
   */
  public String getScope() {
    if (NConnectives_Type.featOkTst && ((NConnectives_Type)jcasType).casFeat_Scope == null)
      jcasType.jcas.throwFeatMissing("Scope", "com.ctapweb.feature.type.NConnectives");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NConnectives_Type)jcasType).casFeatCode_Scope);}
    
  /** setter for Scope - sets Can be one of the following:
                    ALL count single and multi word connectives
                    SINGLE count only single word connectives
                    MULTI count only multi word connectives 
   * @generated
   * @param v value to set into the feature 
   */
  public void setScope(String v) {
    if (NConnectives_Type.featOkTst && ((NConnectives_Type)jcasType).casFeat_Scope == null)
      jcasType.jcas.throwFeatMissing("Scope", "com.ctapweb.feature.type.NConnectives");
    jcasType.ll_cas.ll_setStringValue(addr, ((NConnectives_Type)jcasType).casFeatCode_Scope, v);}    
  }

    