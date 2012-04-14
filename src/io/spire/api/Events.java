/**
 * 
 */
package io.spire.api;

import io.spire.api.Api.ApiDescriptionModel.ApiSchemaModel;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Describes events generated in Spire {@link Channel}
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public class Events extends Resource {

	public enum EventType{
		Message, Join, Part
	}
	
	private List<Message> messages;
	private List<Join> joins;
	private List<Part> parts;
	
	/**
	 * 
	 */
	public Events() {
	}

	/**
	 * @param schema
	 */
	public Events(ApiSchemaModel schema) {
		super(schema);
	}

	/**
	 * @param model
	 * @param schema
	 */
	public Events(ResourceModel model, ApiSchemaModel schema) {
		super(model, schema);
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private <T>List<T> getEventList(String eventName, Class<T> tClass){
		List<T> events = new ArrayList<T>();
		Constructor<T> constructorT = null;
		try {
			constructorT = tClass.getConstructor(ResourceModel.class, ApiSchemaModel.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		}
		
		List<Map<String, Object>> rawEvents = this.model.getProperty(eventName, List.class);
		for (Map<String, Object> rawEvent : rawEvents) {
			try{
				T t = constructorT.newInstance(new ResourceModel(rawEvent), schema);
				events.add(t);
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return events;
	}
	
	/* (non-Javadoc)
	 * @see io.spire.api.Resource#initialize()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void initialize() {
		super.initialize();
		
		// FIXME: make this a method and use Generics?? => getEventList(...)
		messages = new ArrayList<Message>();
		List<Map<String, Object>> rawEvents = this.model.getProperty("messages", List.class);
		for (Map<String, Object> rawEvent : rawEvents) {
			Message message = new Message(new ResourceModel(rawEvent), schema);
			messages.add(message);
		}
		
		joins = new ArrayList<Join>();
		rawEvents = this.model.getProperty("joins", List.class);
		for (Map<String, Object> rawEvent : rawEvents) {
			Join join = new Join(new ResourceModel(rawEvent), schema);
			joins.add(join);
		}
		
		parts = new ArrayList<Part>();
		rawEvents = this.model.getProperty("parts", List.class);
		for (Map<String, Object> rawEvent : rawEvents) {
			Part part = new Part(new ResourceModel(rawEvent), schema);
			parts.add(part);
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
	
	/**
	 * Gets a {@link List} of {@link Message}
	 * 
	 * @return {@link List<Message>}
	 */
	public List<Message> getMessages(){
		return messages;
	}
	
	/**
	 * Gets a {@link List} of {@link Join}
	 * 
	 * @return {@link List<Join>}
	 */
	public List<Join> getJoins(){
		return joins;
	}
	
	/**
	 * Gets a {@link List} of {@link Part}
	 * 
	 * @return {@link List<Part>}
	 */
	public List<Part> getParts(){
		return parts;
	}
	
	@SuppressWarnings("unchecked")
	public <T>List<T> getEventList(EventType type){
		switch (type) {
		case Message:
			return (List<T>)messages;
		case Join:
			return (List<T>)joins;
		case Part:
			return (List<T>)parts;
		default:
			return null;
		}
	}
	
	/**
	 * Gets a last timestamp
	 * 
	 * @return {@link String}
	 */
	public String getLastTimestamp(){
		Object last = this.model.getProperty("last", Object.class);
		return (last != null) ? last.toString() : null;
	}
	
	/**
	 * Gets a first timestamp
	 * 
	 * @return {@link String}
	 */
	public String getFirstTimestamp(){
		return this.model.getProperty("first", Object.class).toString();
	}
}
