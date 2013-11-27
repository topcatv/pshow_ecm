package org.pshow.ecm.security.model;

import java.util.List;


public interface Acl {
	public List<Ace> getAces();
	public void addAce(Ace ace);
	public void removeAce(Ace ace);
}
