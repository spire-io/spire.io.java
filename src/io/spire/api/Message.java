/**
 * 
 */
package io.spire.api;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;

import java.util.HashMap;
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
	
	public String getTimestamp(){
		return this.model.getProperty("timestamp", Object.class).toString();
	}
	
	public String channelName(){
		return this.model.getProperty("channel", String.class);
	}
	
	public static class MessageOptions{
		public String timestamp = "0";
		// timeout option of 0 means no long poll,
		public int timeout = 0;
		public MessageOrderBy orderBy = MessageOrderBy.Desc;
		// delay response from the server... ahh??
		public int delay = 0;
		
		public MessageOptions(){
		}
		
		public enum MessageOrderBy{
			Asc,
			Desc
		}
		
		public String getOrderByOptionString(){
			return this.orderBy.toString().toLowerCase(); 
		}
		
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
