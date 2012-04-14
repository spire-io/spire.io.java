/**
 * 
 */
package io.spire.api;

import io.spire.api.Api.ApiDescriptionModel.ApiSchemaModel;
import io.spire.api.Events.EventType;

/**
 * @author Jorge Gonzalez
 *
 */
public abstract class Event extends Resource {

	protected EventType type;
	
	/**
	 * 
	 */
	protected Event(EventType type) {
		this.type = type;
	}

	/**
	 * @param schema
	 */
	protected Event(ApiSchemaModel schema, EventType type) {
		super(schema);
	}

	/**
	 * @param model
	 * @param schema
	 */
	protected Event(ResourceModel model, ApiSchemaModel schema, EventType type) {
		super(model, schema);
	}
	
	/**
	 * Get event type
	 * 
	 * @return String
	 */
	public String getType(){
		return this.model.getProperty("type", Object.class).toString();
	}
	
	/**
	 * Gets the message content casting it by the specified Class type
	 * 
	 * Returns null if the object could not be parsed
	 * 
	 * @param type
	 * @return T<T>
	 */
	public <T>T getContent(Class<T> type){
		return this.model.getProperty("content", type);
	}
	
	/**
	 * Gets the message content
	 * 
	 * @return {@link Object}
	 */
	public Object getContent(){
		return this.getContent(Object.class);
	}
	
	/**
	 * Message timestamp
	 * 
	 * @return String
	 */
	public String getTimestamp(){
		return this.model.getProperty("timestamp", Object.class).toString();
	}
	
	/**
	 * The channel name this message belongs to
	 * 
	 * @return {@link String}
	 */
	public String channelName(){
		return this.model.getProperty("channel", String.class);
	}
}
