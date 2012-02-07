/**
 * 
 */
package io.spire.api;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;
import io.spire.api.Channel.Channels;
import io.spire.api.Resource.ResourceModel;
import io.spire.api.Subscription.Subscriptions;
import io.spire.request.ResponseException;

/**
 * @author jorge
 *
 */
public class Session extends Resource {

	protected Account account;
	protected Channels channels;
	protected Subscriptions subscriptions;
	
	/**
	 * 
	 */
	public Session(ResourceModel model, APISchemaModel schema) {
		super(model, schema);
	}
	
	@Override
	protected void initialize(){
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
	
	public Account getAccount(){
		return account;
	}
	
	public Channels getChannels(){
		return channels;
	}
	
	public Channel createChannel(String name) throws ResponseException, IOException{
		channels.createChannel(name);
		return channels.getChannel(name);
	}
	
	public Subscription createSubscription(String name, String ...channels) throws ResponseException, IOException{
		List<String> channelList = new ArrayList<String>();
		for (String channel : channels) {
			channelList.add(channel);
		}
		
		return createSubscription(name, channelList);
	}
	
	public Subscription createSubscription(String name, List<String> channels) throws ResponseException, IOException{
		List<String> channelUrls = new ArrayList<String>();
		for (String channelName : channels) {
			channelUrls.add(this.channels.getChannel(channelName).getUrl());
		}
		subscriptions.createSubscription(name, channelUrls);
		return subscriptions.getSubscription(name); 
	}
	
	public Subscriptions getSubscriptions(){
		return this.subscriptions;
	}
	
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
				}else{	// just return if whatever other error
					throw new ResponseException(e.getResponse());
				}
			}catch(Throwable t){
				throw new IOException(t);
			}
			channelUrls.add(channel.getUrl());
		}
		subscriptions.createSubscription(name, channelUrls);
		return subscriptions.getSubscription(name);
	}

}
