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
package org.pshow.ecm.content;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pshow.ecm.SpringTransactionalTestCase;
import org.pshow.ecm.content.exception.ContentConstraintException;
import org.pshow.ecm.content.metadata.ContentSchemaHolder;
import org.pshow.ecm.content.model.PropertyValue;
import org.pshow.ecm.content.model.Workspace;
import org.pshow.ecm.utils.TestDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author topcat
 * 
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ContentServiceTest extends SpringTransactionalTestCase {

	private ContentService contentService;
	private ContentSchemaHolder csh;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#getRoot(java.lang.String)}.
	 */
	@Test
	public void testGetRoot() {
		String root = contentService.getRoot("not_exist_ws");
		assertEquals(null, root);

		String workspace_name = createDefaultWorkspace();
		root = contentService.getRoot(workspace_name);

		assertNotNull("根节点必需存在", root);

		PropertyValue property = contentService.getProperty(root, "sys:name");

		assertEquals(workspace_name + ":ROOT", property.getString());
	}

	private String createDefaultWorkspace() {
		String workspace_name = "default";
		if (contentService.findWorkspace(workspace_name) == null) {
			contentService.createWorkspace(workspace_name);
		}
		return workspace_name;
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#getProperties(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetProperties() {
		String workspace_name = createDefaultWorkspace();
		String type = "test:TestType";
		String parentId = contentService.getRoot(workspace_name);
		String name = "全名";
		Map<String, PropertyValue> properties = TestDataLoader.loadData(
				"test_getproperties", csh);
		String contentId = contentService.createContent(type, parentId, name,
				properties);
		Map<String, PropertyValue> load_properties = contentService
				.getProperties(contentId);
		Iterator<Entry<String, PropertyValue>> iterator = properties.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, PropertyValue> entry = (Map.Entry<String, PropertyValue>) iterator
					.next();
			PropertyValue propertyValue = load_properties.get(entry.getKey());
			assertEquals(entry.getValue(), propertyValue);
		}
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#getProperty(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetProperty() {
		String workspace_name = createDefaultWorkspace();
		String type = "test:TestType";
		String parentId = contentService.getRoot(workspace_name);
		String name = "测试取单个属性";
		Map<String, PropertyValue> properties = TestDataLoader.loadData(
				"test_getproperties", csh);
		String contentId = contentService.createContent(type, parentId, name,
				properties);
		Iterator<Entry<String, PropertyValue>> iterator = properties.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, PropertyValue> entry = (Map.Entry<String, PropertyValue>) iterator
					.next();
			String property_name = entry.getKey();
			PropertyValue property = contentService.getProperty(contentId,
					property_name);
			assertEquals(entry.getValue(), property);
		}
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#setProperty(java.lang.String, java.lang.String, org.pshow.ecm.content.model.PropertyValue)}
	 * .
	 */
	@Test
	public void testSetProperty() {
		String workspace_name = createDefaultWorkspace();
		String type = "test:TestType";
		String parentId = contentService.getRoot(workspace_name);
		String name = "测试属性设置";
		String contentId = contentService.createContent(type, parentId, name);
		PropertyValue value = new PropertyValue("用户名");
		contentService.setProperty(contentId, "test:username", value);

		PropertyValue propertyValue = contentService.getProperty(contentId,
				"test:username");

		assertEquals(value, propertyValue);

		value = new PropertyValue("test001");

		contentService.setProperty(contentId, "test:username", value);

		propertyValue = contentService.getProperty(contentId, "test:username");

		assertEquals(value, propertyValue);
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#setProperites(java.lang.String, java.util.Map)}
	 * .
	 */
	@Test
	public void testSetProperites() {
		String workspace_name = createDefaultWorkspace();
		String type = "test:TestType";
		String parentId = contentService.getRoot(workspace_name);
		String name = "测试取所有属性";
		Map<String, PropertyValue> properties = TestDataLoader.loadData(
				"test_getproperties", csh);
		String contentId = contentService.createContent(type, parentId, name,
				properties);
		Iterator<Entry<String, PropertyValue>> iterator = properties.entrySet()
				.iterator();
		Map<String, PropertyValue> properties2 = contentService
				.getProperties(contentId);
		while (iterator.hasNext()) {
			Map.Entry<String, PropertyValue> entry = (Map.Entry<String, PropertyValue>) iterator
					.next();
			String property_name = entry.getKey();
			PropertyValue property = properties2.get(property_name);
			assertEquals(entry.getValue(), property);
		}
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#addProperty(java.lang.String, java.lang.String, org.pshow.ecm.content.model.PropertyValue)}
	 * .
	 */
	@Test
	public void testAddProperty() {
		String workspace_name = createDefaultWorkspace();
		String type = "test:TestType";
		String parentId = contentService.getRoot(workspace_name);
		String name = "测试添加属性";
		Map<String, PropertyValue> properties = TestDataLoader.loadData(
				"test_getproperties", csh);
		String contentId = contentService.createContent(type, parentId, name);
		Iterator<Entry<String, PropertyValue>> iterator = properties.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, PropertyValue> entry = (Map.Entry<String, PropertyValue>) iterator
					.next();
			String property_name = entry.getKey();
			PropertyValue value = entry.getValue();
			contentService.addProperty(contentId, property_name, value);
			PropertyValue property = contentService.getProperty(contentId, property_name);
			assertEquals(value, property);
		}
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#removeProperty(java.lang.String, java.lang.String)}
	 * .
	 * @throws ContentConstraintException 
	 */
	@Test(expected=ContentConstraintException.class)
	public void testRemoveProperty() throws ContentConstraintException {
		String workspace_name = createDefaultWorkspace();
		String type = "test:TestType";
		String parentId = contentService.getRoot(workspace_name);
		String name = "测试属性移除";
		String contentId = contentService.createContent(type, parentId, name);
		
		PropertyValue value = new PropertyValue("用户名");
		contentService.setProperty(contentId, "test:username", value);
		PropertyValue propertyValue = contentService.getProperty(contentId,
				"test:username");
		assertEquals(value, propertyValue);
		
		value = new PropertyValue(20);
		contentService.setProperty(contentId, "test:age", value);
		propertyValue = contentService.getProperty(contentId,
				"test:age");
		assertEquals(value, propertyValue);
		
		contentService.removeProperty(contentId, "test:age");
		
		propertyValue = contentService.getProperty(contentId,
				"test:age");
		
		assertNull(propertyValue);
		//必填属性不可删除
		contentService.removeProperty(contentId, "test:username");
		
		fail("not to here");
		
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#getType(java.lang.String)}.
	 */
	@Test
	public void testGetType() {
		String workspace_name = createDefaultWorkspace();
		String type = "test:TestType";
		String parentId = contentService.getRoot(workspace_name);
		String name = "测试获取类型";
		String contentId = contentService.createContent(type, parentId, name);
		String ct = contentService.getType(contentId);
		assertEquals(type, ct);
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#getChild(java.lang.String)}.
	 */
	@Test
	public void testGetChild() {
		String workspace_name = createDefaultWorkspace();
		String type = "test:TestType";
		String parentId = contentService.getRoot(workspace_name);
		String name = "父";
		String contentId = contentService.createContent(type, parentId, name);
		String c1 = contentService.createContent(type, contentId, "子1");
		String c2 = contentService.createContent(type, contentId, "子2");
		String c3 = contentService.createContent(type, contentId, "子3");
		
		List<String> child = contentService.getChild(contentId);
		
		assertTrue(child.contains(c1));
		assertTrue(child.contains(c2));
		assertTrue(child.contains(c3));
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#removeContent(java.lang.String)}
	 * .
	 */
	public void testRemoveContent() {
		String workspace_name = createDefaultWorkspace();
		String type = "test:TestType";
		String parentId = contentService.getRoot(workspace_name);
		String name = "测试移除内容";
		String contentId = contentService.createContent(type, parentId, name);
		PropertyValue value = new PropertyValue("用户名");
		contentService.setProperty(contentId, "test:username", value);
		PropertyValue propertyValue = contentService.getProperty(contentId,
				"test:username");
		assertEquals(value, propertyValue);
		
		contentService.removeContent(contentId);
		propertyValue = contentService.getProperty(contentId,
				"test:username");
		assertNull(propertyValue);
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#createWorkspace(java.lang.String)}
	 * .
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void testCreateWorkspace() {
		String workspace_name = "my_workspace";
		Workspace workspace = contentService.createWorkspace(workspace_name);
		assertEquals(workspace_name, workspace.getName());
		assertNotNull(workspace.getId());
		// 每个workspace都必须有一个根节点，并且在workspace被创建时默认同时创建
		assertTrue(StringUtils.isNotBlank(workspace.getRoot()));

		// 同名工作空间不允许创建
		contentService.createWorkspace(workspace_name);
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#findWorkspace(java.lang.String)}
	 * .
	 */
	@Test
	public void testFindWorkspace() {
		String workspace_name = "by_find_ws";
		contentService.createWorkspace(workspace_name);
		Workspace findWorkspace = contentService.findWorkspace(workspace_name);
		assertEquals(workspace_name, findWorkspace.getName());

		// 找不到workspace返回null
		Workspace not_exist_ws = contentService.findWorkspace("can_not_found");

		assertEquals(null, not_exist_ws);
	}

	@Autowired
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	@Autowired
	@Qualifier("contentSchemaHolder")
	public void setCsh(ContentSchemaHolder csh) {
		this.csh = csh;
	}

}
