/**
 * 
 */
package io.spire.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;

/**
 * @author Jorge Gonzalez
 *
 */
public class Billing extends Resource {

	private List<Plan> plans;
	
	/**
	 * 
	 */
	public Billing() {
		super();
	}

	/**
	 * @param schema
	 */
	public Billing(APISchemaModel schema) {
		super(schema);
	}

	/**
	 * @param model
	 * @param schema
	 */
	public Billing(ResourceModel model, APISchemaModel schema) {
		super(model, schema);
	}

	/* (non-Javadoc)
	 * @see io.spire.api.Resource#initialize()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void initialize() {
		plans = new Plans();
		List<Map<String, Object>> rawPlans = model.getProperty("plans", List.class);
		for (Map<String, Object> rawPlan : rawPlans) {
			ResourceModel rawModelPlan = new ResourceModel(rawPlan);
			Plan p = new Plan(rawModelPlan, this.schema);
			plans.add(p);
		}
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
	
	public List<Plan> getPlans(){
		return plans;
	}
	
	public static class Plan extends Resource{
		private Features features;
		
		/**
		 * 
		 */
		public Plan() {
			super();
		}

		/**
		 * @param schema
		 */
		public Plan(APISchemaModel schema) {
			super(schema);
		}

		/**
		 * @param model
		 * @param schema
		 */
		public Plan(ResourceModel model, APISchemaModel schema) {
			super(model, schema);
		}
		
		public String getId(){
			return this.model.getProperty("id", String.class);
		}
		
		public String getName(){
			return this.model.getProperty("name", String.class);
		}
		
		public String getDescription(){
			return this.model.getProperty("description", String.class);
		}
		
		public Double getPrice(){
			return Double.parseDouble(this.model.getProperty("price", String.class));
		}
		
		public Features getFeatures(){
			return features;
		}
		
		@Override
		protected void initialize() {
			ResourceModel featuresModel = getResourceModel("features");
			features = new Features(featuresModel, schema);
		}

		@Override
		public String getResourceName() {
			return this.getClass().getSimpleName().toLowerCase();
		}
		
		@Override
		protected void addModel(Map<String, Object> rawModel) {
			
		}
		
		public static class Features extends Resource{
			private Queue queue;
			/**
			 * 
			 */
			public Features() {
				super();
			}

			/**
			 * @param schema
			 */
			public Features(APISchemaModel schema) {
				super(schema);
			}

			/**
			 * @param model
			 * @param schema
			 */
			public Features(ResourceModel model, APISchemaModel schema) {
				super(model, schema);
			}
			
			@Override
			protected void initialize() {
				ResourceModel queueModel = getResourceModel("queue");
				queue = new Queue(queueModel, schema);
			}

			@Override
			public String getResourceName() {
				return this.getClass().getSimpleName().toLowerCase();
			}
			
			@Override
			protected void addModel(Map<String, Object> rawModel) {
				
			}
			
			public Integer getRPS(){
				return this.model.getProperty("rps", Integer.class);
			}
			
			public Queue getQueue(){
				return queue;
			}
			
			public static class Queue extends Resource{
				/**
				 * 
				 */
				public Queue() {
					super();
				}

				/**
				 * @param schema
				 */
				public Queue(APISchemaModel schema) {
					super(schema);
				}

				/**
				 * @param model
				 * @param schema
				 */
				public Queue(ResourceModel model, APISchemaModel schema) {
					super(model, schema);
				}
				
				public Integer getLimit(){
					return this.model.getProperty("limit", Integer.class);
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
			}
		}
	}
	
	public static class Plans extends ArrayList<Plan>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 2389002871849806024L;
	}

}
