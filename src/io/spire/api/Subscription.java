/**
 * 
 */
package io.spire.api;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.spire.api.Api.ApiDescriptionModel.ApiSchemaModel;
import io.spire.api.Message.MessageOptions;
import io.spire.api.Message.MessageOptions.MessageOrderBy;
import io.spire.request.ResponseException;

/**
 * A subscription resources makes it easy to check messages on one or more channels
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public class Subscription extends Resource {
	
	private List<String> channels;
	private String lastTimestamp = "0";
	private int longPollTimeout = 30;
	private MessageOptions defaultMessageOptions;
	private boolean isListening;
	private Map<Integer, Listener> listeners;
	private ListenerManager listenerManager;
	
	/**
	 * 
	 */
	public Subscription() {
		super();
		defaultMessageOptions = new MessageOptions();
		listeners = new HashMap<Integer, Listener>();
	}

	/**
	 * @param schema
	 */
	public Subscription(ApiSchemaModel schema) {
		super(schema);
		defaultMessageOptions = new MessageOptions();
		listeners = new HashMap<Integer, Listener>();
	}

	/**
	 * @param model
	 * @param schema
	 */
	public Subscription(ResourceModel model, ApiSchemaModel schema) {
		super(model, schema);
		defaultMessageOptions = new MessageOptions();
		listeners = new HashMap<Integer, Listener>();
	}

	/* (non-Javadoc)
	 * @see io.spire.api.Resource#initialize()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void initialize() {
		super.initialize();
		channels = this.model.getProperty("channels", List.class);
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
	
	@Override
	protected void updateModel(Map<String, Object> rawModel){
		
	}
	
	/**
	 * Gets a list of channel names from this subscription 
	 * 
	 * @return {@link List} of channel names
	 */
	public List<String> getChannels(){
		return channels;
	}
	
	private void feedListeners(Events events){
		List<Message> messages = events.getMessages();
		for (Message message : messages) {
			for (Listener listener : this.listeners.values()) {
				listener.process(message);
			}
		}
	}
	
	/**
	 * Retrieves messages {@link Events}
	 * 
	 * @param options {@link MessageOptions}
	 * @return {@link Events}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Events retrieveMessages(MessageOptions options) throws ResponseException, IOException{
		Map<String, Object> queryParams = options.getMapOptions();
		
		Map<String, String> headers = new HashMap<String, String>();
		// FIXME: quick fix... may be is better to use 'Subscription.class.getSimpleName().toLowerCase()' ?
		Events events = new Events(schema);
		headers.put("Authorization", "Capability " + capability.getCapabilityFor("messages"));
		headers.put("Accept", events.getMediaType());
		Map<String, Object> rawModel = super.get(queryParams, headers);
		events.updateModel(rawModel);
//		int countMessages = events.getMessages().size();
//		if(countMessages > 0 && this.isListening){
//			this.lastTimestamp = events.getMessages().get(countMessages-1).getTimestamp();
//			this.feedListeners(events);
//		}
		return events;
	}
	
	/**
	 * Retrieve messages with default {@link MessageOptions}
	 * 
	 * @return {@link Events}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Events retrieveMessages() throws ResponseException, IOException{
		return this.retrieveMessages(new MessageOptions());
	}
	
	/**
	 * Retrieve all messages since last timestamp
	 * 
	 * @param options
	 * @return {@link Events}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Events poll(MessageOptions options) throws ResponseException, IOException{
        /*
         * timeout option of 0 means no long poll, so we force it here.
         */
		options.timeout = 0;
		options.timestamp = this.lastTimestamp;
		Events events = this.retrieveMessages(options);
		this.updateTimestamp(events, options);
		return events;
	}
	
	/**
	 * Retrieve all messages since last timestamp with default {@link MessageOptions}
	 * 
	 * @return {@link Events}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Events poll() throws ResponseException, IOException{
        MessageOptions options = new MessageOptions();
		return this.poll(options);
	}
	
	/**
	 * Retrieve all messages since last timestamp
	 * 
	 * @param options
	 * @return {@link Events}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Events longPoll(MessageOptions options) throws ResponseException, IOException{
		if(options.timeout == defaultMessageOptions.timeout)
			options.timeout = this.longPollTimeout;
		options.timestamp = this.lastTimestamp;
		return this.longPoll(options, false);
	}
	
	private boolean updateTimestamp(Events events, MessageOptions options){
		int countMessages = events.getMessages().size();
		if(countMessages > 0){
			if(options.orderBy == MessageOrderBy.Asc){
				this.lastTimestamp = events.getMessages().get(0).getTimestamp();
			}else{
				this.lastTimestamp = events.getMessages().get(countMessages-1).getTimestamp();
			}
			return true;
		}
		return false;
	}
	
	private Events longPoll(MessageOptions options, boolean listernerMode) throws ResponseException, IOException{
		if(options.timeout == defaultMessageOptions.timeout)
			options.timeout = this.longPollTimeout;
		options.timestamp = this.lastTimestamp;
		Events events = this.retrieveMessages(options);
		
		if(!listernerMode){
			this.updateTimestamp(events, options);
		}
		else if(listernerMode && isListening){
			this.updateTimestamp(events, options);
			this.feedListeners(events);
		}
		
		return events;
	}
	
	/**
	 * Retrieve all messages since last timestamp with default {@link MessageOptions}
	 * 
	 * @return {@link Events}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Events longPoll() throws ResponseException, IOException{
		MessageOptions options = new MessageOptions();
		return this.longPoll(options, false);
	}
	
	/**
	 * Adds a listener to the pool
	 * 
	 * @param listener
	 * @return {@link Integer} listener Id
	 */
	public int addListener(Listener listener){
		int listenerId = listener.hashCode();
		listeners.put(listenerId, listener);
		return listenerId;
	}
	
	/**
	 * Removes a {@link Listener} from the pool
	 * 
	 * @param listenerId
	 * @return {@link Listener}
	 */
	public Listener removeListener(int listenerId){
		return this.listeners.remove(listenerId);
	}
	
	/**
	 * Removes a {@link Listener} from the pool
	 * 
	 * @param listener
	 * @return {@link Listener}
	 */
	public Listener removeListener(Listener listener){
		int listenerId = listener.hashCode();
		return this.removeListener(listenerId);
	}
	
	/**
	 * Checks if it is in listening mode
	 * 
	 * @return {@link Boolean}
	 */
	public boolean isListening(){
		return this.isListening;
	}
	
	/**
	 * Start listening for new {@link Events}
	 * 
	 * @param options
	 */
	public void startListening(MessageOptions options){
		if(this.listeners.isEmpty())
			return;
		
		MessageOptions ops = this.defaultMessageOptions;
		if(options != null)
			ops = options;
		
		this.listenerManager = new ListenerManager(ops);
		this.isListening = true;
		this.listenerManager.start();
	}
	
	/**
	 * Starts listening for new {@link Events}
	 */
	public void startListening(){
		MessageOptions options = new MessageOptions();
		this.startListening(options);
	}
	
	/**
	 * Stops listening for new {@link Events}
	 */
	public void stopListening(){
		this.isListening = false;
		try {
			this.listenerManager.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A Collection of all {@link Listener} in this {@link Subscription}
	 * 
	 * @return Collection
	 */
	public Collection<Listener> getListener(){
		return this.listeners.values();
	}
	
	/**
	 * Describes a Collection of {@link Subscription}
	 * The subscriptions resource allows you to create new subscriptions
	 * 
	 * @since 1.0
	 * @author Jorge Gonzalez
	 *
	 */
	public static class Subscriptions extends Resource implements Map<String, Subscription>{
		private Map<String, Subscription> subscriptionCollection;
		
		/**
		 * 
		 */
		public Subscriptions() {
			super();
		}

		/**
		 * @param schema
		 */
		public Subscriptions(ApiSchemaModel schema) {
			super(schema);
		}

		/**
		 * @param model
		 * @param schema
		 */
		public Subscriptions(ResourceModel model, ApiSchemaModel schema) {
			super(model, schema);
		}
		
		@Override
		protected void initialize() {
			super.initialize();
			subscriptionCollection = model.getMapCollection("resources", Subscription.class, schema);
		}

		@Override
		public String getResourceName() {
			return this.getClass().getSimpleName().toLowerCase();
		}
		
		@Override
		protected void addModel(Map<String, Object> rawModel) {
			Subscription subscription = new Subscription(new ResourceModel(rawModel), this.schema);
			this.addSubscription(subscription);
		}
		
		@Override
		protected void updateModel(Map<String, Object> rawModel){
			this.model.setProperty("resources", rawModel);
			this.initialize();
		}
		
		public Subscription getSubscription(String name){
			return subscriptionCollection.get(name);
		}
		
		public void addSubscription(Subscription subscription){
			subscriptionCollection.put(subscription.getName(), subscription);
		}
		
		/**
		 * Creates a new {@link Subscription}
		 * 
		 * @param name
		 * @param channels
		 * @throws ResponseException
		 * @throws IOException
		 */
		public void createSubscription(String name, List<String> channels) throws ResponseException, IOException{
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("name", name);
			content.put("channels", channels);
			Map<String, String> headers = new HashMap<String, String>();
			// FIXME: quick fix... may be is better to use 'Subscription.class.getSimpleName().toLowerCase()' ?
			Subscription subscription = new Subscription();
			headers.put("Accept", this.schema.getMediaType(subscription.getResourceName()));
			headers.put("Content-Type", this.schema.getMediaType(subscription.getResourceName()));
			super.post(content, headers);
		}
		
		@Override
		public void get() throws ResponseException, IOException{
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Authorization", "Capability " + capability.getCapabilityFor("all"));
			super.get(null, headers);
		}

		@Override
		public void clear() {
			subscriptionCollection.clear();
		}
		
		@Override
		public int size() {
			return subscriptionCollection.size();
		}

		@Override
		public boolean containsKey(Object key) {
			return subscriptionCollection.containsKey(key);
		}

		@Override
		public boolean containsValue(Object value) {
			return subscriptionCollection.containsValue(value);
		}

		@Override
		public Set<java.util.Map.Entry<String, Subscription>> entrySet() {
			return subscriptionCollection.entrySet();
		}

		@Override
		public Subscription get(Object key) {
			return subscriptionCollection.get(key);
		}

		@Override
		public boolean isEmpty() {
			return subscriptionCollection.isEmpty();
		}

		@Override
		public Set<String> keySet() {
			return subscriptionCollection.keySet();
		}

		@Override
		public Subscription put(String key, Subscription value) {
			return subscriptionCollection.put(key, value);
		}

		@Override
		public void putAll(Map<? extends String, ? extends Subscription> m) {
			subscriptionCollection.putAll(m);
		}

		@Override
		public Subscription remove(Object key) {
			return subscriptionCollection.remove(key);
		}

		@Override
		public Collection<Subscription> values() {
			return subscriptionCollection.values();
		}
	}
	
	/**
	 * Listener overlord does all the background polling of messages
	 * 
	 * @since 1.0
	 * @author Jorge Gonzalez
	 *
	 */
	private class ListenerManager extends Thread {
		private MessageOptions options;
		
		/**
		 * 
		 * @param options
		 */
		public ListenerManager(MessageOptions options){
			this.options = options;
		}
		
		@Override
		public void run() {
			while(isListening){
				try{
					try {
						longPoll(this.options, isListening);
					} catch (ResponseException e) {
						System.out.println(e.getMessage());
						e.getResponse().ignore();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}catch (IOException e) {
					e.printStackTrace();
					stopListening();
				}
			}
		}
	}

}
