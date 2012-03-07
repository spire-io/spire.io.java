/**
 * 
 */
package io.spire.api;

import io.spire.api.Api.ApiDescriptionModel.ApiSchemaModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Internal representation of Spire {@link Events}
 * 
 * @since 1.0
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
	public Message(ApiSchemaModel schema) {
		super(schema);
	}

	/**
	 * @param model
	 * @param schema
	 */
	public Message(ResourceModel model, ApiSchemaModel schema) {
		super(model, schema);
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
	 * Gets the message content
	 * 
	 * @return {@link Object}
	 */
	public Object getContent(){
		return this.model.getProperty("content", Object.class);
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
	
	/**
	 * Describes certain properties used for retrieving messages
	 * 
	 * @since 1.0
	 * @author Jorge Gonzalez
	 *
	 */
	public static class MessageOptions{
		public String timestamp;
		// timeout option of 0 means no long poll,
		public int timeout;
		public MessageOrderBy orderBy;
		// delay response from the server... ahh??
		public int delay;
		
		/**
		 * 
		 */
		public MessageOptions(){
			this.timestamp = "0";
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
			queryParams.put("last-message", timestamp);
			queryParams.put("timeout", Integer.toString(timeout));
			queryParams.put("delay", delay);
			queryParams.put("order-by", this.getOrderByOptionString());
			
			return queryParams;
		}
	}

}
