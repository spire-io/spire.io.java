/**
 * 
 */
package io.spire.request;

import io.spire.request.Request.RequestType;
import io.spire.request.RequestData;

/**
 * @author jorge
 *
 */
public class RequestFactory {

	public static RequestData getRequestData(){
		return new RequestData();
	}
	
	public static Request createGETRequest(RequestData data){
		data.method = RequestType.HTTP_GET;
		return new GCRequest(data);
	}
	
	public static Request createRequest(RequestData data){
		return new GCRequest(data);
	}
}
