

/* First created by JCasGen Tue Jan 29 16:16:36 CET 2019 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Tue Jan 29 16:16:36 CET 2019
 * XML source: /Users/zweiss/Documents/Forschung/Projekte/CTAP/git/ctap/ctap-feature/src/main/resources/descriptor/type_system/feature_type/DLTIntegrationCostType.xml
 * @generated */
public class DLTIntegrationCost extends ComplexityFeatureBase {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DLTIntegrationCost.class);
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
  protected DLTIntegrationCost() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DLTIntegrationCost(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DLTIntegrationCost(JCas jcas) {
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
  //* Feature: costCalculationConfiguration

  /** getter for costCalculationConfiguration - gets the constituent type, should be one of the following: 
o		original DLT IC cost calculation
v		additional verb weight
m		ignore modifier weights
c		reduced coordination weights
cv		reduce coordination weight, add verb weight
cm		reduce coordination weight, ignore modifier weight
mv		ignore modifier weight, add verb weight
cmv		reduce coordination weight, ignore modifier weight, add verb weight
   * @generated
   * @return value of the feature 
   */
  public String getCostCalculationConfiguration() {
    if (DLTIntegrationCost_Type.featOkTst && ((DLTIntegrationCost_Type)jcasType).casFeat_costCalculationConfiguration == null)
      jcasType.jcas.throwFeatMissing("costCalculationConfiguration", "com.ctapweb.feature.type.DLTIntegrationCost");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DLTIntegrationCost_Type)jcasType).casFeatCode_costCalculationConfiguration);}
    
  /** setter for costCalculationConfiguration - sets the constituent type, should be one of the following: 
o		original DLT IC cost calculation
v		additional verb weight
m		ignore modifier weights
c		reduced coordination weights
cv		reduce coordination weight, add verb weight
cm		reduce coordination weight, ignore modifier weight
mv		ignore modifier weight, add verb weight
cmv		reduce coordination weight, ignore modifier weight, add verb weight 
   * @generated
   * @param v value to set into the feature 
   */
  public void setCostCalculationConfiguration(String v) {
    if (DLTIntegrationCost_Type.featOkTst && ((DLTIntegrationCost_Type)jcasType).casFeat_costCalculationConfiguration == null)
      jcasType.jcas.throwFeatMissing("costCalculationConfiguration", "com.ctapweb.feature.type.DLTIntegrationCost");
    jcasType.ll_cas.ll_setStringValue(addr, ((DLTIntegrationCost_Type)jcasType).casFeatCode_costCalculationConfiguration, v);}    
   
    
  //*--------------*
  //* Feature: integrationCostType

  /** getter for integrationCostType - gets the constituent type, should be one of the following: 
totalIC		average total integration cost
maxIC		average maximal integration cost
highAdjacentIC		average high adjacent integration cost
   * @generated
   * @return value of the feature 
   */
  public String getIntegrationCostType() {
    if (DLTIntegrationCost_Type.featOkTst && ((DLTIntegrationCost_Type)jcasType).casFeat_integrationCostType == null)
      jcasType.jcas.throwFeatMissing("integrationCostType", "com.ctapweb.feature.type.DLTIntegrationCost");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DLTIntegrationCost_Type)jcasType).casFeatCode_integrationCostType);}
    
  /** setter for integrationCostType - sets the constituent type, should be one of the following: 
totalIC		average total integration cost
maxIC		average maximal integration cost
highAdjacentIC		average high adjacent integration cost 
   * @generated
   * @param v value to set into the feature 
   */
  public void setIntegrationCostType(String v) {
    if (DLTIntegrationCost_Type.featOkTst && ((DLTIntegrationCost_Type)jcasType).casFeat_integrationCostType == null)
      jcasType.jcas.throwFeatMissing("integrationCostType", "com.ctapweb.feature.type.DLTIntegrationCost");
    jcasType.ll_cas.ll_setStringValue(addr, ((DLTIntegrationCost_Type)jcasType).casFeatCode_integrationCostType, v);}    
  }

    