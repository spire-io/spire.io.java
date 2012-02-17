/**
 * 
 */
package io.spire.api;

import io.spire.api.Resource.ResourceModel;

/**
 * Operations to be supported by {@link ResourceModel}
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public interface ResourceModelInterface {
	
	/**
	 * Gets a member and cast it to the corresponding Class type
	 * 
	 * @param propertyName
	 * @param type
	 * @return <T>T
	 */
	public <T>T getProperty(String propertyName, Class<T> type);
	
	/**
	 * Sets a member to the corresponding {@link ResourceModel}
	 * 
	 * @param propertyName
	 * @param data
	 */
	public void setProperty(String propertyName, Object data);
}
