package org.pshow.ecm.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ho.yaml.Yaml;
import org.pshow.ecm.content.metadata.ContentSchemaHolder;
import org.pshow.ecm.content.model.PropertyValue;
import org.pshow.ecm.content.model.definition.PropertyDef;
import org.pshow.ecm.content.model.definition.TypeDef;
import org.springframework.core.io.ClassPathResource;

public class TestDataLoader {

	@SuppressWarnings("rawtypes")
	public static Map<String, PropertyValue> loadData(String name,
			ContentSchemaHolder csh) {
		ClassPathResource resource = new ClassPathResource("test_data.yml");
		try {
			HashMap object = Yaml.loadType(resource.getInputStream(),
					HashMap.class);
			HashMap prop = (HashMap) object.get(name);
			return convert(prop, csh);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String, PropertyValue> convert(HashMap prop,
			ContentSchemaHolder csh) {
		HashMap<String, PropertyValue> re_obj = new HashMap<String, PropertyValue>(
				prop.size());
		String ct = (String) prop.get("content_type");
		prop.remove("content_type");
		TypeDef typeDef = csh.getContentType(ct.replace("\\", ""));
		List<PropertyDef> proDef = typeDef.getProperties();
		Map<String, String> proDefMap = new HashMap<String, String>(
				proDef.size());
		for (PropertyDef propertyDef : proDef) {
			proDefMap.put(propertyDef.getName(), propertyDef.getDataTypeName());
		}
		Iterator iterator = prop.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Serializable> kv = (Entry<String, Serializable>) iterator
					.next();
			String pro_name = kv.getKey().replace("\\", "");
			String pro_type = proDefMap.get(pro_name);
			Serializable pro_value = convertValue(kv.getValue(), pro_type);
			re_obj.put(pro_name, new PropertyValue(pro_value));
		}
		return re_obj;
	}

	private static Serializable convertValue(Serializable value, String pro_type) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if ("d:date".equals(pro_type)) {
			try {
				return simpleDateFormat.parse((String) value);
			} catch (ParseException e) {
				e.printStackTrace();
				return value;
			}
		}
		return value;
	}

}
