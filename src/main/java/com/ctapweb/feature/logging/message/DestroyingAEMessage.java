package com.ctapweb.feature.logging.message;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;

import com.ctapweb.feature.logging.message.AEType;

/**
 * A message for logging AE destory event.
 * @author xiaobin
 *
 */
public class DestroyingAEMessage extends ParameterizedMessage {

	/**
	 * 
	 * @param aeType The type of the AE issuing the log message.
	 * @param aeName The name of the AE issuing the log message.
	 */
	public DestroyingAEMessage(AEType aeType, String aeName) {
		super("Destorying AE: {} <{}>...", aeType, aeName);
	}
}
