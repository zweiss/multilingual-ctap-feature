

/* First created by JCasGen Tue Dec 06 13:51:53 CET 2016 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Wed Dec 21 15:51:41 CET 2016
 * XML source: /home/xiaobin/work/project/CTAP/ctap-feature/src/main/resources/descriptor/TAE/SyntacticComplexity_VBperT_TAE.xml
 * @generated */
public class NSyntacticConstituent extends ComplexityFeatureBase {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(NSyntacticConstituent.class);
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
  protected NSyntacticConstituent() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public NSyntacticConstituent(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public NSyntacticConstituent(JCas jcas) {
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
  //* Feature: contituentType

  /** getter for contituentType - gets the constituent type, should be one of the following: 
VP		Verb Phrases
C		Clauses
T		T-units
DC		Dependent Clauses
CT		Complex T-unit
CP		Coordinate Phrases
CN		Complex nominal
FC		Fragment Clauses
FT		Frgment T-units
   * @generated
   * @return value of the feature 
   */
  public String getContituentType() {
    if (NSyntacticConstituent_Type.featOkTst && ((NSyntacticConstituent_Type)jcasType).casFeat_contituentType == null)
      jcasType.jcas.throwFeatMissing("contituentType", "com.ctapweb.feature.type.NSyntacticConstituent");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NSyntacticConstituent_Type)jcasType).casFeatCode_contituentType);}
    
  /** setter for contituentType - sets the constituent type, should be one of the following: 
VP		Verb Phrases
C		Clauses
T		T-units
DC		Dependent Clauses
CT		Complex T-unit
CP		Coordinate Phrases
CN		Complex nominal
FC		Fragment Clauses
FT		Frgment T-units 
   * @generated
   * @param v value to set into the feature 
   */
  public void setContituentType(String v) {
    if (NSyntacticConstituent_Type.featOkTst && ((NSyntacticConstituent_Type)jcasType).casFeat_contituentType == null)
      jcasType.jcas.throwFeatMissing("contituentType", "com.ctapweb.feature.type.NSyntacticConstituent");
    jcasType.ll_cas.ll_setStringValue(addr, ((NSyntacticConstituent_Type)jcasType).casFeatCode_contituentType, v);}    
  }

    