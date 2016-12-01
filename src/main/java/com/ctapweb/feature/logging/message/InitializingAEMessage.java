package com.ctapweb.feature.logging.message;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;

import com.ctapweb.feature.logging.message.AEType;

/**
 * A message for logging AE initialization event.
 * @author xiaobin
 *
 */
public class InitializingAEMessage extends ParameterizedMessage {

	/**
	 * 
	 * @param aeType The type of the AE issuing the log message.
	 * @param aeName The name of the AE issuing the log message.
	 */
	public InitializingAEMessage(AEType aeType, String aeName) {
		super("Initializing {} <{}>...", new Object[] {aeType, aeName});
	}
	
	
}
