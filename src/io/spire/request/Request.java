/**
 * 
 */
package io.spire.request;

import java.io.IOException;

/**
 * @author Jorge Gonzalez
 *
 */
public abstract class Request implements Requestable{

	public enum RequestType {
		HTTP_GET,
		HTTP_POST,
		HTTP_PUT,
		HTTP_DELETE
	}
	
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
