/**
 * 
 */
package io.spire;

import io.spire.api.Api;
import io.spire.api.Billing;
import io.spire.api.Channel;
import io.spire.api.Channel.Channels;
import io.spire.api.Session;
import io.spire.request.ResponseException;

import java.io.IOException;


/**
 * @author Jorge Gonzalez - Spire.io
 *
 */
public class Spire {
	public static final String SPIRE_URL = "http://localhost:1337";
	
	private String spire_url;
	private Api api;
	private Session session;
	private Billing billing;

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
	
	public Api getApi(){
		return api;
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
	
	public Channels getChannels() throws ResponseException, IOException{
		return session.getChannels();
	}
	
	public Billing billing() throws ResponseException, IOException{
		billing = api.billing();
		return billing;
	}
	
	public Channels channels() throws ResponseException, IOException{
		Channels channels = this.getChannels();
		channels.get();
		return channels;
	}
	
	public Channel createChannel(String name) throws ResponseException, IOException{
		return session.createChannel(name);
	}
}

