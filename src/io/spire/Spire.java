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
	
	private String spire_url;
	private Api api;
	private Session session;

	/**
	 * 
	 */
	public Spire(){
		this.spire_url = SPIRE_URL;
		this.api = new Api(spire_url);
	}
	
	/**
	 * 
	 * @param url
	 */
	public Spire(String url){
		this.spire_url = url;
		this.api = new Api(spire_url);
	}
	
	/**
	 * 
	 * @param url
	 * @param version
	 */
	public Spire(String url, String version){
		this.spire_url = url;
		this.api = new Api(spire_url, version);
	}
	
	public Session getSession(){
		return session;
	}
	
	public void discover() throws ResponseException, IOException{
		api.discover();
	}
	
	public void start(String accountKey) throws ResponseException, IOException{
		session  = api.createSession(accountKey);
	}
	
	public void login(String email, String password) throws ResponseException, IOException{
		session  = api.login(email, password);
	}
	
	public void register(String email, String password, String passwordConfirmation) throws ResponseException, IOException{
		session = api.createAccount(email, password, passwordConfirmation);
	}
	
	public void passwordReset(String email) throws ResponseException, IOException{
		
	}
	
	public void deleteAccount() throws ResponseException, IOException{
		session.getAccount().delete();
	}
}

