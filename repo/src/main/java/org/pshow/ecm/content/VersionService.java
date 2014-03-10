package org.pshow.ecm.content;

import org.pshow.ecm.content.exception.CheckInException;
import org.pshow.ecm.content.exception.CheckOutException;
import org.pshow.ecm.content.model.Version;
import org.pshow.ecm.content.model.VersionHistory;
import org.pshow.ecm.content.model.VersionStrategy;


public interface VersionService {
	public Version getVersion(String contentId, String label);
	public void checkOut(String contentId) throws CheckOutException;
	public void cancelCheckOut(String contentId) throws CheckOutException;
	public Version checkIn(String contentId, String comments, VersionStrategy strategy) throws CheckInException;
	public VersionHistory getVersionHistory(String contentId);
	public void restore(String contentId, String versionNumber);
}
