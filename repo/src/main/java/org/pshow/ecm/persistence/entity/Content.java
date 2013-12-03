package org.pshow.ecm.persistence.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "ps_content", uniqueConstraints=@UniqueConstraint(columnNames={"name", "parent_id"}))
public class Content extends IdEntity {

	private String uuid;
	private String name;
	private boolean checkouted;
	private boolean folder;
	private boolean versioned;
	private String contentType;

	private List<Property> properties;

	private VersionHistory versionHistory;

	private Content parent;

	private List<Content> children;

	@PrePersist
	public void prePersist() {
		this.uuid = UUID.randomUUID().toString();
	}

	@NotBlank
	@Column(unique=true)
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@NotBlank
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

	@OneToMany(mappedBy = "id")
	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public boolean isVersioned() {
		return versioned;
	}

	public void setVersioned(boolean versioned) {
		this.versioned = versioned;
	}

	@OneToOne
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

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id", nullable=true)
	public Content getParent() {
		return parent;
	}

	public void setParent(Content parent) {
		this.parent = parent;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", fetch = FetchType.EAGER)
	public List<Content> getChildren() {
		return children;
	}

	public void setChildren(List<Content> children) {
		this.children = children;
	}
}
