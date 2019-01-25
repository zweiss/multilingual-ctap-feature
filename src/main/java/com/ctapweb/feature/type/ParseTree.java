

/* First created by JCasGen Tue Dec 06 13:17:37 CET 2016 */
package com.ctapweb.feature.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** the parse tree of a sentence
 * Updated by JCasGen Wed Dec 21 15:51:41 CET 2016
 * XML source: /home/xiaobin/work/project/CTAP/ctap-feature/src/main/resources/descriptor/TAE/SyntacticComplexity_VBperT_TAE.xml
 * @generated */
public class ParseTree extends Annotation {
	/** @generated
	 * @ordered 
	 */
	@SuppressWarnings ("hiding")
	public final static int typeIndexID = JCasRegistry.register(ParseTree.class);
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
	protected ParseTree() {/* intentionally empty block */}

	/** Internal - constructor used by generator 
	 * @generated
	 * @param addr low level Feature Structure reference
	 * @param type the type of this Feature Structure 
	 */
	public ParseTree(int addr, TOP_Type type) {
		super(addr, type);
		readObject();
	}

	/** @generated
	 * @param jcas JCas to which this Feature Structure belongs 
	 */
	public ParseTree(JCas jcas) {
		super(jcas);
		readObject();   
	} 

	/** @generated
	 * @param jcas JCas to which this Feature Structure belongs
	 * @param begin offset to the begin spot in the SofA
	 * @param end offset to the end spot in the SofA 
	 */  
	public ParseTree(JCas jcas, int begin, int end) {
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
	//* Feature: parseTree

	/** getter for parseTree - gets the parse tree as a String
	 * @generated
	 * @return value of the feature 
	 */
	public String getParseTree() {
		if (ParseTree_Type.featOkTst && ((ParseTree_Type)jcasType).casFeat_parseTree == null)
			jcasType.jcas.throwFeatMissing("parseTree", "com.ctapweb.feature.type.ParseTree");
		return jcasType.ll_cas.ll_getStringValue(addr, ((ParseTree_Type)jcasType).casFeatCode_parseTree);}

	/** setter for parseTree - sets the parse tree as a String 
	 * @generated
	 * @param v value to set into the feature 
	 */
	public void setParseTree(String v) {
		if (ParseTree_Type.featOkTst && ((ParseTree_Type)jcasType).casFeat_parseTree == null)
			jcasType.jcas.throwFeatMissing("parseTree", "com.ctapweb.feature.type.ParseTree");
		jcasType.ll_cas.ll_setStringValue(addr, ((ParseTree_Type)jcasType).casFeatCode_parseTree, v);}    
}

