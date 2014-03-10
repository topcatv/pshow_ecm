package org.pshow.ecm.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;

@Entity
@Table(name = "ps_version_history")
public class VersionHistory extends IdEntity {

	private List<Version> versions;

	private Content content;

	@OneToMany(mappedBy = "versionHistory", cascade = { CascadeType.ALL })
	public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	@OneToOne
	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	@Transient
	public Version getLastVersion() {
		if (CollectionUtils.isEmpty(versions)) {
			return null;
		} else {
			return versions.get(versions.size() - 1);
		}
	}
	
	public void addVersion(Version version){
		if(this.versions == null){
			this.versions = new ArrayList<Version>();
		}
		this.versions.add(version);
		version.setVersionHistory(this);
	}
}
