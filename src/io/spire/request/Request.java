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
public abstract class Request implements Requester{

	/**
	 * Abstract HTTP Request class that describes the basics of 
	 * an HTTP Request Method GET/POST/PUT/DELETE
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
	
	protected int connectionTimeout;
	protected int readTimeout;
	protected RequestData requestData;
	
	/**
	 * Default constructor
	 */
	public Request() {
		this.connectionTimeout = 20 * 1000;	// 20 seconds
		this.readTimeout = 90 * 1000;		// 90 seconds
	}
	
	/**
	 * Construct a HTTP Request object and initialize it base on the information
	 * describe by {@link RequestData} data
	 * 
	 * @param data
	 */
	public Request(RequestData data) {
		this();
		this.prepareRequest(data);
	}
	
	/**
	 * Initialize underlying HTTP Request client
	 * 
	 * @param data
	 */
	public abstract void prepareRequest(RequestData data);
	
	/**
	 * Gets {@link RequestData} object
	 * 
	 * @return the requestData
	 */
	public RequestData getRequestData() {
		return requestData;
	}

	/**
	 * Sets {@link RequestData} object
	 * 
	 * @param requestData the requestData to set
	 */
	public void setRequestData(RequestData requestData) {
		this.requestData = requestData;
	}
	
	public abstract void setConnectionTimeout(int timeout);
	
	public abstract int getConnectionTimeout();
	
	public abstract void setReadTimeout(int timeout);
	
	public abstract int getReadTimeout();
	
	public abstract void setHeaders(Headers headers);
	
	public abstract Headers getHeaders();
	
	public abstract Response send() throws IOException;
}
