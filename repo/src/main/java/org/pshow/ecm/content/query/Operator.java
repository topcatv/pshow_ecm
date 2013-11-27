package org.pshow.ecm.content.query;

import java.io.Serializable;
import java.util.List;

import org.pshow.ecm.content.model.PropertyValue;


public interface Operator {
	public void setField(String field);
	public void setValues(List<Serializable> propertyValue);
	public void setValue(PropertyValue propertyValue);
}
