
/* First created by JCasGen Tue Aug 16 14:46:06 CEST 2016 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;

/** Mean sentence length in tokens, syllables, or characters.
 * Updated by JCasGen Tue Aug 16 14:46:06 CEST 2016
 * @generated */
public class MeanSentenceLength_Type extends ComplexityFeatureBase_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (MeanSentenceLength_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = MeanSentenceLength_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new MeanSentenceLength(addr, MeanSentenceLength_Type.this);
  			   MeanSentenceLength_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new MeanSentenceLength(addr, MeanSentenceLength_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = MeanSentenceLength.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.xiaobin.ctap.type.MeanSentenceLength");



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public MeanSentenceLength_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

  }
}



    