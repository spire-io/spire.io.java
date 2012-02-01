/**
 * 
 */
package io.spire.api;

import java.util.HashMap;

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
		
	}
	
	public String getUrl(){
		return model.getUrl();
	}

}
