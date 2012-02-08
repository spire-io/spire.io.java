/**
 * 
 */
package io.spire.api;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;

import java.util.Map;

/**
 * @author Jorge Gonzalez
 *
 */
public class Message extends Resource {

	/**
	 * 
	 */
	public Message() {
	}

	/**
	 * @param schema
	 */
	public Message(APISchemaModel schema) {
		super(schema);
	}

	/**
	 * @param model
	 * @param schema
	 */
	public Message(ResourceModel model, APISchemaModel schema) {
		super(model, schema);
	}

	/* (non-Javadoc)
	 * @see io.spire.api.Resource#initialize()
	 */
	@Override
	protected void initialize() {
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
	
	public Object getContent(){
		return this.model.getProperty("content", Object.class);
	}
	
	public <T>T getContent(Class<T> type){
		return this.model.getProperty("content", type);
	}
	
	public int getTimestamp(){
		return this.model.getProperty("timestamp", Integer.class);
	}

}
