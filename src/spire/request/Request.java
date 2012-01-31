/**
 * 
 */
package spire.request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import spire.request.RequestData;
import spire.request.RequestType;
import spire.request.ResponseException.ResponseExceptionFactory;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

/**
 * @author jorge
 *
 */
public class Request extends RequestAbstract {

	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private HttpRequestFactory requestFactory;
	private HttpRequest client;
	
	/**
	 * 
	 */
	public Request() {
		
	}
	
	public Request(RequestData data) {
		super(data);
	}
	
	protected HttpHeaders getHTTPHeaders(RequestData data){
		HttpHeaders headers = new HttpHeaders();
		for (Map.Entry<String, String> header : data.headers.entrySet()) {
			headers.set(header.getKey(), header.getValue());
		}
		return headers;
	}
	
	@Override
	protected void prepareRequest(RequestData data)
	{
		super.prepareRequest(data);
		try{
			client = getHTTPClient(data);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void setRequestFactory(){
		if(requestFactory == null){
			requestFactory =
			        HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			          @Override
			          public void initialize(HttpRequest request) {
			            request.addParser(new JsonHttpParser(JSON_FACTORY));
			          }
			        });
		}
	}
	
	protected HttpRequest getHTTPClient(RequestData data) throws IOException{
		setRequestFactory();
		GenericUrl url = new GenericUrl(data.url);
		HttpContent content = null;
		HttpHeaders headers = getHTTPHeaders(data);
		HttpRequest request = null;
		
		switch (data.method) {
		case HTTP_GET:
			request = requestFactory.buildGetRequest(url);
			break;
		case HTTP_POST:
			request = requestFactory.buildPostRequest(url, content);
			break;
		case HTTP_PUT:
			request = requestFactory.buildPutRequest(url, content);
			break;
		case HTTP_DELETE:
			request = requestFactory.buildDeleteRequest(url);
			break;
		default:
			return request;
		}
		
		request.setHeaders(headers);
		return request;
	}

	public Response send() throws ResponseException, IOException {
		HttpResponse httpResponse = null;
		try{
			httpResponse = client.execute();
		}catch(HttpResponseException e){
			throw ResponseExceptionFactory.createResponseException(e);
		}
		
		Response response = new Response(httpResponse);
		return response;
	}
}
