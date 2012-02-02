/**
 * 
 */
package io.spire.api;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;
import io.spire.api.Session.SessionModel;
import io.spire.request.Request.RequestType;
import io.spire.request.Request;
import io.spire.request.RequestData;
import io.spire.request.RequestFactory;
import io.spire.request.Response;
import io.spire.request.ResponseException;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author jorge
 *
 */
public class Resource {
	
	private ResourceModel model;
	private String url;
	private APISchemaModel schema;
	
	
	/**
	 * 
	 */
	public Resource() {
	}
	
	public Resource(APISchemaModel schema) {
		this.schema = schema;
	}
	
	public Resource(ResourceModel model, APISchemaModel schema) {
		this(schema);
		this.model = model;
	}
	
	public static class ResourceCollectionModel extends HashMap<String, ResourceModel> {
		private static final long serialVersionUID = -2040538068264592599L;

		public <T>T getProperty(String propertyName, Class<T> type){
			return (T)this.get(propertyName);
		}
		
		public ResourceModel getResource(String resourceName){
			return this.getProperty(resourceName, ResourceModel.class); 
		}
	}
	
	public static class ResourceModel extends HashMap<String, Object> {
		private static final long serialVersionUID = -2175184096959826160L;

		public <T>T getProperty(String propertyName, Class<T> type){
			return (T)this.get(propertyName);
		}
		
		public void setProperty(String propertyName, Object data){
			this.put(propertyName, data);
		}
	}
	
	public String getUrl(){
		return model.getProperty("url", String.class);
	}
	
	public String getResourceName(){
		return Resource.class.getName().toLowerCase();
	}
	
	public String getMediaType(){
		String resourceName = this.getResourceName();
		return schema.getMediaType(resourceName);
	}
	
	public String getCapability(){
		return model.getProperty("capability", String.class);
	}
	
	public void get(){
		
	}

	public void update(){
		
	}
	
	public void delete() throws ResponseException, IOException{
		RequestData data = RequestFactory.createRequestData();
		data.method = RequestType.HTTP_DELETE;
		data.url = model.getProperty("url", String.class);
		
		data.headers.put("Authorization", "Capability " + model.getProperty("capability", String.class));
		data.headers.put("Accept", this.getMediaType());
		data.headers.put("Content-Type", this.getMediaType());
		
		Request request = RequestFactory.createRequest(data);
		Response response = request.send();
		if(!response.isSuccessStatusCode())
			throw new ResponseException(response, "Error deleting " + getResourceName());
		
		System.out.println("deleted resource " + getResourceName());
	}
}
