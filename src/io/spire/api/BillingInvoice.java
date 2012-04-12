/**
 * 
 */
package io.spire.api;

import java.util.Map;

import io.spire.api.Api.ApiDescriptionModel.ApiSchemaModel;

/**
 * Describes the Account's billing invoice
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 * @deprecated v1.1.4
 *
 */
public class BillingInvoice extends Resource {

	/**
	 * 
	 */
	public BillingInvoice() {
		super();
	}

	/**
	 * @param schema
	 */
	public BillingInvoice(ApiSchemaModel schema) {
		super(schema);
	}

	/**
	 * @param model
	 * @param schema
	 */
	public BillingInvoice(ResourceModel model, ApiSchemaModel schema) {
		super(model, schema);
	}

	/* (non-Javadoc)
	 * @see io.spire.api.Resource#getResourceName()
	 */
	@Override
	public String getResourceName() {
		return this.getClass().getSimpleName().toLowerCase();
	}
	
	@Override
	protected void addModel(Map<String, Object> rawModel) {
		
	}
}
