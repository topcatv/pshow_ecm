package org.pshow.ecm.content.model.definition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.pshow.ecm.utils.DateConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("model")
public class PSDef {
	@XStreamAsAttribute
	private String name;
	private String description;
	@XStreamAsAttribute
	private String author;
	@XStreamConverter(DateConverter.class)
	private Date published;
	@XStreamAsAttribute
	private String version;
	private List<NamespaceDef> namespaces = new ArrayList<NamespaceDef>();
	private List<NamespaceDef> imports = new ArrayList<NamespaceDef>();
	@XStreamAlias("data-types")
	private List<DataTypeDef> dataTypes = new ArrayList<DataTypeDef>();
	private List<TypeDef> types = new ArrayList<TypeDef>();
	private List<FacetDef> facets = new ArrayList<FacetDef>();
	private List<ConstraintDef> constraints = new ArrayList<ConstraintDef>();

	public String getName() {
		return name;

	}

	public void setName(String name) {

	}

	public String getDescription() {
		return description;

	}

	public void setDescription(String description) {

	}

	public String getAuthor() {
		return author;

	}

	public void setAuthor(String author) {

	}

	public Date getPublished() {
		return published;

	}

	public void setPublished(Date published) {

	}

	public String getVersion() {
		return version;

	}

	public void setVersion(String version) {

	}

	public NamespaceDef createNamespace(String uri, String prefix) {
		NamespaceDef psNamespace = new NamespaceDef();
		psNamespace.setPrefix(prefix);
		psNamespace.setUri(uri);
		namespaces.add(psNamespace);
		return psNamespace;

	}

	public void removeNamespace(String uri) {
		for (NamespaceDef psNamespace : namespaces) {
			if(StringUtils.equals(psNamespace.getUri(), uri)){
				namespaces.remove(psNamespace);
			}
		}
	}

	public List<NamespaceDef> getNamespaces() {
		return namespaces;

	}

	public NamespaceDef getNamespace(String uri) {
		for (NamespaceDef namespace : namespaces) {
			if(StringUtils.equals(uri, namespace.getUri())){
				return namespace;
			}
		}
		return null;

	}

	public NamespaceDef createImport(String uri, String prefix) {
		NamespaceDef psNamespace = new NamespaceDef();
		psNamespace.setPrefix(prefix);
		psNamespace.setUri(uri);
		imports.add(psNamespace);
		return psNamespace;

	}

	public void removeImport(String uri) {
		for (NamespaceDef namespace : imports) {
			if(StringUtils.equals(uri, namespace.getUri())){
				imports.remove(namespace);
			}
		}
	}

	public List<NamespaceDef> getImports() {
		return imports;

	}

	public NamespaceDef getImport(String uri) {
		for (NamespaceDef namespace : imports) {
			if(StringUtils.equals(uri, namespace.getUri())){
				return namespace;
			}
		}
		return null;

	}

	public List<ConstraintDef> getConstraints() {
		return constraints;

	}

	public ConstraintDef getConstraint(String name) {
		for (ConstraintDef constraint : constraints) {
			if(StringUtils.equals(name, constraint.getName())){
				return constraint;
			}
		}
		return null;

	}

	public ConstraintDef createConstraint(String name, String type) {
		ConstraintDef constraintModel = new ConstraintDef();
		constraintModel.setName(name);
		constraintModel.setJavaClassName(type);
		constraints.add(constraintModel);
		return constraintModel;

	}

	public void removeConstraint(String name) {
		for (ConstraintDef constraint : constraints) {
			if(StringUtils.equals(name, constraint.getName())){
				constraints.remove(constraint);
			}
		}
	}

	public DataTypeDef createPropertyType(String name) {
		DataTypeDef dataType = new DataTypeDef();
		dataType.setName(name);
		dataTypes.add(dataType);
		return dataType;

	}

	public void removePropertyType(String name) {
		for (DataTypeDef dataType : dataTypes) {
			if(StringUtils.equals(name, dataType.getName())){
				dataTypes.remove(dataType);
			}
		}
	}

	public List<DataTypeDef> getPropertyTypes() {
		return dataTypes;

	}

	public DataTypeDef getPropertyType(String name) {
		for (DataTypeDef dataType : dataTypes) {
			if(StringUtils.equals(name, dataType.getName())){
				return dataType;
			}
		}
		return null;

	}

	public TypeDef createType(String name) {
		TypeDef typeModel = new TypeDef();
		typeModel.setName(name);
		types.add(typeModel);
		return typeModel;

	}

	public void removeType(String name) {
		for (TypeDef type : types) {
			if (StringUtils.equals(type.getName(), name)) {
				types.remove(type);
			}
		}
	}

	public List<TypeDef> getTypes() {
		return types;

	}

	public TypeDef getType(String name) {
		for (TypeDef type : types) {
			if(StringUtils.equals(name, type.getName())){
				return type;
			}
		}
		return null;

	}

	public FacetDef createFacet(String name) {
		FacetDef facetModel = new FacetDef();
		facetModel.setName(name);
		facets.add(facetModel);
		return facetModel;

	}

	public void removeFacet(String name) {
		for (FacetDef facet : facets) {
			if(StringUtils.equals(name, facet.getName())){
				facets.remove(facet);
			}
		}
	}

	public List<FacetDef> getFacets() {
		return facets;

	}

	public FacetDef getFacet(String name) {
		for (FacetDef facet : facets) {
			if(StringUtils.equals(name, facet.getName())){
				return facet;
			}
		}
		return null;
	}
}
