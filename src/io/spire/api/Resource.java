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
	private String url;
	
	/**
	 * 
	 */
	public Resource() {
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
	}
	
	public String getUrl(){
		return model.getProperty("url", String.class);
	}
	
	public String getCapability(){
		return model.getProperty("capability", String.class);
	}
	

}
