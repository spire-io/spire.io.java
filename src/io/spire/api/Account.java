/**
 * 
 */
package io.spire.api;

import java.util.Map;

import io.spire.api.Api.APIDescriptionModel.APIResourceModel;
import io.spire.api.Api.APIDescriptionModel.APISchemaModel;
import io.spire.api.Resource.ResourceModel;

/**
 * @author jorge
 *
 */
public class Account extends Resource {

	private Billing billing;
	
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
		ResourceModel billingModel = getResourceModel("billing");
		billing = new Billing(billingModel, schema);
	}
	
	@Override
	public String getResourceName(){
		return this.getClass().getSimpleName().toLowerCase();
	}
	
	public static class AccountOrigin extends APIResourceModel{
		private static final long serialVersionUID = 8747002765351883148L;

		public String getHost(){
			return this.getProperty("host", String.class);
		}
		
		public void setHost(String host){
			this.setProperty("host", host);
		}
		
		public String getScheme(){
			return this.getProperty("scheme", String.class);
		}
		
		public void setScheme(String scheme){
			this.setProperty("scheme", scheme);
		}
		
		public Integer getPort(){
			return this.getProperty("port", Integer.class);
		}
		
		public void setPort(Integer port){
			this.setProperty("port", port);
		}
	}
		
	public String getEmail(){
		return this.model.getProperty("email", String.class);
	}
	
	public void setEmail(String email){
		this.model.setProperty("email", email);
	}
	
	public String getKey(){
		return this.model.getProperty("key", String.class);
	}
	
	public void setKey(String key){
		this.model.setProperty("key", key);
	}
	
	public String getName(){
		return this.model.getProperty("name", String.class);
	}
	
	public void setName(String name){
		this.model.setProperty("name", name);
	}
	
	public String getCompany(){
		return this.model.getProperty("company", String.class);
	}
	
	public void setCompany(String companyName){
		this.model.setProperty("company", companyName);
	}
	
	public AccountOrigin getOrigin(){
		return this.model.getProperty("origin", AccountOrigin.class);
	}
	
	public void setOrigin(AccountOrigin origin){
		this.model.setProperty("origin", origin);
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
	
	public Billing getBilling(){
		return billing;
	}
	
	public void setBilling(Billing billing){
		this.billing = billing;
	}

}
