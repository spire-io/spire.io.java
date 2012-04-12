/**
 * 
 */
package io.spire.api;

import java.util.Map;

import io.spire.api.Api.ApiDescriptionModel.ApiSchemaModel;

/**
 * An account resource allows you to get information about your account, 
 * update it, or close it entirely.
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public class Account extends Resource {

	private Origin origin;
	@SuppressWarnings("deprecation")
	private BillingSubscription billing;
	
	/**
	 * 
	 */
	public Account() {
		super();
	}
	
	/**
	 * 
	 * @param schema
	 */
	public Account(ApiSchemaModel schema) {
		super(schema);
	}
	
	/**
	 * 
	 * @param model
	 * @param schema
	 */
	public Account(ResourceModel model, ApiSchemaModel schema) {
		super(model, schema);
	}
	
	@Override
	protected void initialize(){
		super.initialize();
		
		ResourceModel originModel = getResourceModel("origin");
		origin = new Origin(originModel, schema);
	}
	
	@Override
	public String getResourceName(){
		return this.getClass().getSimpleName().toLowerCase();
	}
	
	@Override
	protected void addModel(Map<String, Object> rawModel) {
		
	}
	
	/**
	 * Gets Account secret key
	 * 
	 * @return {@link String}
	 */
	public String getSecret(){
		return this.model.getProperty("secret", String.class);
	}
	
	/**
	 * Sets Account secret key
	 * 
	 * @param secret
	 */
	public void setSecret(String secret){
		this.model.setProperty("secret", secret);
	}
	
	/**
	 * CORS data
	 * 
	 * @since 1.0
	 * @author Jorge Gonzalez
	 *
	 */
	public static class Origin extends Resource{
		/**
		 * 
		 * @param schema
		 */
		public Origin(ApiSchemaModel schema) {
			super(schema);
		}
		
		/**
		 * @param model
		 * @param schema
		 */
		public Origin(ResourceModel model, ApiSchemaModel schema) {
			super(model, schema);
		}

		@Override
		public String getResourceName() {
			return this.getClass().getSimpleName().toLowerCase();
		}
		
		@Override
		protected void addModel(Map<String, Object> rawModel) {
			
		}

		/**
		 * Gets the Origin host
		 * 
		 * @return {@link String}
		 */
		public String getHost(){
			return this.model.getProperty("host", String.class);
		}
		
		/**
		 * Sets the Origin host
		 * 
		 * @param host
		 */
		public void setHost(String host){
			this.model.setProperty("host", host);
		}
		
		/**
		 * Gets the Origin scheme
		 * 
		 * @return {@link String}
		 */
		public String getScheme(){
			return this.model.getProperty("scheme", String.class);
		}
		
		/**
		 * Sets the Origin scheme
		 * 
		 * @param scheme
		 */
		public void setScheme(String scheme){
			this.model.setProperty("scheme", scheme);
		}
		
		/**
		 * Gets the Origin port
		 * 
		 * @return Integer
		 */
		public Integer getPort(){
			return this.model.getProperty("port", Integer.class);
		}
		
		/**
		 * Sets the Origin port
		 * 
		 * @param port
		 */
		public void setPort(Integer port){
			this.model.setProperty("port", port);
		}
	}
	
	/**
	 * Account email
	 * 
	 * @return {@link String}
	 */
	public String getEmail(){
		return this.model.getProperty("email", String.class);
	}
	
	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email){
		this.model.setProperty("email", email);
	}
	
	/**
	 * Account company
	 * 
	 * @return String
	 */
	public String getCompany(){
		return this.model.getProperty("company", String.class);
	}
	
	/**
	 * 
	 * @param companyName
	 */
	public void setCompany(String companyName){
		this.model.setProperty("company", companyName);
	}
	
	/**
	 * Account Origin
	 * 
	 * @return Origin
	 */
	public Origin getOrigin(){
		return origin;
	}
	
	/**
	 * 
	 * @param origin
	 */
	public void setOrigin(Origin origin){
		this.origin = origin;
	}
	
	/**
	 * Recurrent payment token
	 * 
	 * @return String
	 */
	public String getPaymentToken(){
		return this.model.getProperty("paymentToken", String.class);
	}
	
	/**
	 * 
	 * @param token
	 */
	public void setpaymentToken(String token){
		this.model.setProperty("paymentToken", token);
	}
	
	/**
	 * Last 4 digit of the credit card used for billing
	 * 
	 * @return String
	 */
	public String getCreditCard(){
		return this.model.getProperty("cc", String.class);
	}
	
	/**
	 * 
	 * @param cc
	 */
	public void setCreditCard(String cc){
		this.model.setProperty("cc", cc);
	}
	
	/**
	 * Gets Account billing subscription
	 * 
	 * @return BillingSubscription
	 * @deprecated v1.1.4
	 */
	public BillingSubscription getBilling(){
		return billing;
	}
	
	/**
	 * Sets Account billing subscription
	 * 
	 * @param billing
	 * @deprecated v1.1.4
	 */
	public void setBilling(BillingSubscription billing){
		this.billing = billing;
	}

}
