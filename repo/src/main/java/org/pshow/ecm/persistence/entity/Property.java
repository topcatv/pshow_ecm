package org.pshow.ecm.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "ps_property")
public class Property extends IdEntity {
	private Content content;
	private int actualType;
	private String name;
	private int intValue;
	private long longValue;
	private float floatValue;
	private double doubleValue;
	private Date dateValue;
	private boolean booleanValue;
	private String stringValue;
	private Serializable objectValue;

	@ManyToOne
	@JoinColumn(name = "content_id")
	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public long getLongValue() {
		return longValue;
	}

	public void setLongValue(long longValue) {
		this.longValue = longValue;
	}

	public float getFloatValue() {
		return floatValue;
	}

	public void setFloatValue(float floatValue) {
		this.floatValue = floatValue;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}

	public boolean isBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Serializable getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(Serializable objectValue) {
		this.objectValue = objectValue;
	}

	public int getActualType() {
		return actualType;
	}

	public void setActualType(int actualType) {
		this.actualType = actualType;
	}
}
