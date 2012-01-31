/**
 * 
 */
package spire.request;

import java.io.IOException;

/**
 * @author jorge
 *
 */
public abstract class RequestAbstract implements Requestable{

	private RequestData requestData;
	
	/**
	 * 
	 */
	public RequestAbstract() {
		this.requestData = null;
	}
	
	public RequestAbstract(RequestData data) {
		prepareRequest(data);
	}
	
	protected void prepareRequest(RequestData data)
	{
		this.requestData = data;
	}
	
	public abstract ResponseAbstract send() throws IOException;
}
