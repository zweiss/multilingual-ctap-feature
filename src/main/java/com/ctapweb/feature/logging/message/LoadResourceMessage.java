package com.ctapweb.feature.logging.message;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;

/**
 * A message for logging language model load event.
 * @author xiaobin
 *
 */
public class LoadResourceMessage extends ParameterizedMessage{
	
	/**
	 * 
	 * @param resourceKey The resourceKey of the model as set in the AE descriptor.
	 * @param resourcePath The actual resource path the program looks for the language model.
	 */
	public LoadResourceMessage(String resourceKey, String resourcePath) {
		super("Loading resource {} from path {}...", new Object[] {resourceKey, resourcePath});
	}
}
