/**
 * 
 */
package io.spire;

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

import io.spire.api.Api;
import io.spire.api.Session;
import io.spire.request.ResponseException;

import java.io.IOException;
import java.util.List;


/**
 * @author Jorge Gonzalez - Spire.io
 *
 */
public class Spire {
	public static final String SPIRE_URL = "http://localhost:1337";
	
	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	static final JsonFactory JSON_FACTORY = new JacksonFactory();
	
	private String accountKey;
	private String spire_url;
	
	private Api api;
	private Session session;

	public Spire(){
		this.spire_url = SPIRE_URL;
		this.api = new Api(spire_url);
	}
	
	/**
	 * 
	 * @param key
	 */
	public Spire(String url){
		this.spire_url = url;
		this.api = new Api(spire_url);
	}
	
	public Spire(String url, String version){
		this.spire_url = url;
		this.api = new Api(spire_url, version);
	}
	
	public void discover() throws ResponseException, IOException{
		api.discover();
	}
	
	public void start(String accountKey) throws ResponseException, IOException{
		session  = api.createSession(accountKey);
	}
}

