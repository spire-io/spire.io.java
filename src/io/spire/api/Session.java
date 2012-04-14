/**
 * 
 */
package io.spire.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.spire.api.Api.ApiDescriptionModel.ApiSchemaModel;
import io.spire.api.Channel.Channels;
import io.spire.api.Subscription.Subscriptions;
import io.spire.request.ResponseException;

/**
 * A session object gives you access to privileged resources. 
 * The resources attribute will provide a list of them, by name. 
 * For example, you'll have a resource called channels, which will give you 
 * a list of channel resources.
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public class Session extends Resource {

	protected Account account;
	protected Channels channels;
	protected Subscriptions subscriptions;
	
	/**
	 * 
	 */
	public Session(ResourceModel model, ApiSchemaModel schema) {
		super(model, schema);
	}
	
	@Override
	protected void initialize(){
		super.initialize();
		ResourceModel resourceModel = getResourceModel("account");
		account = new Account(resourceModel, this.schema);
		
		resourceModel = getResourceModel("channels");
		channels = new Channels(resourceModel, this.schema);
		
		resourceModel = getResourceModel("subscriptions");
		subscriptions = new Subscriptions(resourceModel, this.schema);
	}
	
	@Override
	protected ResourceModel getResourceModel(String resourceName){
		@SuppressWarnings("unchecked")
		Map<String, Object> resources = model.getProperty("resources", Map.class);
		@SuppressWarnings("unchecked")
		Map<String, Object> rawModel = (Map<String, Object>)resources.get(resourceName);
		return new ResourceModel(rawModel);
	}
	
	@Override
	public String getResourceName(){
		return this.getClass().getSimpleName().toLowerCase();
	}
	
	@Override
	protected void addModel(Map<String, Object> rawModel) {
		
	}
	
	/**
	 * The Account this session belongs to
	 * 
	 * @return {@link Account}
	 */
	public Account getAccount(){
		return account;
	}
	
	/**
	 * {@link Channels} that belongs to this {@link Session}
	 * 
	 * @return {@link Channels}
	 */
	public Channels getChannels(){
		return channels;
	}
	
	/**
	 * GET {@link Channels} that belong to this Session
	 * 
	 * @return {@link Channels}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Channels channels() throws ResponseException, IOException{
		this.channels.get();
		return this.channels;
	}
	
	/**
	 * Creates a new Channel resource
	 * 
	 * @param name
	 * @return {@link Channel}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Channel createChannel(String name) throws ResponseException, IOException{
		channels.createChannel(name);
		return channels.getChannel(name);
	}
	
	/**
	 * Creates a new {@link Subscription}
	 * 
	 * @param name
	 * @param channels
	 * @return {@link Subscription}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Subscription createSubscription(String name, String ...channels) throws ResponseException, IOException{
		List<String> channelList = new ArrayList<String>();
		for (String channel : channels) {
			channelList.add(channel);
		}
		
		return createSubscription(name, channelList, null);
	}
	
	/**
	 * Creates a new {@link Subscription} for all existing channels
	 * 
	 * @param name
	 * @param channels
	 * @return {@link Subscription}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Subscription createSubscription(String name, List<String> channels) throws ResponseException, IOException{
		return this.createSubscription(name, channels, null);
	}
	
	/**
	 * Creates a new {@link Subscription} for all existing channels
	 * 
	 * @param name
	 * @param channels
	 * @param expiration
	 * @return {@link Subscription}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Subscription createSubscription(String name, List<String> channels, Integer expiration) throws ResponseException, IOException{
		List<String> channelUrls = new ArrayList<String>();
		for (String channelName : channels) {
			channelUrls.add(this.channels.getChannel(channelName).getUrl());
		}
		subscriptions.createSubscription(name, channelUrls, expiration);
		return subscriptions.getSubscription(name); 
	}
	
	/**
	 * {@link Subscriptions} that belong to this {@link Session}
	 * 
	 * @return {@link Subscriptions}
	 */
	public Subscriptions getSubscriptions(){
		return this.subscriptions;
	}
	
	/**
	 * GET {@link Subscriptions} that belong to this Session
	 * 
	 * @return {@link Subscriptions}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Subscriptions subscriptions() throws ResponseException, IOException{
		this.subscriptions.get();
		return this.subscriptions;
	}
	
	/**
	 * Creates a new {@link Subscription} for the specified {@link Channels} names
	 * 
	 * If any of the {@link Channel} does not exist, it gets created before trying to
	 * subscribe to it.
	 * 
	 * If a {@link Subscription} with this 'name' already exists, then returns 
	 * the existing {@link Subscription}
	 * 
	 * @param name
	 * @param channels
	 * @return {@link Subscription}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Subscription subscribe(String name, String ...channels) throws ResponseException, IOException{
		List<String> channelUrls = new ArrayList<String>();
		for (String channelName : channels) {
			Channel channel = null;
			try{
				// create channel
				channel = this.createChannel(channelName);
			}catch(ResponseException e){
				// if channel already exists, just get it.
				if(e.getResponse().getStatusCode() == 409){
					this.channels.get();
					channel = this.channels.getChannel(channelName);
				}else{	// just return if whatever other error
					throw new ResponseException(e.getResponse());
				}
			}catch(Throwable t){
				throw new IOException(t);
			}
			channelUrls.add(channel.getUrl());
		}
		
		try{
			subscriptions.createSubscription(name, channelUrls);
		}catch(ResponseException e){
			// if channel already exists, just get it.
			if(e.getResponse().getStatusCode() == 409){
				this.subscriptions.get();
			}else{	// just return if whatever other error
				throw new ResponseException(e.getResponse());
			}
		}catch(Throwable t){
			throw new IOException(t);
		}
		
		return subscriptions.getSubscription(name);
	}

}
