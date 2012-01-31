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

	public ResponseAbstract send() throws IOException;
}
