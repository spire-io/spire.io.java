/**
 * 
 */
package spire.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import spire.request.*;

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
			throw new ResponseException(response);
		description = response.parseAs(APIDescriptionModel.class);
	    
		System.out.println("API result....");
	    System.out.println(description.url);
	    System.out.println(description.resources.size());
	    for (Map.Entry<String, ResourceModel> resource : description.resources.entrySet()) {
	    	ResourceModel value = resource.getValue();
	    	String rurl = (String)value.get("url");
	    	System.out.println(resource.getKey() + " => " + rurl );
		}
	    
//	    for (Map.Entry<String, Object> schemaItem : description.schema.entrySet()) {
//	    	System.out.println(schemaItem.getKey());
//	    	Map<String, Object> schema = (Map<String, Object>)schemaItem.getValue();
//	    	for (Map.Entry<String, Object> schemaResourceItem : schema.entrySet()) {
//	    		System.out.println(schemaResourceItem.getKey());
//	    	}
//	    }
	    
	    System.out.println("schema url => " + description.schema.getUrl());
	    System.out.println("account mediaType => " + description.schema.getMediaType("account"));
//	    APISchemaItemsModel schemas = description.schema.getSchemaItems();
//	    for (Map.Entry<String, APISchemaItemPropertiesModel> schemaItem : schemas.entrySet()) {
//	    	String resource = schemaItem.getKey();
//	    	APISchemaItemPropertiesModel prop = schemaItem.getValue(); 
//	    	System.out.println(resource + " => " + prop.getMediaType() );
//		}
	}
}
