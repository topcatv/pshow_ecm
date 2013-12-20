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

import java.util.List;
import java.util.Map;

import org.pshow.ecm.content.ContentService;
import org.pshow.ecm.content.model.Content;
import org.pshow.ecm.content.model.ContentFacet;
import org.pshow.ecm.content.model.ContentType;
import org.pshow.ecm.content.model.PropertyValue;
import org.pshow.ecm.content.model.PropertyValue.ValueType;
import org.pshow.ecm.content.model.Workspace;
import org.pshow.ecm.persistence.dao.ContentDao;
import org.pshow.ecm.persistence.dao.PropertyDao;
import org.pshow.ecm.persistence.dao.WorkspaceDao;
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

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.ContentService#getRoot(java.lang.String)
	 */
	@Override
	public String getRoot(String workspace) {
		org.pshow.ecm.persistence.entity.Workspace ps_workspace = workspaceDao.findByName(workspace);
		if(ps_workspace == null){
			return null;
		}
		return ps_workspace.getRoot().getUuid();
	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.ContentService#getProperties(java.lang.String)
	 */
	@Override
	public Map<String, PropertyValue> getProperties(String contentId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.ContentService#getProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public PropertyValue getProperty(String contentId, String name) {
		Property property = propertyDao.findByContentUuidAndName(contentId, name);
		int actualType = property.getActualType();
		if(ValueType.STRING.getIndex() == actualType){
			return new PropertyValue(property.getStringValue());
		}
		return new PropertyValue(property.getObjectValue());
	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.ContentService#setProperty(java.lang.String, java.lang.String, org.pshow.ecm.content.model.PropertyValue)
	 */
	@Override
	public void setProperty(String contentId, String name, PropertyValue value) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.ContentService#setProperites(java.lang.String, java.util.Map)
	 */
	@Override
	public void setProperites(String contentId,
			Map<String, PropertyValue> values) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.ContentService#addProperty(java.lang.String, java.lang.String, org.pshow.ecm.content.model.PropertyValue)
	 */
	@Override
	public void addProperty(String contentId, String name, PropertyValue value) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.ContentService#removeProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public void removeProperty(String contentId, String name) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.ContentService#getType(java.lang.String)
	 */
	@Override
	public ContentType getType(String contentId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.ContentService#getFacets(java.lang.String)
	 */
	@Override
	public List<ContentFacet> getFacets(String contentId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.ContentService#getChild(java.lang.String)
	 */
	@Override
	public List<Content> getChild(String contentId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.ContentService#createContent(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String createContent(String type, String parentId, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.ContentService#createContent(java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public String createContent(String type, String parentId, String name,
			Map<String, PropertyValue> properties) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.ContentService#removeContent(java.lang.String)
	 */
	@Override
	public void removeContent(String contentId) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.ContentService#createWorkspace(java.lang.String)
	 */
	@Override
	public Workspace createWorkspace(String name) {
		org.pshow.ecm.persistence.entity.Content root = new org.pshow.ecm.persistence.entity.Content();
		String rootName = String.format("%s:ROOT", name);
		root.setName(rootName);
		contentDao.save(root);
		Property property = new Property();
		property.setContent(root);
		property.setName("base:name");
		property.setActualType(ValueType.STRING.getIndex());
		property.setStringValue(rootName);
		propertyDao.save(property);
		org.pshow.ecm.persistence.entity.Workspace workspace = new org.pshow.ecm.persistence.entity.Workspace();
		workspace.setName(name);
		workspace.setRoot(root);
		workspaceDao.save(workspace);
		return new Workspace(workspace);
	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.ContentService#findWorkspace(java.lang.String)
	 */
	@Override
	public Workspace findWorkspace(String name) {
		org.pshow.ecm.persistence.entity.Workspace workspace = workspaceDao.findByName(name);
		if(workspace == null){
			return null;
		}
		return new Workspace(workspace);
	}

	public void setPropertyDao(PropertyDao propertyDao) {
		this.propertyDao = propertyDao;
	}

}
