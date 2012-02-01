/**
 * 
 */
package io.spire.api;

import io.spire.api.Session.SessionModel;
import io.spire.request.*;
import io.spire.request.Request.RequestType;

import java.io.IOException;
import java.util.Map;


import com.google.api.client.util.Key;

/**
 * @author jorge
 *
 */
public class Api extends Resource {

	public static String API_VERSION = "1.0";
	
	private String url;
	private APIDescriptionModel description;
	
	/**
	 * 
	 */
	public Api(String url) {
		this.url = url; 
	}
	
	public Api(String url, String version) {
		this(url);
		Api.API_VERSION = version; 
	}
	
	public static class APIDescriptionModel {
		@Key
		public String url;
		
		@Key
		public ResourceCollectionModel resources;
		
		public static class APISchemaModel extends ResourceModel {			
			private static final long serialVersionUID = 5222700238203763225L;

			public String getMediaType(String resource){
				Map<String, Object> schemas = this.getProperty(Api.API_VERSION, Map.class);
				Map<String, Object> schema = (Map<String, Object>)schemas.get(resource);
				String mediaType = (String)schema.get("mediaType");
				return mediaType;
			}
		}
		
		@Key
		public APISchemaModel schema;
	}
	
	public void discover() throws ResponseException, IOException {
		RequestData data = RequestFactory.createRequestData();
		data.method = RequestType.HTTP_GET;
		data.url = url;
		data.headers.put("Accept", "application/json");
		
		Request request = RequestFactory.createRequest(data);
		Response response = request.send();
		if(!response.isSuccessStatusCode())
			throw new ResponseException(response, "Error during discovery: " + response.getStatusCode());
		description = response.parseAs(APIDescriptionModel.class);
	    
		// temp test
		System.out.println("API result....");
	    System.out.println(description.url);
	    System.out.println(description.resources.size());
	    System.out.println("schema url => " + description.schema.getProperty("url", String.class));
	    System.out.println("account mediaType => " + description.schema.getMediaType("account"));
	}
	
	public Session createSession(String accountKey) throws ResponseException, IOException{
		RequestData data = RequestFactory.createRequestData();
		data.method = RequestType.HTTP_POST;
		data.url = description.resources.getResource("sessions").getProperty("url", String.class);
		data.body.put("key", accountKey);
		data.headers.put("Accept", description.schema.getMediaType("session"));
		data.headers.put("Content-Type", description.schema.getMediaType("account"));
		
		Request request = RequestFactory.createRequest(data);
		Response response = request.send();
		if(!response.isSuccessStatusCode())
			throw new ResponseException(response, "Error starting a key-based session");
		SessionModel model = response.parseAs(SessionModel.class);
		Session session = new Session(model);
		
		System.out.println("Create Session result....");
		System.out.println(model.getProperty("url", String.class));
		
		return session;
	}
	
	public Session createAccount(String email, String password, String passwordConfirmation) 
			throws ResponseException, IOException{
		RequestData data = RequestFactory.createRequestData();
		data.method = RequestType.HTTP_POST;
		data.url = description.resources.getResource("accounts").getProperty("url", String.class);
		data.body.put("email", email);
		data.body.put("password", password);
		data.body.put("password_confirmation", passwordConfirmation);
		data.headers.put("Accept", description.schema.getMediaType("session"));
		data.headers.put("Content-Type", description.schema.getMediaType("account"));
		
		Request request = RequestFactory.createRequest(data);
		Response response = request.send();
		if(!response.isSuccessStatusCode())
			throw new ResponseException(response, "Error attemping to register");
		SessionModel model = response.parseAs(SessionModel.class);
		Session session = new Session(model);
		
		System.out.println("Login result....");
		System.out.println(model.getProperty("url", String.class));
		
		return session;
	}
	
	public Session login(String email, String password) throws ResponseException, IOException{
		RequestData data = RequestFactory.createRequestData();
		data.method = RequestType.HTTP_POST;
		data.url = description.resources.getResource("sessions").getProperty("url", String.class);
		data.body.put("email", email);
		data.body.put("password", password);
		data.headers.put("Accept", description.schema.getMediaType("session"));
		data.headers.put("Content-Type", description.schema.getMediaType("account"));
		
		Request request = RequestFactory.createRequest(data);
		Response response = request.send();
		if(!response.isSuccessStatusCode())
			throw new ResponseException(response, "Error attemping to login");
		SessionModel model = response.parseAs(SessionModel.class);
		Session session = new Session(model);
		
		System.out.println("Login result....");
		System.out.println(model.getProperty("url", String.class));
		
		return session;
	}
	
	public void resetPassword(String email){
		
	}
	
	public void billing(String accountKey){
		
	}
}
