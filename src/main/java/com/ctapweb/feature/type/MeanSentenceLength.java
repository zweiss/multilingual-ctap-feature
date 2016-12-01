

/* First created by JCasGen Tue Aug 16 14:46:06 CEST 2016 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Mean sentence length in tokens, syllables, or characters.
 * Updated by JCasGen Tue Aug 16 14:46:06 CEST 2016
 * XML source: /home/xiaobin/sync/projects/eclipse/FeatureUIMA/src/main/descriptor/type_system/feature_type/MeanSentenceLength.xml
 * @generated */
public class MeanSentenceLength extends ComplexityFeatureBase {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MeanSentenceLength.class);
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
  protected MeanSentenceLength() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public MeanSentenceLength(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public MeanSentenceLength(JCas jcas) {
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
     
}

    