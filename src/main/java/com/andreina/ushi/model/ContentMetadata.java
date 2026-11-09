package com.andreina.ushi.model;

public class ContentMetadata {
	
	// private String contentId = null;
	
	private String entityType = null;    
	private String entityId = null; 
	private String resourceType = null; 
	private String format = null; 
	
	public ContentMetadata() {		
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}


	

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
}
