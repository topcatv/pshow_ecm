package org.pshow.ecm.security.model;


public interface Group extends Subject {
	public String getName();
	public String setName();
	public String getDispalyName();
	public void setDispalyName(String displayName);
	public User getUsers();
	public void addUser(User user);
	public void removeUser(User user);
	public boolean containUser(User user);
}
