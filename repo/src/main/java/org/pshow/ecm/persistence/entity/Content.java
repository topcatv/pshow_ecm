package org.pshow.ecm.persistence.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ps_content", uniqueConstraints = @UniqueConstraint(columnNames = {
		"name", "parent_id" }))
public class Content extends IdEntity {

	private String uuid;
	private String name;
	private boolean checkouted;
	private boolean folder;
	private boolean versioned;
	private String contentType;
	private Workspace worksapce;

	private Set<Property> properties;

	private VersionHistory versionHistory;

	private Content parent;

	private List<Content> children;

	@PrePersist
	public void prePersist() {
		this.uuid = UUID.randomUUID().toString();
	}

	@Column(unique = true, nullable = false)
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCheckouted() {
		return checkouted;
	}

	public void setCheckouted(boolean checkouted) {
		this.checkouted = checkouted;
	}

	public boolean isFolder() {
		return folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "content")
	public Set<Property> getProperties() {
		return properties;
	}

	public void setProperties(Set<Property> properties) {
		this.properties = properties;
	}

	public void addProperty(Property property) {
		property.setContent(this);
		if (null == properties)
			properties = new HashSet<Property>();
		this.properties.add(property);
	}

	public boolean isVersioned() {
		return versioned;
	}

	public void setVersioned(boolean versioned) {
		this.versioned = versioned;
	}

	@OneToOne(mappedBy = "content")
	public VersionHistory getVersionHistory() {
		return versionHistory;
	}

	public void setVersionHistory(VersionHistory versionHistory) {
		this.versionHistory = versionHistory;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id", nullable = true)
	public Content getParent() {
		return parent;
	}

	public void setParent(Content parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy = "parent")
	public List<Content> getChildren() {
		return children;
	}

	public void setChildren(List<Content> children) {
		this.children = children;
	}

	@OneToOne
	public Workspace getWorksapce() {
		return worksapce;
	}

	public void setWorksapce(Workspace worksapce) {
		this.worksapce = worksapce;
	}

	public void removeProperty(Property property) {
		if (this.properties != null)
			this.properties.remove(property);
	}
}
