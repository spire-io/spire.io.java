/**
 * 
 */
package io.spire.api;

import java.util.Map;

import io.spire.api.Api.ApiDescriptionModel.ApiSchemaModel;
import io.spire.api.Events.EventType;

/**
 * @author Jorge Gonzalez
 *
 */
public class Part extends Event {

	/**
	 * 
	 */
	public Part() {
		super(EventType.Part);
	}

	/**
	 * @param schema
	 */
	public Part(ApiSchemaModel schema) {
		super(schema, EventType.Part);
	}

	/**
	 * @param model
	 * @param schema
	 */
	public Part(ResourceModel model, ApiSchemaModel schema) {
		super(model, schema, EventType.Part);
	}
	
	/* (non-Javadoc)
	 * @see io.spire.api.Resource#addModel(java.util.Map)
	 */
	@Override
	protected void addModel(Map<String, Object> rawModel) {
	}

	/* (non-Javadoc)
	 * @see io.spire.api.Resource#getResourceName()
	 */
	@Override
	public String getResourceName() {
		return this.getClass().getSimpleName().toLowerCase();
	}

	@Override
	public <T> T getContent(Class<T> type) {
		return null;
	}

}
