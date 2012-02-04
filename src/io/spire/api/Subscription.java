/**
 * 
 */
package io.spire.api;

import java.util.Map;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;

/**
 * @author Jorge Gonzalez
 *
 */
public class Subscription extends Resource {

	/**
	 * 
	 */
	public Subscription() {
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
	@Override
	protected void initialize() {

	}

	/* (non-Javadoc)
	 * @see io.spire.api.Resource#getResourceName()
	 */
	@Override
	public String getResourceName() {
		return this.getClass().getSimpleName().toLowerCase();
	}
	
	public static class Subscriptions extends Resource{
		private Map<String, Subscription> subscriptionCollection;
		
		/**
		 * 
		 */
		public Subscriptions() {
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
		
		public Subscription getSubscription(String name){
			return subscriptionCollection.get(name);
		}
	}

}
