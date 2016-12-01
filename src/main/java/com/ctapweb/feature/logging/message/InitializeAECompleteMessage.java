package com.ctapweb.feature.logging.message;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;

/**
 * A message for logging AE initialization complete event.
 * @author xiaobin
 *
 */
public class InitializeAECompleteMessage extends ParameterizedMessage {
	/**
	 * 
	 * @param aeType The type of the AE issuing the log message.
	 * @param aeName The name of the AE issuing the log message.
	 */
	public InitializeAECompleteMessage(AEType aeType, String aeName) {
		super("Initialization completed {} <{}>.", new Object[] {aeType, aeName});
	}
}
