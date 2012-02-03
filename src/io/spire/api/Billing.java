/**
 * 
 */
package io.spire.api;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;

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
	public String getResourceName(){
		return this.getClass().getSimpleName().toLowerCase();
	}
	

}
