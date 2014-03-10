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
package org.pshow.ecm.content.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.pshow.ecm.content.ContentService;
import org.pshow.ecm.content.exception.ContentConstraintException;
import org.pshow.ecm.content.metadata.ContentSchemaHolder;
import org.pshow.ecm.content.model.PropertyValue;
import org.pshow.ecm.content.model.PropertyValue.ValueType;
import org.pshow.ecm.content.model.Workspace;
import org.pshow.ecm.content.model.definition.TypeDef;
import org.pshow.ecm.persistence.dao.ContentDao;
import org.pshow.ecm.persistence.dao.PropertyDao;
import org.pshow.ecm.persistence.dao.WorkspaceDao;
import org.pshow.ecm.persistence.entity.Content;
import org.pshow.ecm.persistence.entity.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author topcat
 * 
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService {

	@Autowired
	private WorkspaceDao workspaceDao;
	@Autowired
	private ContentDao contentDao;
	@Autowired
	private PropertyDao propertyDao;
	@Autowired
	private ContentSchemaHolder csh;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.ContentService#getRoot(java.lang.String)
	 */
	@Override
	public String getRoot(String workspace) {
		org.pshow.ecm.persistence.entity.Workspace ps_workspace = workspaceDao
				.findByName(workspace);
		if (ps_workspace == null) {
			return null;
		}
		return ps_workspace.getRoot().getUuid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.ContentService#getProperties(java.lang.String)
	 */
	@Override
	public Map<String, PropertyValue> getProperties(String contentId) {
		Map<String, PropertyValue> map = new HashMap<String, PropertyValue>();
		List<Property> properties = propertyDao.findByContentUuid(contentId);
		if (properties != null) {
			for (Property p : properties) {
				map.put(p.getName(), getPropertyValue(p));
			}
		}
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.ContentService#getProperty(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public PropertyValue getProperty(String contentId, String name) {
		Property property = propertyDao.findByContentUuidAndName(contentId,
				name);
		return property == null ? null : getPropertyValue(property);
	}

	private PropertyValue getPropertyValue(Property property) {
		int actualType = property.getActualType();
		if (ValueType.INT.getIndex() == actualType) {
			return new PropertyValue(property.getIntValue());
		} else if (ValueType.LONG.getIndex() == actualType) {
			return new PropertyValue(property.getLongValue());
		} else if (ValueType.FLOAT.getIndex() == actualType) {
			return new PropertyValue(property.getFloatValue());
		} else if (ValueType.DOUBLE.getIndex() == actualType) {
			return new PropertyValue(property.getDoubleValue());
		} else if (ValueType.DATE.getIndex() == actualType) {
			return new PropertyValue(property.getDateValue());
		} else if (ValueType.STRING.getIndex() == actualType) {
			return new PropertyValue(property.getStringValue());
		} else if (ValueType.BOOLEAN.getIndex() == actualType) {
			return new PropertyValue(property.isBooleanValue());
		} else if (ValueType.ANY.getIndex() == actualType) {
			return new PropertyValue(property.getObjectValue());
		}
		return new PropertyValue(property.getObjectValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.ContentService#setProperty(java.lang.String,
	 * java.lang.String, org.pshow.ecm.content.model.PropertyValue)
	 */
	@Override
	public void setProperty(String contentId, String name, PropertyValue value) {
		Property property = propertyDao.findByContentUuidAndName(contentId,
				name);
		if (property == null) {
			property = new Property();
			Content content = contentDao.findByUuid(contentId);
			property.setName(name);
			content.addProperty(property);
		}
		int index = value.getType().getIndex();
		property.setActualType(index);
		setPropertyValue(property, value);
		propertyDao.save(property);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.ContentService#setProperites(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public void setProperites(String contentId,
			Map<String, PropertyValue> values) {
		if (values != null) {
			Iterator<Entry<String, PropertyValue>> it = values.entrySet()
					.iterator();
			while (it.hasNext()) {
				Entry<String, PropertyValue> entry = it.next();
				this.setProperty(contentId, entry.getKey(), entry.getValue());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.ContentService#addProperty(java.lang.String,
	 * java.lang.String, org.pshow.ecm.content.model.PropertyValue)
	 */
	@Override
	public void addProperty(String contentId, String name, PropertyValue value) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pshow.ecm.content.ContentService#removeProperty(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void removeProperty(String contentId, String name) throws ContentConstraintException {
		
		Property property = propertyDao.findByContentUuidAndName(contentId,
				name);
		if(isMandatoryProperty(property)){
			throw new ContentConstraintException(String.format("Property '%s' is mandatory.", property.getName()));
		}
		if (property != null) {
			property.getContent().removeProperty(property);
			propertyDao.delete(property);
		}
	}

	private boolean isMandatoryProperty(Property property) {
		String contentType = property.getContent().getContentType();
		TypeDef typeDef = csh.getContentType(contentType);
		boolean mandatory = typeDef.getPropoerty(property.getName()).isMandatory();
		return mandatory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.ContentService#getType(java.lang.String)
	 */
	@Override
	public String getType(String contentId) {
		Content content = contentDao.findByUuid(contentId);
		return content == null ? null : content.getContentType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.ContentService#getFacets(java.lang.String)
	 */
	@Override
	public List<String> getFacets(String contentId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.ContentService#getChild(java.lang.String)
	 */
	@Override
	public List<String> getChild(String contentId) {
		List<String> list = new ArrayList<String>();
		List<Content> children = contentDao.findByParentUuid(contentId);
		for (Content c : children) {
			list.add(c.getUuid());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.ContentService#createContent(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public String createContent(String type, String parentId, String name) {
		org.pshow.ecm.persistence.entity.Content content = new org.pshow.ecm.persistence.entity.Content();
		content.setName(name);
		content.setContentType(type);
		Content parent = contentDao.findByUuid(parentId);
		content.setParent(parent);
		content.setWorksapce(parent.getWorksapce());
		Property property = new Property();
		property.setName("sys:name");
		property.setActualType(ValueType.STRING.getIndex());
		property.setStringValue(name);
		content.addProperty(property);
		contentDao.save(content);
		propertyDao.save(property);
		return content.getUuid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.ContentService#createContent(java.lang.String,
	 * java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public String createContent(String type, String parentId, String name,
			Map<String, PropertyValue> properties) {
		String uuid = createContent(type, parentId, name);
		Content content = contentDao.findByUuid(uuid);
		if (properties != null && !properties.isEmpty()) {
			Iterator<Entry<String, PropertyValue>> iterator = properties
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, PropertyValue> entry = iterator.next();
				Property p = new Property();
				p.setName(entry.getKey());
				PropertyValue propertyValue = entry.getValue();
				int index = propertyValue.getType().getIndex();
				p.setActualType(index);
				setPropertyValue(p, propertyValue);
				content.addProperty(p);
				propertyDao.save(p);
			}
		}
		return uuid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.ContentService#removeContent(java.lang.String)
	 */
	@Override
	public void removeContent(String contentId) {
		Content content = contentDao.findByUuid(contentId);
		if (content != null) {
//			if(content.getProperties() != null){
//				propertyDao.delete(content.getProperties());
//			}
			contentDao.delete(content);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pshow.ecm.content.ContentService#createWorkspace(java.lang.String)
	 */
	@Override
	public Workspace createWorkspace(String name) {
		org.pshow.ecm.persistence.entity.Content root = new org.pshow.ecm.persistence.entity.Content();
		String rootName = String.format("%s:ROOT", name);
		root.setName(rootName);
		Property property = new Property();
		property.setName("sys:name");
		property.setActualType(ValueType.STRING.getIndex());
		property.setStringValue(rootName);
		root.addProperty(property);
		org.pshow.ecm.persistence.entity.Workspace workspace = new org.pshow.ecm.persistence.entity.Workspace();
		workspace.setName(name);
		workspace.setRoot(root);
		workspaceDao.save(workspace);
		root.setWorksapce(workspace);
		contentDao.save(root);
		propertyDao.save(property);
		return new Workspace(workspace);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.ContentService#findWorkspace(java.lang.String)
	 */
	@Override
	public Workspace findWorkspace(String name) {
		org.pshow.ecm.persistence.entity.Workspace workspace = workspaceDao
				.findByName(name);
		if (workspace == null) {
			return null;
		}
		return new Workspace(workspace);
	}

	public void setPropertyDao(PropertyDao propertyDao) {
		this.propertyDao = propertyDao;
	}

	/**
	 * 根据属性的类型设置属性相应的值
	 */
	private void setPropertyValue(Property p, PropertyValue value) {
		switch (p.getActualType()) {
		case 1:
			p.setIntValue(value.getInt());
			break;
		case 2:
			p.setLongValue(value.getLong());
			break;
		case 3:
			p.setFloatValue(value.getFloat());
			break;
		case 4:
			p.setDoubleValue(value.getDouble());
			break;
		case 5:
			p.setDateValue(value.getDate());
			break;
		case 6:
			p.setStringValue(value.getString());
			break;
		case 7:
			p.setBooleanValue(value.getBoolean());
			break;
		default:
			if (value == null) {
				p.setObjectValue(null);
			} else {
				p.setObjectValue(value.getValue());
			}
			break;
		}
	}

}
