/**
 * 
 */
package io.spire.api;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;
import io.spire.api.Subscription.Subscriptions;
import io.spire.request.ResponseException;

/**
 * @author Jorge Gonzalez
 *
 */
public class Channel extends Resource {

	private Map<String, Subscription> subscriptionCollection;
	
	/**
	 * 
	 */
	public Channel() {
		super();
	}

	/**
	 * @param schema
	 */
	public Channel(APISchemaModel schema) {
		super(schema);
	}

	/**
	 * @param model
	 * @param schema
	 */
	public Channel(ResourceModel model, APISchemaModel schema) {
		super(model, schema);
	}

	/* (non-Javadoc)
	 * @see io.spire.api.Resource#initialize()
	 */
	@Override
	protected void initialize() {
		subscriptionCollection = model.getMapCollection("subscriptions", Subscription.class, schema);
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
	
	public Set<String> getSubcriptions(){
		return subscriptionCollection.keySet();
	}
	
	public Subscription getSubcription(String name){
		return subscriptionCollection.get(name);
	}
	
	// TODO: this should implements Collection<Channel>
	public static class Channels extends Resource{
		private Map<String, Channel> channelCollection;
		
		/**
		 * 
		 */
		public Channels() {
			super();
		}

		/**
		 * @param schema
		 */
		public Channels(APISchemaModel schema) {
			super(schema);
		}

		/**
		 * @param model
		 * @param schema
		 */
		public Channels(ResourceModel model, APISchemaModel schema) {
			super(model, schema);
		}
		
		@Override
		protected void initialize() {
			channelCollection = model.getMapCollection("resources", Channel.class, schema);
		}
		
		@Override
		protected void updateModel(Map<String, Object> rawModel){
			this.model.setProperty("resources", rawModel);
			this.initialize();
		}
	
		@Override
		public String getResourceName() {
			return this.getClass().getSimpleName().toLowerCase();
		}
		
		public Channel getChannel(String name){
			return channelCollection.get(name);
		}
		
		public Channel addChannel(Channel channel){
			return channelCollection.put(channel.getName(), channel);
		}
		
		public int size(){
			return channelCollection.size();
		}
		
		@Override
		protected void addModel(Map<String, Object> rawModel){
			Channel channel = new Channel(new ResourceModel(rawModel), this.schema);
			this.addChannel(channel);
		}
		
		public void createChannel(String name) throws ResponseException, IOException{
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("name", name);
			Map<String, String> headers = new HashMap<String, String>();
			Channel channel = new Channel();
			headers.put("Accept", this.schema.getMediaType(channel.getResourceName()));
			headers.put("Content-Type", this.schema.getMediaType(channel.getResourceName()));
			super.post(content, headers);
		}
	}
}
