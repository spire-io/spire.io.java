/**
 * 
 */
package io.spire.request;

import java.io.IOException;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;

/**
 * HTTP Response exception wrapper
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public class ResponseException extends IOException {

	private static final long serialVersionUID = 1315031124790186892L;
	private Response response;
	private String message;
	
	/**
	 * 
	 * @param response
	 */
	public ResponseException(Response response) {
		this.response = response;
	}
	
	/**
	 * 
	 * @param response
	 * @param message
	 */
	public ResponseException(Response response, String message) {
		this.response = response;
		this.message = message;
	}

	/**
	 * 
	 * @return  {@link Response}
	 */
	public Response getResponse(){
		return response;
	}
	
	/**
	 * 
	 */
	public String getMessage(){
		return message;
	}
	
	/**
	 * Response factory
	 * 
	 * @since 1.0
	 * @author Jorge Gonzalez
	 *
	 */
	public static final class ResponseExceptionFactory{
		public static ResponseException createResponseException(HttpResponseException exception){
			HttpResponse httpResponse = exception.getResponse();
			GCResponse response = new GCResponse(httpResponse);
			return new ResponseException(response, HttpResponseException.computeMessage(httpResponse));
		}
	}

}
