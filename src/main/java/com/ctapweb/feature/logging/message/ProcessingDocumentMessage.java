package com.ctapweb.feature.logging.message;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;

import com.ctapweb.feature.logging.message.AEType;

/**
 * A message for logging document processing event. 
 * @author xiaobin
 *
 */
public class ProcessingDocumentMessage extends ParameterizedMessage {
	
	/**
	 * 
	 * @param aeType The type of the AE.
	 * @param aeName The AE's name.
	 * @param docText The text being analyzed.
	 */
	public ProcessingDocumentMessage(AEType aeType, String aeName, String docText) {
		super("Processing document with {} <{}>: {}...", 
				new Object[] { aeType, aeName, docText.substring(0, 50)});
	}

}
