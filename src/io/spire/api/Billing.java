/**
 * 
 */
package io.spire.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;
import io.spire.api.Resource.ResourceModel;

/**
 * Spire Billing plans
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public class Billing extends Resource {

	private List<Plan> plans;
	
	/**
	 * @see Resource#Resource()
	 */
	public Billing() {
		super();
	}

	/**
	 * @see Resource#Resource(APISchemaModel)
	 * 
	 * @param schema
	 */
	public Billing(APISchemaModel schema) {
		super(schema);
	}

	/**
	 * @see Resource#Resource(ResourceModel, APISchemaModel)
	 * 
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
	
	/**
	 * Gets a list of {@link Plan}
	 * 
	 * @return List<Plans>
	 */
	public List<Plan> getPlans(){
		return plans;
	}
	
	/**
	 * 
	 * @since 1.0
	 * @author Jorge Gonzalez
	 *
	 */
	public static class Plan extends Resource{
		private Features features;
		
		/**
		 * @see Resource#Resource()
		 */
		public Plan() {
			super();
		}

		/**
		 * @see Resource#Resource(APISchemaModel)
		 * 
		 * @param schema
		 */
		public Plan(APISchemaModel schema) {
			super(schema);
		}

		/**
		 * @see Resource#Resource(ResourceModel, APISchemaModel)
		 * 
		 * @param model
		 * @param schema
		 */
		public Plan(ResourceModel model, APISchemaModel schema) {
			super(model, schema);
		}
		
		/**
		 * 
		 * @return String
		 */
		public String getId(){
			return this.model.getProperty("id", String.class);
		}
		
		@Override
		public String getName(){
			return this.model.getProperty("name", String.class);
		}
		
		/**
		 * 
		 * @return String
		 */
		public String getDescription(){
			return this.model.getProperty("description", String.class);
		}
		
		/**
		 * 
		 * @return Double
		 */
		public Double getPrice(){
			return Double.parseDouble(this.model.getProperty("price", String.class));
		}
		
		/**
		 * 
		 * @return Features
		 */
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
		
		/**
		 * Billing plan features
		 * 
		 * @since 1.0
		 * @author Jorge Gonzalez
		 *
		 */
		public static class Features extends Resource{
			private Queue queue;
			
			/**
			 * @see Resource#Resource()
			 */
			public Features() {
				super();
			}

			/**
			 * @see Resource#Resource(APISchemaModel)
			 * 
			 * @param schema
			 */
			public Features(APISchemaModel schema) {
				super(schema);
			}

			/**
			 * @see Resource#Resource(ResourceModel, APISchemaModel)
			 * 
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
			
			/**
			 * 
			 * @return Integer
			 */
			public Integer getRPS(){
				return this.model.getProperty("rps", Integer.class);
			}
			
			/**
			 * 
			 * @return Queue
			 */
			public Queue getQueue(){
				return queue;
			}
			
			/**
			 * 
			 * @since 1.0
			 * @author Jorge Gonzalez
			 *
			 */
			public static class Queue extends Resource{
				/**
				 * @see Resource#Resource()
				 */
				public Queue() {
					super();
				}

				/**
				 * @see Resource#Resource(APISchemaModel)
				 * 
				 * @param schema
				 */
				public Queue(APISchemaModel schema) {
					super(schema);
				}

				/**
				 * @see Resource#Resource(ResourceModel, APISchemaModel)
				 * 
				 * @param model
				 * @param schema
				 */
				public Queue(ResourceModel model, APISchemaModel schema) {
					super(model, schema);
				}
				
				/**
				 * 
				 * @return Integer
				 */
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
	
	/**
	 * 
	 * @since 1.0
	 * @author Jorge Gonzalez
	 *
	 */
	public static class Plans extends ArrayList<Plan>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 2389002871849806024L;
	}

}
