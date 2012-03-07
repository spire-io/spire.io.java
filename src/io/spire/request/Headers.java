/**
 * 
 */
package io.spire.request;

import java.util.HashMap;

/**
 * HTTP Headers
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public class Headers extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3145282783839561120L;

	/**
	 * Default constructor
	 */
	public Headers(){
		super();
	}
	
	/**
	 * Gets Authorization header 
	 * 
	 * @return {@link String}
	 */
	public String getAuthorization(){
		return (String)this.get("Authorization"); 
	}
	
	/**
	 * Sets the Authorization header
	 * @param authorization
	 */
	public void setAuthorization(String authorization){
		this.put("Authorization", authorization);
	}
	
	/**
	 * Returns true if the Authorization header has been set, false otherwise.
	 * 
	 * @return {@link Boolean}
	 */
	public boolean containsAuthorization(){
		return this.containsKey("Authorization");
	}
	
}
