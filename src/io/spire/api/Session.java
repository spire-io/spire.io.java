/**
 * 
 */
package io.spire.api;

/**
 * @author jorge
 *
 */
public class Session extends Resource {

	private SessionModel model;
	/**
	 * 
	 */
	public Session() {
	}
	
	public Session(SessionModel model) {
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
