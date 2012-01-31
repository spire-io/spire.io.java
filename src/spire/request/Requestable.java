/**
 * 
 */
package spire.request;

import java.io.IOException;


/**
 * @author jorge
 *
 */
public interface Requestable {

	public Responsable send() throws IOException;
}
