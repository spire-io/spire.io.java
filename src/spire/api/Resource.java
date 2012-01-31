/**
 * 
 */
package spire.api;

import java.util.HashMap;

import spire.request.RequestFactory;

/**
 * @author jorge
 *
 */
public class Resource {

	protected RequestFactory requestFactory;
	/**
	 * 
	 */
	public Resource() {
		requestFactory = new RequestFactory();
	}
	
	public static class ResourceModel extends HashMap<String, Object> {
	}

}
