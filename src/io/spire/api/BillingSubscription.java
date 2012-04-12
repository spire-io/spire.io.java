/**
 * 
 */
package io.spire.api;

import java.util.Map;

import io.spire.api.Api.ApiDescriptionModel.ApiSchemaModel;

/**
 * Describes the plan the Account is subscribe to
 *  
 * @since 1.0
 * @author Jorge Gonzalez
 * @deprecated v1.1.4
 *
 */
public class BillingSubscription extends Resource {

	private BillingInvoices invoices;
	/**
	 * 
	 */
	public BillingSubscription() {
		super();
	}

	/**
	 * @param schema
	 */
	public BillingSubscription(ApiSchemaModel schema) {
		super(schema);
	}

	/**
	 * @param model
	 * @param schema
	 */
	public BillingSubscription(ResourceModel model, ApiSchemaModel schema) {
		super(model, schema);
	}
	
	@Override
	protected void initialize(){
		super.initialize();
		ResourceModel invoicesModel = getResourceModel("invoices");
		invoices = new BillingInvoices(invoicesModel, schema);
	}
	
	@Override
	public String getResourceName(){
		return "billing_subscription";
	}
	
	@Override
	protected void addModel(Map<String, Object> rawModel) {
		
	}
	
	public String getId(){
		return this.model.getProperty("id", String.class);
	}
	
	public void setId(String id){
		this.model.setProperty("id", id);
	}
	
	public BillingInvoices getInvoices(){
		return invoices;
	}
	
	/**
	 * A Collection of previous or upcoming invoices
	 * 
	 * @since 1.0
	 * @author Jorge Gonzalez
	 *
	 */
	public static class BillingInvoices extends Resource{
		
		/**
		 * 
		 */
		private BillingInvoice upcoming;

		/**
		 * 
		 */
		public BillingInvoices() {
			super();
		}

		/**
		 * @param schema
		 */
		public BillingInvoices(ApiSchemaModel schema) {
			super(schema);
		}

		/**
		 * @param model
		 * @param schema
		 */
		public BillingInvoices(ResourceModel model, ApiSchemaModel schema) {
			super(model, schema);
		}
		
		@Override
		protected void initialize() {
			super.initialize();
			ResourceModel upcomingInvoiceModel = getResourceModel("upcoming");
			upcoming = new BillingInvoice(upcomingInvoiceModel, schema);
		}

		@Override
		public String getResourceName() {
			return "billing_invoices";
		}
		
		@Override
		protected void addModel(Map<String, Object> rawModel) {
			
		}
		
		/**
		 * Gets the upcoming Invoice
		 * 
		 * @return {@link BillingInvoice}
		 */
		public BillingInvoice getUpcomingInvoice(){
			return upcoming;
		}
	}
}
