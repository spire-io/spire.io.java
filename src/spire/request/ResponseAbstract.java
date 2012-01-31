/**
 * 
 */
package spire.request;

import java.io.IOException;

/**
 * @author jorge
 *
 */
public abstract class ResponseAbstract {

	/**
	 * 
	 */
	public ResponseAbstract() {
		// TODO Auto-generated constructor stub
	}

	public abstract <T> T parseAs(Class<T> dataClass) throws IOException;
}
