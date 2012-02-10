/**
 * 
 */
package io.spire.request;

import java.io.IOException;


/**
 * @author Jorge Gonzalez
 *
 */
public interface Requestable {

	public Responsable send() throws IOException;
}
