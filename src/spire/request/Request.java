/**
 * 
 */
package spire.request;

import java.io.IOException;

/**
 * @author jorge
 *
 */
public abstract class Request implements Requestable{

	private RequestData requestData;
	
	/**
	 * 
	 */
	public Request() {
	}
	
	public Request(RequestData data) {
		prepareRequest(data);
	}
	
	protected void prepareRequest(RequestData data)
	{
		this.requestData = data;
	}
	
	public abstract Response send() throws IOException;
}
