/**
 * 
 */
package com.ctapweb.feature.util;

import java.util.Set;

/**
 * Interface for look up list resources. Example look up list is a list of connectives and its variants.
 * @author zweiss
 *
 */
public interface LookUpListResource {
	
	public String[] lookup(String word);
	public Set<String> getKeys();

}
