/**
 * 
 */
package io.spire.api;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;

/**
 * @author Jorge Gonzalez
 *
 */
public class Channel extends Resource {

	/**
	 * 
	 */
	public Channel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param schema
	 */
	public Channel(APISchemaModel schema) {
		super(schema);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param model
	 * @param schema
	 */
	public Channel(ResourceModel model, APISchemaModel schema) {
		super(model, schema);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see io.spire.api.Resource#initialize()
	 */
	@Override
	protected void initialize() {

	}

	/* (non-Javadoc)
	 * @see io.spire.api.Resource#getResourceName()
	 */
	@Override
	public String getResourceName() {
		return this.getClass().getSimpleName().toLowerCase();
	}
}
