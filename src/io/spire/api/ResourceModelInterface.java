/**
 * 
 */
package io.spire.api;

/**
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public interface ResourceModelInterface {
	
	/**
	 * 
	 * @param propertyName
	 * @param type
	 * @return <T>T
	 */
	public <T>T getProperty(String propertyName, Class<T> type);
	
	/**
	 * 
	 * @param propertyName
	 * @param data
	 */
	public void setProperty(String propertyName, Object data);
}
