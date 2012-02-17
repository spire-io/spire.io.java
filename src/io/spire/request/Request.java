/**
 * 
 */
package io.spire.request;

import java.io.IOException;

/**
 * Spire HTTP Request Abstract class
 * Encapsulates any underlying HTTP client library used 
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public abstract class Request implements Requestable{

	/**
	 * 
	 * @since 1.0
	 * @author Jorge Gonzalez
	 *
	 */
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
	
	/**
	 * 
	 * @param data
	 */
	public Request(RequestData data) {
		this.setRequestData(data);
		this.prepareRequest(data);
	}
	
	/**
	 * 
	 * @param data
	 */
	protected abstract void prepareRequest(RequestData data);
	
	/**
	 * @return the requestData
	 */
	public RequestData getRequestData() {
		return requestData;
	}

	/**
	 * @param requestData the requestData to set
	 */
	public void setRequestData(RequestData requestData) {
		this.requestData = requestData;
	}

	/**
	 * 
	 */
	public abstract Response send() throws IOException;
}
