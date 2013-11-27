package org.pshow.ecm.content.metadata;

import java.util.List;

import org.pshow.ecm.content.model.definition.ConstraintDef;
import org.pshow.ecm.content.model.definition.FacetDef;
import org.pshow.ecm.content.model.definition.PSDef;
import org.pshow.ecm.content.model.definition.NamespaceDef;
import org.pshow.ecm.content.model.definition.TypeDef;


public interface ContentSchemaHolder {
	public void registContentSchema(PSDef schema);
	public void registContentSchemas(List<PSDef> models);
	public boolean hasRegisteredObject(String name);
	public <E> E getRegisteredObject(String name, E e);
	public boolean hasNamespace(String name);
	public NamespaceDef getNamespaceByUri(String uri);
	public NamespaceDef getNamespaceByPrefix(String prefix);
	public List<NamespaceDef> getAllNamespace();
	public boolean hasContentType(String name);
	public TypeDef getContentType(String name);
	public List<TypeDef> getAllContentType();
	public boolean hasFacet(String name);
	public FacetDef getFacet(String name);
	public List<FacetDef> getAllFacet();
	public boolean hasConstraint(String name);
	public ConstraintDef getConstraint(String name);
	public List<ConstraintDef> getAllConstraint();
}
