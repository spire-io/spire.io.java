/**
 * 
 */
package io.spire.request;

import java.io.IOException;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;

/**
 * @author Jorge Gonzalez
 *
 */
public class ResponseException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1315031124790186892L;
	private Response response;
	private String message;
	
	public ResponseException(Response response) {
		this.response = response;
	}
	
	public ResponseException(Response response, String message) {
		this.response = response;
		this.message = message;
	}

	public Response getResponse(){
		return response;
	}
	
	public String getMessage(){
		return message;
	}
	
	public static final class ResponseExceptionFactory{
		public static ResponseException createResponseException(HttpResponseException exception){
			HttpResponse httpResponse = exception.getResponse();
			GCResponse response = new GCResponse(httpResponse);
			return new ResponseException(response, HttpResponseException.computeMessage(httpResponse));
		}
	}

}
