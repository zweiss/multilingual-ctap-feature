package com.ctapweb.feature.logging.message;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;

/**
 * A message for logging that a feature value has been obtained and populated into the CAS.
 * @author xiaobin
 *
 */
public class PopulatedFeatureValueMessage extends ParameterizedMessage{
	
	/**
	 * 
	 * @param aeID The id of the AE in the database, which is set automatically when CTAP constructs the CPE.
	 * @param value The calculated value of this feature.
	 */
	public PopulatedFeatureValueMessage(int aeID, double value) {
		super("Feature value {} calculated from AE {} populated into CAS.", new Object[] {value, aeID});
	}
}
