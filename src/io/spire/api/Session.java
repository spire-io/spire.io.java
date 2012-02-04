/**
 * 
 */
package io.spire.api;

import java.util.HashMap;
import java.util.Map;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;
import io.spire.api.Channel.Channels;
import io.spire.api.Resource.ResourceModel;

/**
 * @author jorge
 *
 */
public class Session extends Resource {

	protected Account account;
	protected Channels channels;
	
	/**
	 * 
	 */
	public Session(ResourceModel model, APISchemaModel schema) {
		super(model, schema);
	}
	
	@Override
	protected void initialize(){
		ResourceModel resourceModel = getResourceModel("account");
		account = new Account(resourceModel, this.schema);
		
		resourceModel = getResourceModel("channels");
		channels = new Channels(resourceModel, this.schema);
	}
	
	@Override
	protected ResourceModel getResourceModel(String resourceName){
		@SuppressWarnings("unchecked")
		Map<String, Object> resources = model.getProperty("resources", Map.class);
		@SuppressWarnings("unchecked")
		Map<String, Object> rawModel = (Map<String, Object>)resources.get(resourceName);
		return new ResourceModel(rawModel);
	}
	
	@Override
	public String getResourceName(){
		return this.getClass().getSimpleName().toLowerCase();
	}
	
	public Account getAccount(){
		return account;
	}
	
	public Channels getChannels(){
		return channels;
	}

}
