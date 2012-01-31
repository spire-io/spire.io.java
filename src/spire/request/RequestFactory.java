/**
 * 
 */
package spire.request;

import spire.request.RequestData;

/**
 * @author jorge
 *
 */
public class RequestFactory {

	public static RequestData getRequestData(){
		return new RequestData();
	}
	
	public static Request getRequest(RequestData data){
		data.method = RequestType.HTTP_GET;
		return new Request(data);
	}
	
	public static Request createRequest(RequestData data){
		return new Request(data);
	}
}
