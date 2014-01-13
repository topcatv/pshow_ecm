package org.pshow.ecm.content;

import org.pshow.ecm.content.model.Version;
import org.pshow.ecm.content.model.VersionHistory;
import org.pshow.ecm.content.model.VersionStrategy;


public interface VersionService {
	public Version getVersion(String contentId, String label);
	public boolean checkOut(String contentId);
	public boolean cancelCheckOut(String contentId);
	public Version checkIn(String contentId, String comments, VersionStrategy strategy);
	public VersionHistory getVersionHistory(String contentId);
	public void restore(String contentId, String label);
}
