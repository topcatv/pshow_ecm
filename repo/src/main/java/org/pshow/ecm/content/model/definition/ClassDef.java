package org.pshow.ecm.content.model.definition;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class ClassDef {
	
	@XStreamAsAttribute
	public String name;
	public String title;
	public String description;
	@XStreamAsAttribute
	public String parent;
	public List<PropertyDef> properties;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<PropertyDef> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyDef> properties) {
		this.properties = properties;
	}
}
