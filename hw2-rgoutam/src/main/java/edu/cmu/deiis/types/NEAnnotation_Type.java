
/* First created by JCasGen Tue Oct 07 22:09:11 EDT 2014 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** 
 * Updated by JCasGen Tue Oct 07 22:09:11 EDT 2014
 * @generated */
public class NEAnnotation_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (NEAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = NEAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new NEAnnotation(addr, NEAnnotation_Type.this);
  			   NEAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new NEAnnotation(addr, NEAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = NEAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.deiis.types.NEAnnotation");
 
  /** @generated */
  final Feature casFeat_NamedEntity;
  /** @generated */
  final int     casFeatCode_NamedEntity;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getNamedEntity(int addr) {
        if (featOkTst && casFeat_NamedEntity == null)
      jcas.throwFeatMissing("NamedEntity", "edu.cmu.deiis.types.NEAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_NamedEntity);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setNamedEntity(int addr, String v) {
        if (featOkTst && casFeat_NamedEntity == null)
      jcas.throwFeatMissing("NamedEntity", "edu.cmu.deiis.types.NEAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_NamedEntity, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public NEAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_NamedEntity = jcas.getRequiredFeatureDE(casType, "NamedEntity", "uima.cas.String", featOkTst);
    casFeatCode_NamedEntity  = (null == casFeat_NamedEntity) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_NamedEntity).getCode();

  }
}



    