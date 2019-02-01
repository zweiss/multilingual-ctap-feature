

/* First created by JCasGen Tue Jan 29 11:05:21 CET 2019 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** the dependency parse
 of a sentence
 * Updated by JCasGen Tue Jan 29 16:16:36 CET 2019
 * XML source: /Users/zweiss/Documents/Forschung/Projekte/CTAP/git/ctap/ctap-feature/src/main/resources/descriptor/type_system/feature_type/DLTIntegrationCostType.xml
 * @generated */
public class DependencyParse extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DependencyParse.class);
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
  protected DependencyParse() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DependencyParse(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DependencyParse(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DependencyParse(JCas jcas, int begin, int end) {
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
  //* Feature: dependencyParse

  /** getter for dependencyParse - gets 
   * @generated
   * @return value of the feature 
   */
  public String getDependencyParse() {
    if (DependencyParse_Type.featOkTst && ((DependencyParse_Type)jcasType).casFeat_dependencyParse == null)
      jcasType.jcas.throwFeatMissing("dependencyParse", "com.ctapweb.feature.type.DependencyParse");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DependencyParse_Type)jcasType).casFeatCode_dependencyParse);}
    
  /** setter for dependencyParse - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDependencyParse(String v) {
    if (DependencyParse_Type.featOkTst && ((DependencyParse_Type)jcasType).casFeat_dependencyParse == null)
      jcasType.jcas.throwFeatMissing("dependencyParse", "com.ctapweb.feature.type.DependencyParse");
    jcasType.ll_cas.ll_setStringValue(addr, ((DependencyParse_Type)jcasType).casFeatCode_dependencyParse, v);}    
  }

    