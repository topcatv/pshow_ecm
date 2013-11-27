package org.pshow.ecm.content;

import java.util.List;
import java.util.Map;

import org.pshow.ecm.content.model.Content;
import org.pshow.ecm.content.model.ContentFacet;
import org.pshow.ecm.content.model.ContentType;
import org.pshow.ecm.content.model.PropertyValue;
import org.pshow.ecm.content.model.Workspace;

public interface ContentService {
	public String getRoot(String workspace);
	public Map<String, PropertyValue> getProperties(String contentId);
	public PropertyValue getProperty(String contentId, String name);
	public void setProperty(String contentId, String name, PropertyValue value);
	public void setProperites(String contentId, Map<String, PropertyValue> values);
	public void addProperty(String contentId, String name, PropertyValue value);
	public void removeProperty(String contentId, String name);
	public ContentType getType(String contentId);
	public List<ContentFacet> getFacets(String contentId);
	public List<Content> getChild(String contentId);
	public String createContent(String type, String parentId, String name);
	public String createContent(String type, String parentId, String name, Map<String, PropertyValue> properties);
	public void removeContent(String contentId);
	public Workspace createWorkspace(String name);
	public Workspace findWorkspace(String name);
}
