package com.ctapweb.feature.logging;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

/**
 * This class provides the markers used in the program logs to identify the types of logs.
 * Markers can be used for filtering log types.
 * 
 * @author xiaobin
 *
 */
public class LogMarker {
	public static final Marker UIMA_MARKER = MarkerManager.getMarker("UIMA");
	public static final Marker OPENNLP_MARKER = MarkerManager.getMarker("OPENNLP");
}
