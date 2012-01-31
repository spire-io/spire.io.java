/**
 * 
 */
package spire.request;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;

/**
 * @author jorge
 *
 */
public class ResponseException extends HttpResponseException {

	public ResponseException(HttpResponse response) {
		super(response);
	}

	

}
