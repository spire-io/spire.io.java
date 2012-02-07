/**
 * 
 */
package io.spire.api;

import java.util.Map;

import io.spire.api.Api.APIDescriptionModel.APIResourceModel;
import io.spire.api.Api.APIDescriptionModel.APISchemaModel;

/**
 * @author jorge
 *
 */
public class Account extends Resource {

	private Origin origin;
	private BillingSubscription billing;
	
	/**
	 * 
	 * @param schema
	 */
	public Account(APISchemaModel schema) {
		super(schema);
	}
	
	/**
	 * @param model
	 * @param schema
	 */
	public Account(ResourceModel model, APISchemaModel schema) {
		super(model, schema);
	}
	
	@Override
	protected void initialize(){
		ResourceModel originModel = getResourceModel("origin");
		origin = new Origin(originModel, schema);
		
		ResourceModel billingModel = getResourceModel("billing");
		billing = new BillingSubscription(billingModel, schema);
	}
	
	@Override
	public String getResourceName(){
		return this.getClass().getSimpleName().toLowerCase();
	}
	
	@Override
	protected void addModel(Map<String, Object> rawModel) {
		
	}
	
	public static class Origin extends Resource{
		/**
		 * 
		 * @param schema
		 */
		public Origin(APISchemaModel schema) {
			super(schema);
		}
		
		/**
		 * @param model
		 * @param schema
		 */
		public Origin(ResourceModel model, APISchemaModel schema) {
			super(model, schema);
		}
		
		@Override
		protected void initialize() {
			
		}

		@Override
		public String getResourceName() {
			return this.getClass().getSimpleName().toLowerCase();
		}
		
		@Override
		protected void addModel(Map<String, Object> rawModel) {
			
		}

		public String getHost(){
			return this.model.getProperty("host", String.class);
		}
		
		public void setHost(String host){
			this.model.setProperty("host", host);
		}
		
		public String getScheme(){
			return this.model.getProperty("scheme", String.class);
		}
		
		public void setScheme(String scheme){
			this.model.setProperty("scheme", scheme);
		}
		
		public Integer getPort(){
			return this.model.getProperty("port", Integer.class);
		}
		
		public void setPort(Integer port){
			this.model.setProperty("port", port);
		}
	}
		
	public String getEmail(){
		return this.model.getProperty("email", String.class);
	}
	
	public void setEmail(String email){
		this.model.setProperty("email", email);
	}
	
	public String getCompany(){
		return this.model.getProperty("company", String.class);
	}
	
	public void setCompany(String companyName){
		this.model.setProperty("company", companyName);
	}
	
	public Origin getOrigin(){
		return origin;
	}
	
	public void setOrigin(Origin origin){
		this.origin = origin;
	}
	
	public String getPaymentToken(){
		return this.model.getProperty("paymentToken", String.class);
	}
	
	public void setpaymentToken(String token){
		this.model.setProperty("paymentToken", token);
	}
	
	public String getCreditCard(){
		return this.model.getProperty("cc", String.class);
	}
	
	public void setCreditCard(String cc){
		this.model.setProperty("cc", cc);
	}
	
	public BillingSubscription getBilling(){
		return billing;
	}
	
	public void setBilling(BillingSubscription billing){
		this.billing = billing;
	}

}
