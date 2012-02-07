/**
 * 
 */
package io.spire.request;

import java.util.Map;

import io.spire.request.Request.RequestType;
import io.spire.request.RequestData;

/**
 * @author jorge
 *
 */
public class RequestFactory {

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
	
	public static Request createGETRequest(RequestData data){
		data.method = RequestType.HTTP_GET;
		return new GCRequest(data);
	}
	
	public static Request createRequest(RequestData data){
		return new GCRequest(data);
	}
}
