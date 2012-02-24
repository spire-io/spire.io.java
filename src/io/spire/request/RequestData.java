/**
 * 
 */
package io.spire.request;

import io.spire.request.Request.RequestType;

import java.util.HashMap;
import java.util.Map;

/**
 * Describes the all HTTP request info 
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public class RequestData {
	
	public String url;
	public Map<String, Object> queryParams;
	public RequestType method;
	public Headers headers;
	public Object body;
	
	/**
	 * 
	 */
	public RequestData() {
		headers = new Headers();
		queryParams = new HashMap<String, Object>();
	}
}
