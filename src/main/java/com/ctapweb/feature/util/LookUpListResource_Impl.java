package com.ctapweb.feature.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

import com.ctapweb.feature.logging.LogMarker;
import com.ctapweb.feature.logging.message.LoadResourceMessage;

/**
 * Looks up lists of words
 * @author zweiss
 *
 */
public class LookUpListResource_Impl implements LookUpListResource, SharedResourceObject {

	private static final Logger logger = LogManager.getLogger();
	public static String RESOURCE_KEY = "LookUpListTable";
	private Map<String, String[]> lookUpListTable = new HashMap<>();

	@Override
	public void load(DataResource aData) throws ResourceInitializationException {
		logger.trace(LogMarker.UIMA_MARKER, new LoadResourceMessage(RESOURCE_KEY, aData.getUrl().toString()));

		InputStream inStr = null;
		try {
			// open input stream to data
			inStr = aData.getInputStream();
			// read each line
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStr));
			String line;
			String[] tmpSplit;
			while ((line = reader.readLine()) != null) {
				tmpSplit = line.split(":");
				String key = tmpSplit[0].trim().toLowerCase();
				String[] val;
				if (tmpSplit.length>1) {
					val = tmpSplit[1].split(",");
				} else {
					val = new String[0];
				}
				lookUpListTable.put(key, val);
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
	public String[] lookup(String word) {
		if (!lookUpListTable.containsKey(word)) {
			return new String[0];
		}
		return lookUpListTable.get(word);
	}

	@Override
	public Set<String> getKeys() {
		return lookUpListTable.keySet();
	}
}
