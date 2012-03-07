/**
 * 
 */
package io.spire.api;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.spire.api.Api.ApiDescriptionModel.ApiSchemaModel;
import io.spire.request.ResponseException;

/**
 * A channel resource allows you to get information about a channel, 
 * publish messages to a channel, or close (delete) a channel.
 * 
 * @since 1.0
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
	public Channel(ApiSchemaModel schema) {
		super(schema);
	}

	/**
	 * @param model
	 * @param schema
	 */
	public Channel(ResourceModel model, ApiSchemaModel schema) {
		super(model, schema);
	}

	/* (non-Javadoc)
	 * @see io.spire.api.Resource#initialize()
	 */
	@Override
	protected void initialize() {
		super.initialize();
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
	
	/**
	 * Gets a Collection of subscriptions names for this channel
	 * 
	 * @return {@link Set}
	 */
	public Set<String> getSubscriptionNames(){
		return subscriptionCollection.keySet();
	}
	
	/**
	 * Gets a Collection of all {@link Subscription} for this channel
	 * 
	 * @return Collection
	 */
	public Collection<Subscription> getSubscriptions(){
		return subscriptionCollection.values();
	}
	
	/**
	 * Gets a subscription by its name
	 * 
	 * @param name
	 * @return {@link Subscription}
	 */
	public Subscription getSubcription(String name){
		return subscriptionCollection.get(name);
	}
	
	/**
	 * Creates a subscription to this channel
	 * 
	 * @param name
	 * @param session
	 * @return {@link Subscription}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Subscription subscribe(String name, Session session) throws ResponseException, IOException{
		Subscription subscription = session.subscribe(name, this.getName());
		Channel channel = session.channels.getChannel(this.getName());
		this.copy(channel);
		return subscription;
	}
	
	/**
	 * Publish a message to this channel
	 * 
	 * @param content of the message to publish
	 * @return {@link Message}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Message publish(Object content) throws ResponseException, IOException{
		Map<String, Object> messageContent = new HashMap<String, Object>();
		messageContent.put("content", content);
		
		Map<String, String> headers = new HashMap<String, String>();
		Message message = new Message(this.schema);
		headers.put("Authorization", "Capability " + capability.getCapabilityFor("publish"));
		headers.put("Accept", message.getMediaType());
		headers.put("Content-Type", message.getMediaType());
		
		Map<String, Object> rawModel = super.post(messageContent, headers);
		
		message.setInnerModel(new ResourceModel(rawModel));
		message.initialize();
		return message;
	}
	
	/**
	 * Describes a Collection of channels
	 * The channels resources allows you to create new channels.
	 * 
	 * @since 1.0
	 * @author Jorge Gonzalez
	 *
	 */
	public static class Channels extends Resource implements Map<String, Channel>{
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
		public Channels(ApiSchemaModel schema) {
			super(schema);
		}

		/**
		 * @param model
		 * @param schema
		 */
		public Channels(ResourceModel model, ApiSchemaModel schema) {
			super(model, schema);
		}
		
		@Override
		protected void initialize() {
			super.initialize();
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
		
		/**
		 * Gets a channel by its name
		 * @see #get(Object)
		 * 
		 * @param name
		 * @return {@link Channel}
		 */
		public Channel getChannel(String name){
			return channelCollection.get(name);
		}
		
		/**
		 * Adds a channel to the Collection
		 * @see #put(String, Channel)
		 *  
		 * @param channel
		 * @return {@link Channel}
		 */
		public Channel addChannel(Channel channel){
			return channelCollection.put(channel.getName(), channel);
		}
		
		@Override
		protected void addModel(Map<String, Object> rawModel){
			Channel channel = new Channel(new ResourceModel(rawModel), this.schema);
			this.addChannel(channel);
		}
		
		/**
		 * Creates a new Channel
		 * 
		 * @param name
		 * @throws ResponseException
		 * @throws IOException
		 */
		public void createChannel(String name) throws ResponseException, IOException{
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("name", name);
			Map<String, String> headers = new HashMap<String, String>();
			// FIXME: quick fix... may be is better to use 'Channel.class.getSimpleName().toLowerCase()' ?
			Channel channel = new Channel();
			headers.put("Accept", this.schema.getMediaType(channel.getResourceName()));
			headers.put("Content-Type", this.schema.getMediaType(channel.getResourceName()));
			super.post(content, headers);
		}
		
		@Override
		public void get() throws ResponseException, IOException{
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Authorization", "Capability " + capability.getCapabilityFor("all"));
			super.get(null, headers);
		}
		
		// Map interface implementation
		@Override
		public void clear() {
			channelCollection.clear();
		}
		
		@Override
		public int size() {
			return channelCollection.size();
		}

		@Override
		public boolean containsKey(Object key) {
			return channelCollection.containsKey(key);
		}

		@Override
		public boolean containsValue(Object value) {
			return channelCollection.containsValue(value);
		}

		@Override
		public Set<java.util.Map.Entry<String, Channel>> entrySet() {
			return channelCollection.entrySet();
		}

		@Override
		public Channel get(Object key) {
			return channelCollection.get(key);
		}

		@Override
		public boolean isEmpty() {
			return channelCollection.isEmpty();
		}

		@Override
		public Set<String> keySet() {
			return channelCollection.keySet();
		}

		@Override
		public Channel put(String key, Channel value) {
			return channelCollection.put(key, value);
		}

		@Override
		public void putAll(Map<? extends String, ? extends Channel> m) {
			channelCollection.putAll(m);
		}

		@Override
		public Channel remove(Object key) {
			return channelCollection.remove(key);
		}

		@Override
		public Collection<Channel> values() {
			return channelCollection.values();
		}
	}
}
