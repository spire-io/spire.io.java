/**
 * 
 */
package io.spire.api;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;

/**
 * @author jorge
 *
 */
public class Session extends Resource {

	private SessionModel model;
	/**
	 * 
	 */
	public Session(SessionModel model, APISchemaModel schemas) {
		super(schemas);
		this.model = model;
	}
	
	public static class SessionModel extends ResourceModel {
		private static final long serialVersionUID = 6181456514065378756L;
	}
	
	public String getUrl(){
		return model.getProperty("url", String.class);
	}
	
	public <T>T getResource(String resourceName, Class<T> type){
		ResourceCollectionModel resources = model.getProperty("resources", ResourceCollectionModel.class);
		T resource = resources.getProperty(resourceName, type);
		return resource;
	}

}
