package org.pshow.ecm.security.model;

import java.util.List;


public interface User extends Subject {
	public String getName();
	public String getDispalyName();
	public void setName(String name);
	public void setDispalyName(String displayName);
	public List<Group> getGroups();
}
