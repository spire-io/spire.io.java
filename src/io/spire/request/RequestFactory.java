/**
 * 
 */
package io.spire.request;

import java.util.Map;

import io.spire.request.Request.RequestType;
import io.spire.request.RequestData;
import io.spire.request.GCRequest.CGRequestFactory;

/**
 * @author jorge
 *
 */
public abstract class RequestFactory {
	
	public static final RequestFactory REQUEST_FACTORY = new CGRequestFactory(); 
	
	public RequestFactory(){
		
	}
	
	public abstract Request createHTTPRequest();
	
	public abstract Request createHTTPRequest(RequestData data);

	public static RequestData createRequestData(){
		return new RequestData();
	}
	
	public static RequestData createRequestData(RequestType methodType, String url, Map<String, Object> content, Map<String, String> headers){
		RequestData data = new RequestData();
		data.method = methodType;
		data.url = url;
		data.body = content;
		data.headers = headers;
		return data;
	}
	
	public static Request createRequest(RequestData data){
		return REQUEST_FACTORY.createHTTPRequest(data);
	}
}
