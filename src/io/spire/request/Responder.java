/**
 * 
 */
package io.spire.request;

import java.io.IOException;

/**
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public interface Responder {
	
	public abstract int getStatusCode();
	
	public abstract boolean isSuccessStatusCode();

	public abstract <T> T parseAs(Class<T> dataClass) throws IOException;
	
	public String parseAsString() throws IOException;
	
	public abstract void close() throws IOException;
	
	public abstract void ignore() throws IOException;
}
