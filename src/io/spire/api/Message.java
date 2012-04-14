/**
 * 
 */
package io.spire.api;

import io.spire.api.Api.ApiDescriptionModel.ApiSchemaModel;
import io.spire.api.Events.EventType;

import java.util.HashMap;
import java.util.Map;

/**
 * Internal representation of Spire {@link Events}
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public class Message extends Event {

	/**
	 * 
	 */
	public Message() {
		super(EventType.Message);
	}

	/**
	 * @param schema
	 */
	public Message(ApiSchemaModel schema) {
		super(schema, EventType.Message);
	}

	/**
	 * @param model
	 * @param schema
	 */
	public Message(ResourceModel model, ApiSchemaModel schema) {
		super(model, schema, EventType.Message);
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
	
	/**
	 * Describes certain properties used for retrieving messages
	 * 
	 * @since 1.0
	 * @author Jorge Gonzalez
	 *
	 */
	public static class MessageOptions{
		public String last;
		// timeout option of 0 means no long poll,
		public int timeout;
		public MessageOrderBy orderBy;
		// delay response from the server... ahh??
		public int delay;
		
		/**
		 * 
		 */
		public MessageOptions(){
			this.last = "0";
			this.timeout = 0;
			this.orderBy = MessageOrderBy.Desc;
			this.delay = 0;
		}
		
		/**
		 * The chronological order in which messages are retrieve
		 * 
		 * @since 1.0
		 * @author Jorge Gonzalez
		 *
		 */
		public enum MessageOrderBy{
			Asc,
			Desc
		}
		
		/**
		 * Gets the String representation of {@link MessageOrderBy} 
		 * 
		 * @return {@link String}
		 */
		public String getOrderByOptionString(){
			return this.orderBy.toString().toLowerCase(); 
		}
		
		/**
		 * Gets options as a Map collection
		 * 
		 * @return Map
		 */
		public Map<String, Object> getMapOptions(){
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("last", last);
			queryParams.put("timeout", Integer.toString(timeout));
			queryParams.put("delay", delay);
			queryParams.put("order-by", this.getOrderByOptionString());
			
			return queryParams;
		}
	}

}
