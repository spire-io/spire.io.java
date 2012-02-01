/**
 * 
 */
package io.spire.api;

import java.util.HashMap;

/**
 * @author jorge
 *
 */
public class Resource {
	
	private ResourceModel model;

	/**
	 * 
	 */
	public Resource() {
	}
	
	public static class ResourceCollectionModel extends HashMap<String, ResourceModel> {
		public ResourceModel getResource(String resourceName){
			return (ResourceModel)this.get(resourceName); 
		}
	}
	
	public static class ResourceModel extends HashMap<String, Object> {
		public String getUrl(){
			return (String)this.get("url");
		}
	}
	
	public String getUrl(){
		return model.getUrl();
	}

}
