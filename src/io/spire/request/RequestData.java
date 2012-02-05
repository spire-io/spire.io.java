/**
 * 
 */
package io.spire.request;

import io.spire.request.Request.RequestType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jorge
 *
 */
public class RequestData {
	
	public String url;
	public RequestType method;
	public Map<String, String> headers;
	public Map<String, Object> body;
	
	/**
	 * 
	 */
	public RequestData() {
		headers = new HashMap<String, String>();
		body = new HashMap<String, Object>();
	}

}
