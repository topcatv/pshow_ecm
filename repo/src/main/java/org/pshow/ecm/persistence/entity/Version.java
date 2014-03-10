package org.pshow.ecm.persistence.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "ps_version")
public class Version extends IdEntity {
	
	private String label;
	private String comments;
	private int index;
	private VersionHistory versionHistory;
	private Set<VersionedProperty> versionedProperties;

	@NotBlank
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@ManyToOne
	public VersionHistory getVersionHistory() {
		return versionHistory;
	}

	public void setVersionHistory(VersionHistory versionHistory) {
		this.versionHistory = versionHistory;
	}

	@OneToMany(mappedBy="version")
	public Set<VersionedProperty> getVersionedProperties() {
		return versionedProperties;
	}

	public void setVersionedProperties(Set<VersionedProperty> versionedProperties) {
		this.versionedProperties = versionedProperties;
	}
	
	public void addVersionedProperty(VersionedProperty versionedProperty){
		if(this.versionedProperties == null){
			this.versionedProperties = new HashSet<VersionedProperty>(1);
		}
		versionedProperties.add(versionedProperty);
		versionedProperty.setVersion(this);
	}
}
