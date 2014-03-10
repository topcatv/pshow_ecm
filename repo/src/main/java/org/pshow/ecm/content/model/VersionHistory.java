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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author topcat
 * 
 */
public class VersionHistory implements Serializable {

	private static final long serialVersionUID = -3382851016486201864L;
	private List<Version> versions;
	private String contentId;

	public VersionHistory(org.pshow.ecm.persistence.entity.VersionHistory vh) {
		contentId = vh.getContent().getUuid();
		versions = new ArrayList<Version>();
		List<org.pshow.ecm.persistence.entity.Version> v = vh.getVersions();
		for (org.pshow.ecm.persistence.entity.Version version : v) {
			versions.add(new Version(version));
		}
	}

	public Version getVersion(String versionNumber) {
		for (Version version : versions) {
			if (StringUtils.equals(versionNumber, version.getNumber())) {
				return version;
			}
		}
		return null;
	}

	public String getContent() {
		return contentId;
	}

	public Version getLastVersion() {
		return versions.get(versions.size() - 1);
	}

	public Version getFirstVersion() {
		return versions.get(0);
	}

	public List<Version> getAllVersions() {
		return versions;
	}

}
