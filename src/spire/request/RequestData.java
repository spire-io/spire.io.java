/**
 * 
 */
package spire.request;

import java.util.HashMap;

/**
 * @author jorge
 *
 */
public class RequestData {

	public String url;
	public RequestType method;
	public HashMap<String, String> headers;
	public HashMap<String, String> body;
	
	/**
	 * 
	 */
	public RequestData() {
		headers = new HashMap<String, String>();
		body = new HashMap<String, String>();
	}

}
