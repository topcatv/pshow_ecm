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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pshow.ecm.SpringTransactionalTestCase;
import org.pshow.ecm.content.model.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author topcat
 * 
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ContentServiceTest extends SpringTransactionalTestCase {

	private ContentService contentService;

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
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#getProperties(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetProperties() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#getProperty(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetProperty() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#setProperty(java.lang.String, java.lang.String, org.pshow.ecm.content.model.PropertyValue)}
	 * .
	 */
	@Test
	public void testSetProperty() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#setProperites(java.lang.String, java.util.Map)}
	 * .
	 */
	@Test
	public void testSetProperites() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#addProperty(java.lang.String, java.lang.String, org.pshow.ecm.content.model.PropertyValue)}
	 * .
	 */
	@Test
	public void testAddProperty() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#removeProperty(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testRemoveProperty() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#getType(java.lang.String)}.
	 */
	@Test
	public void testGetType() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#getFacets(java.lang.String)}.
	 */
	@Test
	public void testGetFacets() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#getChild(java.lang.String)}.
	 */
	@Test
	public void testGetChild() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#createContent(java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testCreateContentStringStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#createContent(java.lang.String, java.lang.String, java.lang.String, java.util.Map)}
	 * .
	 */
	@Test
	public void testCreateContentStringStringStringMapOfStringPropertyValue() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#removeContent(java.lang.String)}
	 * .
	 */
	@Test
	public void testRemoveContent() {
		fail("Not yet implemented");
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
		assertTrue(StringUtils.isNotBlank(workspace.getRoot()));
		contentService.createWorkspace(workspace_name);
	}

	/**
	 * Test method for
	 * {@link org.pshow.ecm.content.ContentService#findWorkspace(java.lang.String)}
	 * .
	 */
	@Test
	public void testFindWorkspace() {
		fail("Not yet implemented");
	}

	@Autowired
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

}
