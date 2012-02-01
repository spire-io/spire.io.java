/**
 * 
 */
package io.spire.api;

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
		public HashMap<String, ResourceModel> resources;
		
		public static class APISchemaModel extends HashMap<String, Object> {
			public String getUrl(){
				return (String)this.get("url");
			}
			
			public String getMediaType(String resource){
				Map<String, Object> schemas = (Map<String, Object>)this.get(Api.API_VERSION);
				Map<String, Object> schema = (Map<String, Object>)schemas.get(resource);
				String mediaType = (String)schema.get("mediaType");
				return mediaType;
			}
		}
		
		@Key
		public APISchemaModel schema;
	}
	
	public void discover() throws ResponseException, IOException {
		RequestData data = RequestFactory.getRequestData();
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
	    System.out.println("schema url => " + description.schema.getUrl());
	    System.out.println("account mediaType => " + description.schema.getMediaType("account"));
	}
}
