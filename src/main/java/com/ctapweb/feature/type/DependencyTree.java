

/* First created by JCasGen Fri Jan 25 11:00:43 CET 2019 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;


/** the dependency tree of a sentence
 * Updated by JCasGen Fri Jan 25 11:30:53 CET 2019
 * XML source: /Users/zweiss/Documents/Forschung/Projekte/CTAP/git/ctap/ctap-feature/src/main/resources/descriptor/annotator/DependencyTreeAnnotator.xml
 * @generated */
public class DependencyTree extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DependencyTree.class);
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
  protected DependencyTree() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DependencyTree(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DependencyTree(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DependencyTree(JCas jcas, int begin, int end) {
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
  //* Feature: dependencyTree

  /** getter for dependencyTree - gets the dependency tree as a String array
   * @generated
   * @return value of the feature 
   */
  public StringArray getDependencyTree() {
    if (DependencyTree_Type.featOkTst && ((DependencyTree_Type)jcasType).casFeat_dependencyTree == null)
      jcasType.jcas.throwFeatMissing("dependencyTree", "com.ctapweb.feature.type.DependencyTree");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((DependencyTree_Type)jcasType).casFeatCode_dependencyTree)));}
    
  /** setter for dependencyTree - sets the dependency tree as a String array 
   * @generated
   * @param v value to set into the feature 
   */
  public void setDependencyTree(StringArray v) {
    if (DependencyTree_Type.featOkTst && ((DependencyTree_Type)jcasType).casFeat_dependencyTree == null)
      jcasType.jcas.throwFeatMissing("dependencyTree", "com.ctapweb.feature.type.DependencyTree");
    jcasType.ll_cas.ll_setRefValue(addr, ((DependencyTree_Type)jcasType).casFeatCode_dependencyTree, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for dependencyTree - gets an indexed value - the dependency tree as a String array
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getDependencyTree(int i) {
    if (DependencyTree_Type.featOkTst && ((DependencyTree_Type)jcasType).casFeat_dependencyTree == null)
      jcasType.jcas.throwFeatMissing("dependencyTree", "com.ctapweb.feature.type.DependencyTree");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DependencyTree_Type)jcasType).casFeatCode_dependencyTree), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DependencyTree_Type)jcasType).casFeatCode_dependencyTree), i);}

  /** indexed setter for dependencyTree - sets an indexed value - the dependency tree as a String array
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setDependencyTree(int i, String v) { 
    if (DependencyTree_Type.featOkTst && ((DependencyTree_Type)jcasType).casFeat_dependencyTree == null)
      jcasType.jcas.throwFeatMissing("dependencyTree", "com.ctapweb.feature.type.DependencyTree");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DependencyTree_Type)jcasType).casFeatCode_dependencyTree), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DependencyTree_Type)jcasType).casFeatCode_dependencyTree), i, v);}
  }

    