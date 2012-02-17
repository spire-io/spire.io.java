/**
 * 
 */
package io.spire.request;

import java.util.Map;

import io.spire.request.Request.RequestType;
import io.spire.request.RequestData;
import io.spire.request.GCRequest.CGRequestFactory;

/**
 * HTTP request factory
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public abstract class RequestFactory {
	
	/**
	 * 
	 */
	public static final RequestFactory REQUEST_FACTORY = new CGRequestFactory(); 
	
	/**
	 * 
	 */
	public RequestFactory(){
		
	}
	
	/**
	 * 
	 * @return {@link Request}
	 */
	public abstract Request createHTTPRequest();
	
	/**
	 * 
	 * @param data
	 * @return  {@link Request}
	 */
	public abstract Request createHTTPRequest(RequestData data);

	/**
	 * 
	 * @return  {@link RequestData}
	 */
	public static RequestData createRequestData(){
		return new RequestData();
	}
	
	/**
	 * 
	 * @param methodType
	 * @param url
	 * @param content
	 * @param headers
	 * @return  {@link RequestData}
	 */
	public static RequestData createRequestData(RequestType methodType, String url, Map<String, Object> content, Map<String, String> headers){
		RequestData data = new RequestData();
		data.method = methodType;
		data.url = url;
		data.body = content;
		data.headers = headers;
		return data;
	}
	
	/**
	 * 
	 * @param data
	 * @return  {@link Request}
	 */
	public static Request createRequest(RequestData data){
		return REQUEST_FACTORY.createHTTPRequest(data);
	}
}
