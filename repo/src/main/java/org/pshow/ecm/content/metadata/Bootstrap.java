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
package org.pshow.ecm.content.metadata;

import java.io.IOException;

import org.pshow.ecm.content.model.definition.PSDef;
import org.pshow.ecm.utils.XmlUtil;
import org.springframework.core.io.Resource;

/**
 * @author topcat
 * 
 */
public class Bootstrap {
	private Resource[] xml_resources;

	private ContentSchemaHolder csh;

	public void onBootstrap() throws IOException {
		for (Resource xml : xml_resources) {
			registMetaData(xml);
		}
	}

	private void registMetaData(Resource xml) throws IOException {
		PSDef schema = convertToSchame(xml);
		csh.registContentSchema(schema);
	}

	private PSDef convertToSchame(Resource xml) throws IOException {
		return XmlUtil.toBeanFromStream(xml.getInputStream(), PSDef.class);
	}

	public void setCsh(ContentSchemaHolder csh) {
		this.csh = csh;
	}

	public void setXml_resources(Resource[] xml_resources) {
		this.xml_resources = xml_resources;
	}
}
