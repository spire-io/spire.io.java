/**
 * 
 */
package io.spire.api;

import java.util.Map;

import io.spire.api.Resource.ResourceModel;
import io.spire.request.Request.RequestType;

/**
 * Represents the capability or set of capabilities for a resource
 * 
 * @since v1.0
 * @author Jorge Gonzalez
 *
 */
public class Capability {
	private String soloCapability;
	private ResourceModel collectionCapability;
	
	/**
	 * Default constructor
	 */
	public Capability() {
		
	}
	
	/**
	 * Construct a capability based on single key 
	 * 
	 * @param capability
	 */
	public Capability(String capability) {
		this.soloCapability = capability;
	}
	
	/**
	 * Construct a capability for a set of keys
	 * 
	 * @param rawModel
	 */
	public Capability(Map<String, Object> rawModel) {
		this.collectionCapability = new ResourceModel(rawModel);
	}
	
	/**
	 * Construct a capability for a set of keys passed
	 * as a {@link ResourceModel}
	 * 
	 * @param model
	 */
	public Capability(ResourceModel model) {
		this.collectionCapability = model;
	}
	
	/**
	 * Gets a capability key by name
	 * 
	 * @param type
	 * @return {@link String}
	 */
	public String getCapabilityFor(String type){
		return collectionCapability.getProperty(type, String.class);
	}
	
	/**
	 * Gets a capability key for a {@link RequestType} method
	 * 
	 * Defaults returns the single capability key if set
	 * 
	 * @param type
	 * @return {@link String}
	 */
	public String getCapabilityFor(RequestType type){
		switch (type) {
		case HTTP_GET:
			return collectionCapability.getProperty("get", String.class);
		case HTTP_DELETE:
			return collectionCapability.getProperty("delete", String.class);
		case HTTP_PUT:
			return collectionCapability.getProperty("update", String.class);
		case HTTP_POST:
			return collectionCapability.getProperty("create", String.class);
		default:
			return this.soloCapability;
		}
	}
	
	/**
	 * Sets a global capability key
	 * 
	 * @param capability
	 */
	public void setCapability(String capability){
		this.soloCapability = capability;
	}
	
	/**
	 * Sets a collection of capabilities as a {@link ResourceModel}
	 * 
	 * @param model
	 */
	public void setCapabilities(ResourceModel model){
		this.collectionCapability = model;
	}
	
	/**
	 * Class Factory Method. Creates a capability based on a {@link Object} that could be
	 * either a String, a Map<String, Object> or a ResourceModel 
	 * 
	 * @param rawCapability
	 * @return {@link Capability}
	 */
	@SuppressWarnings("unchecked")
	public static Capability CreateCapability(Object rawCapability){
		Capability capability = null;		
		if(rawCapability.getClass() == Map.class){
			capability = new Capability((Map<String, Object>)rawCapability);
		}else if(rawCapability.getClass() == ResourceModel.class){
			capability = new Capability((ResourceModel)rawCapability);
		}else{
			capability = new Capability((String)rawCapability);
		}
			
		return capability;
	}
	
	/**
	 * Class Factory Method. Creates a capability based on a global key or a Collection keys
	 * 
	 * @param rawCapability
	 * @param rawCapabilities
	 * @return {@link Capability}
	 */
	public static Capability CreateCapability(String rawCapability, Map<String, Object> rawCapabilities){
		Capability capability = new Capability(rawCapabilities);
		if(rawCapability != null){
			capability.setCapability(rawCapability);
		}
		return capability;
	}
}
