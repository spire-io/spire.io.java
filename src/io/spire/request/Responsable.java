/**
 * 
 */
package io.spire.request;

import java.io.IOException;

/**
 * @author jorge
 *
 */
public interface Responsable {
	
	public abstract int getStatusCode();
	
	public abstract boolean isSuccessStatusCode();

	public abstract <T> T parseAs(Class<T> dataClass) throws IOException;
	
	public String parseAsString() throws IOException;
}
