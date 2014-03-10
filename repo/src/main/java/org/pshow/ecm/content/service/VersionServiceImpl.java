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

import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.pshow.ecm.content.VersionService;
import org.pshow.ecm.content.exception.CheckInException;
import org.pshow.ecm.content.exception.CheckOutException;
import org.pshow.ecm.content.model.Version;
import org.pshow.ecm.content.model.VersionHistory;
import org.pshow.ecm.content.model.VersionStrategy;
import org.pshow.ecm.persistence.dao.ContentDao;
import org.pshow.ecm.persistence.dao.PropertyDao;
import org.pshow.ecm.persistence.dao.VersionDao;
import org.pshow.ecm.persistence.dao.VersionHistoryDao;
import org.pshow.ecm.persistence.dao.VersionPropertyDao;
import org.pshow.ecm.persistence.entity.Content;
import org.pshow.ecm.persistence.entity.Property;
import org.pshow.ecm.persistence.entity.VersionedProperty;
import org.pshow.ecm.utils.common.ExtBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author topcat
 * 
 */
@Service
@Transactional
public class VersionServiceImpl implements VersionService {

	@Autowired
	private ContentDao contentDao;
	@Autowired
	private VersionHistoryDao versionHistoryDao;
	@Autowired
	private VersionDao versionDao;
	@Autowired
	private VersionPropertyDao versionPropertyDao;
	@Autowired
	private PropertyDao propertyDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.VersionService#getVersion(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Version getVersion(String contentId, String label) {
		org.pshow.ecm.persistence.entity.Version version = versionDao
				.findVersion(contentId, label);
		return new Version(version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.VersionService#checkOut(java.lang.String)
	 */
	@Override
	public void checkOut(String contentId) throws CheckOutException {
		Content content = contentDao.findByUuid(contentId);
		if (content.isCheckouted()) {
			throw new CheckOutException(String.format(
					"Content %s already be checkouted by others", contentId));
		}
		content.setCheckouted(true);
		contentDao.save(content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pshow.ecm.content.VersionService#cancelCheckOut(java.lang.String)
	 */
	@Override
	public void cancelCheckOut(String contentId) throws CheckOutException {
		Content content = contentDao.findByUuid(contentId);
		if (!content.isCheckouted() || content.isVersioned()) {
			throw new CheckOutException(String.format(
					"Content %s never be checkouted or it's already checkin.",
					contentId));
		}
		content.setCheckouted(false);
		contentDao.save(content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.VersionService#checkIn(java.lang.String,
	 * java.lang.String, org.pshow.ecm.content.model.VersionStrategy)
	 */
	@Override
	public Version checkIn(String contentId, String comments,
			VersionStrategy strategy) throws CheckInException {

		Content content = contentDao.findByUuid(contentId);
		if (!content.isCheckouted()) {
			throw new CheckInException(String.format(
					"Content %s has not been checkouted.", contentId));
		}

		content.setCheckouted(false);
		content.setVersioned(true);
		org.pshow.ecm.persistence.entity.VersionHistory versionHistory = content
				.getVersionHistory();

		org.pshow.ecm.persistence.entity.Version version = new org.pshow.ecm.persistence.entity.Version();

		version.setComments(comments);
		if (versionHistory == null) {
			versionHistory = new org.pshow.ecm.persistence.entity.VersionHistory();
			content.setVersionHistory(versionHistory);
			versionHistory.setContent(content);

			versionHistoryDao.save(versionHistory);
		}
		version.setLabel(nextVersionNumber(strategy,
				versionHistory.getLastVersion()));
		versionHistory.addVersion(version);

		versionDao.save(version);
		Set<Property> properties = content.getProperties();
		if (CollectionUtils.isNotEmpty(properties)) {
			for (Property property : properties) {
				VersionedProperty versionedProperty = new VersionedProperty();
				ExtBeanUtils.copyProperties(versionedProperty, property);
				versionedProperty.setId(null);
				version.addVersionedProperty(versionedProperty);
				versionPropertyDao.save(versionedProperty);
			}
		}

		return new Version(version);
	}

	private String nextVersionNumber(VersionStrategy strategy,
			org.pshow.ecm.persistence.entity.Version lastVersion) {
		if (null == lastVersion) {
			if (strategy == VersionStrategy.major) {
				return "1.0";
			} else {
				return "0.1";
			}
		}
		float number = Float.parseFloat(lastVersion.getLabel());
		if (strategy == VersionStrategy.major) {
			number += 1.0;
		} else {
			number += 0.1;
		}
		return String.valueOf(number);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pshow.ecm.content.VersionService#getVersionHistory(java.lang.String)
	 */
	@Override
	public VersionHistory getVersionHistory(String contentId) {
		Content content = contentDao.findByUuid(contentId);
		if (content != null) {
			return new VersionHistory(content.getVersionHistory());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pshow.ecm.content.VersionService#restore(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void restore(String contentId, String versionNumber) {
		try {
			this.checkOut(contentId);
			this.checkIn(contentId,
					String.format("restore version to %s", versionNumber),
					VersionStrategy.major);
		} catch (CheckOutException e1) {
			e1.printStackTrace();
		} catch (CheckInException e) {
			e.printStackTrace();
		}
		// 将现有属性与内容断开并删除
		Content content = contentDao.findByUuid(contentId);
		Set<Property> properties = content.getProperties();
		content.setProperties(null);
		if (null != properties) {
			propertyDao.deleteInBatch(properties);
		}
		// 将指定版本属性取出并填充到当前内容中
		org.pshow.ecm.persistence.entity.Version version = versionDao
				.findVersion(contentId, versionNumber);
		Set<VersionedProperty> versionedProperties = version
				.getVersionedProperties();
		if (null != versionedProperties) {
			for (VersionedProperty vp : versionedProperties) {
				Property property = new Property();
				ExtBeanUtils.copyProperties(property, vp);
				property.setId(null);
				content.addProperty(property);
				propertyDao.save(property);
			}
		}
	}
}
