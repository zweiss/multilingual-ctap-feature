package com.ctapweb.feature.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

import com.ctapweb.feature.logging.LogMarker;
import com.ctapweb.feature.logging.message.LoadResourceMessage;

public class LookUpTableResource_Impl implements LookUpTableResource, SharedResourceObject {

	private static final Logger logger = LogManager.getLogger();
	public static String RESOURCE_KEY = "LookUpTable";
	private Map<String, Double> lookUpTable = new HashMap<>();

	@Override
	public void load(DataResource aData) throws ResourceInitializationException {

		logger.trace(LogMarker.UIMA_MARKER, 
				new LoadResourceMessage(RESOURCE_KEY, aData.getUrl().toString()));

		InputStream inStr = null;
		try {
			// open input stream to data
			inStr = aData.getInputStream();
			// read each line
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStr));
			String line;
			while ((line = reader.readLine()) != null) {
				// the first tab on each line separates key from value.
				// Keys cannot contain whitespace.
				line = line.trim();
				if(line.length()==0) {
					continue;
				}
				int tabPos = line.indexOf('\t');
				String key = line.substring(0, tabPos).toLowerCase();
				Double val = Double.parseDouble(line.substring(tabPos + 1));
				lookUpTable.put(key, val);
			}
		} catch (IOException e) {
			throw new ResourceInitializationException(e);
		} finally {
			if (inStr != null) {
				try {
					inStr.close();
				} catch (IOException e) {
				}
			}
		}

	}

	@Override
	public double lookup(String word) {
		double val = 0;
		if(lookUpTable.get(word) != null) {
			val = lookUpTable.get(word);
		}

		return val;
	}
}
