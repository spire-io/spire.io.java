/**
 * 
 */
package io.spire.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;
import io.spire.api.Resource.ResourceModel;
import io.spire.request.ResponseException;

/**
 * @author Jorge Gonzalez
 *
 */
public class Subscription extends Resource {
	
	private List<String> channels;

	/**
	 * 
	 */
	public Subscription() {
		super();
	}

	/**
	 * @param schema
	 */
	public Subscription(APISchemaModel schema) {
		super(schema);
	}

	/**
	 * @param model
	 * @param schema
	 */
	public Subscription(ResourceModel model, APISchemaModel schema) {
		super(model, schema);
	}

	/* (non-Javadoc)
	 * @see io.spire.api.Resource#initialize()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void initialize() {
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
	
	public List<String> getChannels(){
		return channels;
	}
	
	public static class Subscriptions extends Resource{
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
		public Subscriptions(APISchemaModel schema) {
			super(schema);
		}

		/**
		 * @param model
		 * @param schema
		 */
		public Subscriptions(ResourceModel model, APISchemaModel schema) {
			super(model, schema);
		}
		
		@Override
		protected void initialize() {
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
		
		public Subscription getSubscription(String name){
			return subscriptionCollection.get(name);
		}
		
		public void addSubscription(Subscription subscription){
			subscriptionCollection.put(subscription.getName(), subscription);
		}
		
		public int size(){
			return subscriptionCollection.size();
		}
		
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
	}

}
