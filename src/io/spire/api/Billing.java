/**
 * 
 */
package io.spire.api;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;
import io.spire.api.Resource.ResourceModel;

/**
 * @author Jorge Gonzalez
 *
 */
public class Billing extends Resource {

	/**
	 * 
	 */
	public Billing() {
	}

	/**
	 * @param schema
	 */
	public Billing(APISchemaModel schema) {
		super(schema);
	}

	/**
	 * @param model
	 * @param schema
	 */
	public Billing(ResourceModel model, APISchemaModel schema) {
		super(model, schema);
	}
	
	@Override
	protected void initialize(){
	}
	
	@Override
	public String getResourceName(){
		return this.getClass().getSimpleName().toLowerCase();
	}
	
	public String getId(){
		return this.model.getProperty("id", String.class);
	}
	
	public void setId(String id){
		this.model.setProperty("id", id);
	}
}
