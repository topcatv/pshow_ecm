package org.pshow.ecm.content.model.definition;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("property")
public class PropertyDef {
	@XStreamAsAttribute
	public String name;
	public String title;
	@XStreamAlias("type")
	public String dataTypeName;
	public List<String> constraints;
	@XStreamAsAttribute
	public boolean indexed;
	@XStreamAsAttribute
	public boolean multipled;
	@XStreamAsAttribute
	public boolean mandatory;

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

	public boolean isIndexed() {
		return indexed;
	}

	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}

	public boolean isMultipled() {
		return multipled;
	}

	public void setMultipled(boolean multipled) {
		this.multipled = multipled;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getDataTypeName() {
		return dataTypeName;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	public List<String> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<String> constraints) {
		this.constraints = constraints;
	}
}
