/**
 * 
 */
package io.spire.api;

import io.spire.api.Api.APIDescriptionModel.APISchemaModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jorge Gonzalez
 *
 */
public class Events extends Resource {

	private List<Message> messages;
	
	/**
	 * 
	 */
	public Events() {
	}

	/**
	 * @param schema
	 */
	public Events(APISchemaModel schema) {
		super(schema);
	}

	/**
	 * @param model
	 * @param schema
	 */
	public Events(ResourceModel model, APISchemaModel schema) {
		super(model, schema);
	}

	/* (non-Javadoc)
	 * @see io.spire.api.Resource#initialize()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void initialize() {
		messages = new ArrayList<Message>();
		List<Map<String, Object>> rawMessages = this.model.getProperty("messages", List.class);
		for (Map<String, Object> rawMessage : rawMessages) {
			Message message = new Message(new ResourceModel(rawMessage), schema);
			messages.add(message);
		}
	}

	/* (non-Javadoc)
	 * @see io.spire.api.Resource#addModel(java.util.Map)
	 */
	@Override
	protected void addModel(Map<String, Object> rawModel) {
	}

	/* (non-Javadoc)
	 * @see io.spire.api.Resource#getResourceName()
	 */
	@Override
	public String getResourceName() {
		return this.getClass().getSimpleName().toLowerCase();
	}
	
	@Override
	protected void updateModel(Map<String, Object> rawModel){
		this.model = new ResourceModel(rawModel);
		this.initialize();
	}
	
	public List<Message> getMessages(){
		return messages;
	}
}
