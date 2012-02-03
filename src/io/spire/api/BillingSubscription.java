/**
 * 
 */
package io.spire.api;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;
import io.spire.api.Resource.ResourceModel;

/**
 * @author Jorge Gonzalez
 *
 */
public class BillingSubscription extends Resource {

	private BillingInvoices invoices;
	/**
	 * 
	 */
	public BillingSubscription() {
	}

	/**
	 * @param schema
	 */
	public BillingSubscription(APISchemaModel schema) {
		super(schema);
	}

	/**
	 * @param model
	 * @param schema
	 */
	public BillingSubscription(ResourceModel model, APISchemaModel schema) {
		super(model, schema);
	}
	
	@Override
	protected void initialize(){
		ResourceModel invoicesModel = getResourceModel("invoices");
		invoices = new BillingInvoices(invoicesModel, schema);
	}
	
	@Override
	public String getResourceName(){
		return "billing_subscription";
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
	
	public static class BillingInvoices extends Resource{
		
		private BillingInvoice upcoming;

		/**
		 * 
		 */
		public BillingInvoices() {
		}

		/**
		 * @param schema
		 */
		public BillingInvoices(APISchemaModel schema) {
			super(schema);
		}

		/**
		 * @param model
		 * @param schema
		 */
		public BillingInvoices(ResourceModel model, APISchemaModel schema) {
			super(model, schema);
		}
		
		@Override
		protected void initialize() {
			ResourceModel upcomingInvoiceModel = getResourceModel("upcoming");
			upcoming = new BillingInvoice(upcomingInvoiceModel, schema);
		}

		@Override
		public String getResourceName() {
			return "billing_invoices";
		}
		
		public BillingInvoice getUpcomingInvoice(){
			return upcoming;
		}
	}
}
