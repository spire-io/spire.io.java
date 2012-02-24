/**
 * 
 */
package io.spire.request;

import java.io.IOException;

/**
 * HTTP Request interface
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public interface Requester {

	/**
	 * Sets the timeout in milliseconds to establish a connection or 0 for an infinite timeout.
	 * By default it is 20000 (20 seconds)
	 * 
	 * @param timeout
	 */
	public void setConnectionTimeout(int timeout);
	
	/**
	 * Returns the timeout in milliseconds to establish a connection or 0 for an infinite timeout.
	 * 
	 * @return {@link Integer}
	 */
	public int getConnectionTimeout();
	
	/**
	 * Sets the timeout in milliseconds to read data from an established 
	 * connection or 0 for an infinite timeout.
	 * 
	 * By default it is 90000 (90 seconds).
	 * 
	 * @param timeout
	 */
	public void setReadTimeout(int timeout);
	
	/**
	 * Returns the timeout in milliseconds to read data from an 
	 * established connection or 0 for an infinite timeout.
	 * 
	 * 
	 * @return {@link Integer}
	 */
	public int getReadTimeout();
	
	/**
	 * Sets the HTTP request headers.
	 * 
	 * @param headers
	 */
	public void setHeaders(Headers headers);
	
	/**
	 * Returns the HTTP request headers.
	 * 
	 * @return {@link Headers}
	 */
	public Headers getHeaders();
	
	/**
	 * Executes an HTTP request
	 * 
	 * @return {@link Responder}
	 * @throws IOException
	 */
	public Responder send() throws IOException;
}
