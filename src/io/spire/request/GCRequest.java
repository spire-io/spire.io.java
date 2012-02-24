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
 * Google HTTP Request wrapper
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public class GCRequest extends Request {

	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private HttpRequestFactory requestFactory;
	private HttpRequest client;
	
	/**
	 * 
	 */
	public GCRequest() {
		super();
	}
	
	/**
	 * 
	 * @param data
	 */
	public GCRequest(RequestData data) {
		super(data);
	}
	
	/**
	 * Get HTTP Headers from {@link RequestData} object
	 * 
	 * @param data
	 * @return {@link HttpHeaders}
	 */
	protected HttpHeaders getCGHTTPHeaders(Headers headers){
		HttpHeaders gcHeaders = new HttpHeaders();
		for (Map.Entry<String, Object> header : headers.entrySet()) {
			gcHeaders.set(header.getKey(), header.getValue());
		}
		return gcHeaders;
	}
	
	@Override
	public void prepareRequest(RequestData data)
	{
		this.setRequestData(data);
		
		try{
			client = getHTTPClient(data);
			this.setConnectionTimeout(this.connectionTimeout);
			this.setReadTimeout(this.readTimeout);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize the HTTP client request factory
	 * 
	 * @param contentType
	 */
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
	
	/**
	 * Generates the {@link GenericUrl} object for this HTTP request
	 * 
	 * @param url
	 * @param queryParams
	 * @return {@link GenericUrl}
	 */
	protected GenericUrl createCGUrl(String url, Map<String, Object> queryParams){
		GenericUrl gurl = new GenericUrl(url);
		if(queryParams != null){
			gurl.putAll(queryParams);
		}
		return gurl;
	}
	
	/**
	 * 
	 * @param data
	 * @return {@link HttpRequest}
	 * @throws IOException
	 */
	protected HttpRequest getHTTPClient(RequestData data) throws IOException{
		GenericUrl url = createCGUrl(data.url, data.queryParams);
		HttpContent content = null;
		HttpHeaders headers = getCGHTTPHeaders(data.headers);
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
	public void setConnectionTimeout(int timeout) {
		this.client.setConnectTimeout(timeout);
	}

	@Override
	public int getConnectionTimeout() {
		return this.client.getConnectTimeout();
	}

	@Override
	public void setReadTimeout(int timeout) {
		this.client.setReadTimeout(timeout);
	}

	@Override
	public int getReadTimeout() {
		return this.client.getReadTimeout();
	}

	@Override
	public void setHeaders(Headers headers) {
		HttpHeaders gcHeaders = this.getCGHTTPHeaders(headers);
		this.client.setHeaders(gcHeaders);
	}

	@Override
	public Headers getHeaders() {
		return this.requestData.headers;
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
	
	/**
	 * 
	 * @since 1.0
	 * @author Jorge Gonzalez
	 *
	 */
	public static class CGRequestFactory extends RequestFactory{

		/**
		 * 
		 */
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
