/**
 * 
 */
package com.ctapweb.feature.util;

/**
 * Interface for look up table resources. Example look up table is a frequency lists.
 * @author xiaobin
 *
 */
public interface LookUpTableResource {
	
	public double lookup(String word);

}
