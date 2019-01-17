package com.ctapweb.feature.logging.message;

import org.apache.logging.log4j.message.ParameterizedMessage;

/**
 * A message for logging selection of language specific resource in annotators
 * @author zweiss
 *
 */
public class SelectingLanguageSpecificResource extends ParameterizedMessage {

	/**
	 * 
	 * @param aeName The name of the AE issuing the log message.
	 * @oaram resourceLanguage The abbreviation oft he resource language chosen.
	 */
	public SelectingLanguageSpecificResource(String aeName, String resourceLanguage) {
		super("Selecting annotation resource for {} for language: {}...", new Object[] {aeName, resourceLanguage});
	}
}
