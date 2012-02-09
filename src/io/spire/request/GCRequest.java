/**
 * 
 */
package io.spire.request;

import io.spire.request.RequestData;
import io.spire.request.ResponseException.ResponseExceptionFactory;

import java.io.IOException;
import java.util.Map;


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
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

/**
 * @author jorge
 *
 */
public class GCRequest extends Request {

	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private HttpRequestFactory requestFactory;
	private HttpRequest client;
	
	/**
	 * 
	 */
	public GCRequest() {
		super();
	}
	
	public GCRequest(RequestData data) {
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
	
	protected void setRequestFactory(final String contentType){
		if(requestFactory == null){
			requestFactory =
			        HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			          @Override
			          public void initialize(HttpRequest request) {
			        	  JsonHttpParser.Builder builder = JsonHttpParser.builder(JSON_FACTORY);
			        	  if(contentType != null)
			        		  builder = builder.setContentType(contentType);
			        	  
			        	  request.addParser(builder.build());
			          }
			        });
		}
	}
	
	protected GenericUrl createCGUrl(String url, Map<String, Object> queryParams){
		GenericUrl gurl = new GenericUrl(url);
		if(queryParams != null){
			gurl.putAll(queryParams);
//			for (Map.Entry<String, Object> param : queryParams.entrySet()) {
//				
//			}
		}
		return gurl;
	}
	
	protected HttpRequest getHTTPClient(RequestData data) throws IOException{
//		GenericUrl url = new GenericUrl(data.url);
		GenericUrl url = createCGUrl(data.url, data.queryParams);
		HttpContent content = null;
		HttpHeaders headers = getHTTPHeaders(data);
		HttpRequest request = null;
		setRequestFactory(headers.getAccept());
		
		switch (data.method) {
		case HTTP_GET:
			request = requestFactory.buildGetRequest(url);
			break;
		case HTTP_POST:
			content = new JsonHttpContent(JSON_FACTORY, data.body);
			request = requestFactory.buildPostRequest(url, content);
			break;
		case HTTP_PUT:
			content = new JsonHttpContent(JSON_FACTORY, data.body);
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

	@Override
	public GCResponse send() throws ResponseException, IOException {
		HttpResponse httpResponse = null;
		try{
			httpResponse = client.execute();
		}catch(HttpResponseException e){
			throw ResponseExceptionFactory.createResponseException(e);
		}
		
		GCResponse response = new GCResponse(httpResponse);
		return response;
	}
	
	public static class CGRequestFactory extends RequestFactory{

		public CGRequestFactory(){
			super();
		}
		
		@Override
		public Request createHTTPRequest() {
			return new GCRequest();
		}
		
		@Override
		public Request createHTTPRequest(RequestData data) {
			return new GCRequest(data);
		}
		
	}
}
