package org.pshow.ecm.security.model;


public interface Ace {
	public Permission getPermission();
	public Subject getSubject();
	public Resource getResource();
}
