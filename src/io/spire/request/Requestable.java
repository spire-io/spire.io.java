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
public interface Requestable {

	public Responsable send() throws IOException;
}
