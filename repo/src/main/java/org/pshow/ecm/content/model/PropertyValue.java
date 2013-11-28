/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pshow.ecm.content.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author topcat
 * 
 */
public class PropertyValue implements Serializable {

	private static final long serialVersionUID = -5867191236511333471L;

	private Serializable value;

	public PropertyValue(Serializable value) {
		this.value = value;
	}

	public ValueType getType() {
		if (value instanceof String) {
			return ValueType.STRING;
		} else if (value instanceof Boolean) {
			return ValueType.BOOLEAN;
		} else if (value instanceof Integer) {
			return ValueType.INT;
		} else if (value instanceof Long) {
			return ValueType.LONG;
		} else if (value instanceof Float) {
			return ValueType.FLOAT;
		} else if (value instanceof Double) {
			return ValueType.DOUBLE;
		} else if (value instanceof Date) {
			return ValueType.DATE;
		}
		return ValueType.ANY;
	}

	public String getString() {
		return (String) value;
	}

	public Boolean getBoolean() {
		return (Boolean) value;
	}

	public Long getLong() {
		return (Long) value;
	}

	public Integer getInt() {
		return (Integer) value;
	}

	public Float getFloat() {
		return (Float) value;
	}

	public Double getDouble() {
		return (Double) value;
	}

	public Date getDate() {
		return (Date) value;
	}
	
	public Serializable getValue(){
		return value;
	}

	public enum ValueType {
		INT, LONG, FLOAT, DOUBLE, DATE, STRING, BOOLEAN, ANY
	}

}
