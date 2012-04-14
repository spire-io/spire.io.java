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
public class Join extends Event {

	/**
	 * 
	 */
	public Join() {
		super(EventType.Join);
	}

	/**
	 * @param schema
	 */
	public Join(ApiSchemaModel schema) {
		super(schema, EventType.Join);
	}

	/**
	 * @param model
	 * @param schema
	 */
	public Join(ResourceModel model, ApiSchemaModel schema) {
		super(model, schema, EventType.Join);
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
