
/* First created by JCasGen Tue Jan 29 16:16:36 CET 2019 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** 
 * Updated by JCasGen Tue Jan 29 16:16:36 CET 2019
 * @generated */
public class DLTIntegrationCost_Type extends ComplexityFeatureBase_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DLTIntegrationCost.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ctapweb.feature.type.DLTIntegrationCost");
 
  /** @generated */
  final Feature casFeat_costCalculationConfiguration;
  /** @generated */
  final int     casFeatCode_costCalculationConfiguration;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCostCalculationConfiguration(int addr) {
        if (featOkTst && casFeat_costCalculationConfiguration == null)
      jcas.throwFeatMissing("costCalculationConfiguration", "com.ctapweb.feature.type.DLTIntegrationCost");
    return ll_cas.ll_getStringValue(addr, casFeatCode_costCalculationConfiguration);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCostCalculationConfiguration(int addr, String v) {
        if (featOkTst && casFeat_costCalculationConfiguration == null)
      jcas.throwFeatMissing("costCalculationConfiguration", "com.ctapweb.feature.type.DLTIntegrationCost");
    ll_cas.ll_setStringValue(addr, casFeatCode_costCalculationConfiguration, v);}
    
  
 
  /** @generated */
  final Feature casFeat_integrationCostType;
  /** @generated */
  final int     casFeatCode_integrationCostType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getIntegrationCostType(int addr) {
        if (featOkTst && casFeat_integrationCostType == null)
      jcas.throwFeatMissing("integrationCostType", "com.ctapweb.feature.type.DLTIntegrationCost");
    return ll_cas.ll_getStringValue(addr, casFeatCode_integrationCostType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setIntegrationCostType(int addr, String v) {
        if (featOkTst && casFeat_integrationCostType == null)
      jcas.throwFeatMissing("integrationCostType", "com.ctapweb.feature.type.DLTIntegrationCost");
    ll_cas.ll_setStringValue(addr, casFeatCode_integrationCostType, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public DLTIntegrationCost_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_costCalculationConfiguration = jcas.getRequiredFeatureDE(casType, "costCalculationConfiguration", "uima.cas.String", featOkTst);
    casFeatCode_costCalculationConfiguration  = (null == casFeat_costCalculationConfiguration) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_costCalculationConfiguration).getCode();

 
    casFeat_integrationCostType = jcas.getRequiredFeatureDE(casType, "integrationCostType", "uima.cas.String", featOkTst);
    casFeatCode_integrationCostType  = (null == casFeat_integrationCostType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_integrationCostType).getCode();

  }
}



    