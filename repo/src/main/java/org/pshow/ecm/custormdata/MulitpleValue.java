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
package org.pshow.ecm.custormdata;

import java.util.ArrayList;
import java.util.Date;

import org.pshow.ecm.content.model.PropertyValue;
import org.pshow.ecm.content.model.PropertyValue.ValueType;

/**
 * @author topcat
 * @param <E>
 *
 */
public class MulitpleValue<E> extends ArrayList<E> {
	
	private static final long serialVersionUID = 1923328615958767286L;

	public ValueType getType(){
		if(this.isEmpty()){
			return null;
		}
		E value = this.get(0);
		if (value instanceof String) {
			return PropertyValue.ValueType.STRING;
		} else if (value instanceof Boolean) {
			return PropertyValue.ValueType.BOOLEAN;
		} else if (value instanceof Integer) {
			return PropertyValue.ValueType.INT;
		} else if (value instanceof Long) {
			return PropertyValue.ValueType.LONG;
		} else if (value instanceof Float) {
			return PropertyValue.ValueType.FLOAT;
		} else if (value instanceof Double) {
			return PropertyValue.ValueType.DOUBLE;
		} else if (value instanceof Date) {
			return PropertyValue.ValueType.DATE;
		}
		return ValueType.ANY;
	}

}
