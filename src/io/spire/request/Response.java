package io.spire.request;

import java.io.IOException;


/**
 * HTTP response abstract class
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public abstract class Response implements Responder {

	public Response() {
	}
	
	public abstract int getStatusCode();
	
	public abstract boolean isSuccessStatusCode();
	
	public abstract <T> T parseAs(Class<T> dataClass) throws IOException;
	
	public abstract String parseAsString() throws IOException;

	public abstract void close() throws IOException;
	
	public abstract void ignore() throws IOException;
}