/**
 * 
 */
package io.spire.api;

/**
 * @author Jorge Gonzalez
 *
 */
public interface ResourceModelInterface {
	
	public <T>T getProperty(String propertyName, Class<T> type);
	
	public void setProperty(String propertyName, Object data);
}
