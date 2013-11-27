package org.pshow.ecm.persistence.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ps_version_history")
public class VersionHistory extends IdEntity {
	
	private List<Version> versions;

	@OneToMany
	public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}
}
