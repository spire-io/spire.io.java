/**
 * 
 */
package io.spire.api;

import java.util.HashMap;
import java.util.Map;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;
import io.spire.api.Resource.ResourceModel;

/**
 * @author jorge
 *
 */
public class Session extends Resource {

	protected Account account;

	/**
	 * 
	 */
	public Session(ResourceModel model, APISchemaModel schema) {
		super(model, schema);
		ResourceModel accountModel = getResource("account");
		account = new Account(accountModel, this.schema);
	}
	
	public ResourceModel getResource(String resourceName){
		Map<String, Object> resources = model.getProperty("resources", Map.class);
		Map<String, Object> rawModel = (Map<String, Object>)resources.get(resourceName);
		return new ResourceModel(rawModel);
	}
	
	public Account getAccount(){
		return account;
	}

}
