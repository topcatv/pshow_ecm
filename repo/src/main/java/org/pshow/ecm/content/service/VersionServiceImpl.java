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

import org.pshow.ecm.content.VersionService;
import org.pshow.ecm.content.model.Version;
import org.pshow.ecm.content.model.VersionHistory;
import org.pshow.ecm.content.model.VersionStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author topcat
 *
 */
@Service
@Transactional
public class VersionServiceImpl implements VersionService {

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.VersionService#getVersion(java.lang.String, java.lang.String)
	 */
	@Override
	public Version getVersion(String contentId, String label) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.VersionService#checkOut(java.lang.String)
	 */
	@Override
	public boolean checkOut(String contentId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.VersionService#cancelCheckOut(java.lang.String)
	 */
	@Override
	public boolean cancelCheckOut(String contentId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.VersionService#checkIn(java.lang.String, java.lang.String, org.pshow.ecm.content.model.VersionStrategy)
	 */
	@Override
	public Version checkIn(String contentId, String comments,
			VersionStrategy strategy) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.VersionService#getVersionHistory(java.lang.String)
	 */
	@Override
	public VersionHistory getVersionHistory(String contentId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.pshow.ecm.content.VersionService#restore(java.lang.String, java.lang.String)
	 */
	@Override
	public void restore(String contentId, String label) {
		// TODO Auto-generated method stub

	}

}
