/**
 * 
 */
package io.spire.api;

import io.spire.api.Resource.ResourceModel;
import io.spire.request.*;
import io.spire.request.Request.RequestType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import com.google.api.client.util.Key;

/**
 * @author jorge
 *
 */
public class Api {

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
	
	public APIDescriptionModel getApiDescription(){
		return description;
	}
	
	public void setApiDescription(APIDescriptionModel description){
		this.description = description;
	}
	
	public static class APIDescriptionModel {
		@Key
		public String url;
		
		
		public static class APIResourceCollectionModel extends HashMap<String, APIResourceModel> implements ResourceModelInterface {
			@Override
			public <T> T getProperty(String propertyName, Class<T> type) {
				return (T)this.get(propertyName);
			}

			@Override
			public void setProperty(String propertyName, Object data) {
			}
			
			public APIResourceModel getResource(String resourceName){
				return this.getProperty(resourceName, APIResourceModel.class); 
			}
		}
		
		@Key
		public APIResourceCollectionModel resources;
		
		public static class APIResourceModel extends HashMap<String, Object> implements ResourceModelInterface {			
			private static final long serialVersionUID = 5222700238203763225L;

			@Override
			public <T> T getProperty(String propertyName, Class<T> type) {
				return (T)this.get(propertyName);
			}

			@Override
			public void setProperty(String propertyName, Object data) {
			}
		}
		
		public static class APISchemaModel extends APIResourceModel {			
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
		Map<String, Object> rawModel = response.parseAs(HashMap.class);
		Session session = new Session(new ResourceModel(rawModel), description.schema);
		
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
		Map<String, Object> rawModel = response.parseAs(HashMap.class);
		Session session = new Session(new ResourceModel(rawModel), description.schema);
		
		return session;
	}
	
	@SuppressWarnings("unchecked")
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
		
		Map<String, Object> rawModel = response.parseAs(HashMap.class);
		Session session = new Session(new ResourceModel(rawModel), description.schema);
		
		return session;
	}
	
	public void resetPassword(String email){
		
	}
	
	public Billing billing() throws ResponseException, IOException{
		RequestData data = RequestFactory.createRequestData();
		data.method = RequestType.HTTP_GET;
		data.url = description.resources.getResource("billing").getProperty("url", String.class);
		data.headers.put("Accept", "application/json");
		
		Request request = RequestFactory.createRequest(data);
		Response response = request.send();
		if(!response.isSuccessStatusCode())
			throw new ResponseException(response, "Error getting billing: " + response.getStatusCode());
		
		Map<String, Object> rawModel = response.parseAs(HashMap.class);
		Billing billing = new Billing(new ResourceModel(rawModel), description.schema);
		return billing;
	}
}
