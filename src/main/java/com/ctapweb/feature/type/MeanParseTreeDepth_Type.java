
/* First created manually by copying a similar file on May 29 2019 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;

/** Mean parse tree depth.
 * 
 *  */
public class MeanParseTreeDepth_Type extends ComplexityFeatureBase_Type {
	/** @generated 
	   * @return the generator for this type
	   */
	  @Override
	  protected FSGenerator getFSGenerator() {return fsGenerator;}
	  /** @generated */
	  private final FSGenerator fsGenerator = 
	    new FSGenerator() {
	      public FeatureStructure createFS(int addr, CASImpl cas) {
	  			 if (MeanParseTreeDepth_Type.this.useExistingInstance) {
	  			   // Return eq fs instance if already created
	  		     FeatureStructure fs = MeanParseTreeDepth_Type.this.jcas.getJfsFromCaddr(addr);
	  		     if (null == fs) {
	  		       fs = new MeanParseTreeDepth(addr, MeanParseTreeDepth_Type.this);
	  		     MeanParseTreeDepth_Type.this.jcas.putJfsFromCaddr(addr, fs);
	  			   return fs;
	  		     }
	  		     return fs;
	        } else return new MeanParseTreeDepth(addr, MeanParseTreeDepth_Type.this);
	  	  }
	    };
	  /** @generated */
	  @SuppressWarnings ("hiding")
	  public final static int typeIndexID = MeanParseTreeDepth.typeIndexID;
	  /** @generated 
	     @modifiable */
	  @SuppressWarnings ("hiding")
	  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.xiaobin.ctap.type.MeanParseTreeDepth");



	  /** initialize variables to correspond with Cas Type and Features
		 * @generated
		 * @param jcas JCas
		 * @param casType Type 
		 */
	  public MeanParseTreeDepth_Type(JCas jcas, Type casType) {
	    super(jcas, casType);
	    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

	  }
}
