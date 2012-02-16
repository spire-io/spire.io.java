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
 * Encapsulates detailed information about the Spire Api resources.  
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public class Api {

	public static String API_VERSION = "1.0";
	
	private String url;
	private APIDescriptionModel description;
	
	/**
	 * 
	 * @param url the Spire Api entry point
	 */
	public Api(String url) {
		this.url = url; 
	}
	
	/**
	 * 
	 * @param url entry point
	 * @param version
	 */
	public Api(String url, String version) {
		this(url);
		Api.API_VERSION = version; 
	}
	
	/**
	 * Get access to the Api description resource
	 * 
	 * @return {@link APIDescriptionModel}
	 */
	public APIDescriptionModel getApiDescription(){
		return description;
	}
	
	/**
	 * Sets the {@link APIDescriptionModel} description object
	 * 
	 * @param description
	 */
	public void setApiDescription(APIDescriptionModel description){
		this.description = description;
	}
	
	/**
	 * Holds the Spire Api resource model from {@link Api#discover()}
	 * This class knows how to parsed the APIDescriptionModel
	 * 
	 * @since 1.0
	 * @author Jorge Gonzalez
	 *
	 */
	public static class APIDescriptionModel{
		
		@Key
		private String url;
		
		public String getUrl(){
			return this.url;
		}
		
		/**
		 * An Abstract Map representation of Api Resources
		 * 
		 * @since 1.0
		 * @author Jorge Gonzalez
		 *
		 */
		public static class APIResourceCollectionModel extends HashMap<String, APIResourceModel> implements ResourceModelInterface {
			/**
			 * 
			 */
			private static final long serialVersionUID = -780744225506092309L;

			@SuppressWarnings("unchecked")
			@Override
			public <T> T getProperty(String propertyName, Class<T> type) {
				return (T)this.get(propertyName);
			}

			@Override
			public void setProperty(String propertyName, Object data) {
			}
			
			/**
			 * Provides access to resources
			 * 
			 * @param resourceName
			 * @return {@link APIResourceModel}
			 */
			public APIResourceModel getResource(String resourceName){
				return this.getProperty(resourceName, APIResourceModel.class); 
			}
		}
		
		@Key
		private APIResourceCollectionModel resources;
		
		/**
		 * Gets Api resource descriptions
		 * 
		 * @return {@link APIResourceCollectionModel}
		 */
		public APIResourceCollectionModel getResources(){
			return this.resources;
		}
		
		/**
		 * General representation of Api resource models
		 * 
		 * since 1.0
		 * @author Jorge Gonzalez
		 *
		 */
		public static class APIResourceModel extends HashMap<String, Object> implements ResourceModelInterface {			
			private static final long serialVersionUID = 5222700238203763225L;

			@SuppressWarnings("unchecked")
			@Override
			public <T> T getProperty(String propertyName, Class<T> type) {
				return (T)this.get(propertyName);
			}

			@Override
			public void setProperty(String propertyName, Object data) {
			}
		}
		
		/**
		 * Describes Api resource schemas
		 * 
		 * since 1.0
		 * @author Jorge Gonzalez
		 *
		 */
		public static class APISchemaModel extends APIResourceModel {			
			/**
			 * 
			 */
			private static final long serialVersionUID = 5222700238203763225L;

			@SuppressWarnings("unchecked")
			public String getMediaType(String resource){
				Map<String, Object> schemas = this.getProperty(Api.API_VERSION, Map.class);
				Map<String, Object> schema = (Map<String, Object>)schemas.get(resource);
				String mediaType = (String)schema.get("mediaType");
				return mediaType;
			}
		}
		
		@Key
		private APISchemaModel schema;
		
		/**
		 * Gets Api schema resources
		 * 
		 * @return {@link APISchemaModel}
		 */
		public APISchemaModel getSchema(){
			return this.schema;
		}
	}
	
	/**
	 * GET Api description 
	 * 
	 * @throws ResponseException
	 * @throws IOException
	 */
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
	
	/**
	 * Creates a new Spire session
	 * 
	 * @param accountKey
	 * @return {@link Session}
	 * @throws ResponseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Session createSession(String accountKey) throws ResponseException, IOException{
		RequestData data = RequestFactory.createRequestData();
		data.method = RequestType.HTTP_POST;
		data.url = description.resources.getResource("sessions").getProperty("url", String.class);
		HashMap<String, Object> content = new HashMap<String, Object>();
		content.put("key", accountKey);
		data.body = content;
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
	
	/**
	 * 
	 * Creates a new Spire account 
	 * 
	 * @param email
	 * @param password
	 * @param passwordConfirmation
	 * @return {@link Session}
	 * @throws ResponseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Session createAccount(String email, String password, String passwordConfirmation) 
			throws ResponseException, IOException{
		RequestData data = RequestFactory.createRequestData();
		data.method = RequestType.HTTP_POST;
		data.url = description.resources.getResource("accounts").getProperty("url", String.class);
		HashMap<String, Object> content = new HashMap<String, Object>();
		content.put("email", email);
		content.put("password", password);
		content.put("password_confirmation", passwordConfirmation);
		data.body = content;
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
	
	/**
	 * Authenticates an existing Spire account
	 * 
	 * @param email
	 * @param password
	 * @return {@link Session}
	 * @throws ResponseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Session login(String email, String password) throws ResponseException, IOException{
		RequestData data = RequestFactory.createRequestData();
		data.method = RequestType.HTTP_POST;
		data.url = description.resources.getResource("sessions").getProperty("url", String.class);
		HashMap<String, Object> content = new HashMap<String, Object>();
		content.put("email", email);
		content.put("password", password);
		data.body = content;
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
	
	/**
	 * Returns information about the existing billing plans
	 * 
	 * @return {@link Billing}
	 * @throws ResponseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
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
