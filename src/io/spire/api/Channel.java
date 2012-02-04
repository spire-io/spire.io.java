/**
 * 
 */
package io.spire.api;

import java.util.Map;
import java.util.Set;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;
import io.spire.api.Subscription.Subscriptions;

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
	
	public Set<String> getSubcriptions(){
		return subscriptionCollection.keySet();
	}
	
	public Subscription getSubcription(String name){
		return subscriptionCollection.get(name);
	}
	
	public static class Channels extends Resource{
		private Map<String, Channel> channelCollection;
		
		/**
		 * 
		 */
		public Channels() {
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
		public String getResourceName() {
			return this.getClass().getSimpleName().toLowerCase();
		}
		
		public Channel getChannel(String name){
			return channelCollection.get(name);
		}
		
		public int count(){
			return channelCollection.size();
		}
		
	}
}
