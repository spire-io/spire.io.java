/**
 * 
 */
package spire;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.Key;

import java.io.IOException;
import java.util.List;

import spire.api.Api;

/**
 * @author Jorge Gonzalez - Spire.io
 *
 */
public class Spire {
	public static final String SPIRE_URL = "http://localhost:1337";
	
	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	static final JsonFactory JSON_FACTORY = new JacksonFactory();
	
	private String key;
	private String spire_url;
	private Api api;

	public Spire(){
		this.spire_url = SPIRE_URL;
		this.api = new Api(spire_url);
	}
	
	/**
	 * 
	 * @param key
	 */
	public Spire(String url, String version){
		this.spire_url = url;
		this.api = new Api(spire_url, version);
	}
	
//	public static class APIResource {
//		@Key
//		public String url;
//	}
	
	public Api discover() throws Exception{
//		HttpRequestFactory requestFactory =
//		        HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
//		          @Override
//		          public void initialize(HttpRequest request) {
//		            request.addParser(new JsonHttpParser(JSON_FACTORY));
//		          }
//		        });
//		GenericUrl url = new GenericUrl(spire_url);
//	    HttpRequest request = requestFactory.buildGetRequest(url);
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.set("Accept", "application/json");
//	    request.setHeaders(headers);
//	    APIResource api = request.execute().parseAs(APIResource.class);
	    
		api.discover();
		return api;
//	    System.out.println("API result....");
//	    System.out.println(description.url);
	}
}

