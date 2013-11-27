package org.pshow.ecm.content.model.definition;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.pshow.ecm.content.exception.DataTypeUnSupportExeception;
import org.pshow.ecm.content.model.ContentData;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("data-type")
public class DataTypeDef {
	
	@XStreamAsAttribute
	private String name;
	@XStreamAsAttribute
	private String title;
	private String description;
	@XStreamAlias("java-class")
	private String javaClassName;
	
	public static enum Type {
        ANY(0), TEXT(1), CONTENT(2), INT(3), LONG(4), FLOAT(5), DOUBLE(6), DATE(7), DATETIME(8), BOOLEAN(9);

        private final int type;

        Type(int type) {
            this.type = type;
        }

        public int getActualType() {
            return this.type;
        }

        public static Type getObjectType(Serializable o) throws DataTypeUnSupportExeception {
            if (o instanceof Boolean) {
                return BOOLEAN;
            } else if (o instanceof String) {
                return TEXT;
            } else if (o instanceof ContentData) {
                return CONTENT;
            } else if (o instanceof Integer) {
                return INT;
            } else if (o instanceof Long) {
                return LONG;
            } else if (o instanceof Float) {
                return FLOAT;
            } else if (o instanceof Double) {
                return DOUBLE;
            } else if (o instanceof Date) {
                return DATE;
            } else if (o instanceof Calendar) {
                return DATETIME;
            } else if (o instanceof Serializable) {
                return ANY;
            } else {
                throw new DataTypeUnSupportExeception(String.format("Unsupport [%s] data type", o.getClass().getName()));
            }
        }

        public static Type valueOf(int value) {
            switch (value) {
                case 0:
                    return ANY;
                case 1:
                    return TEXT;
                case 2:
                    return CONTENT;
                case 3:
                    return INT;
                case 4:
                    return LONG;
                case 5:
                    return FLOAT;
                case 6:
                    return DOUBLE;
                case 7:
                    return DATE;
                case 8:
                    return DATETIME;
                case 9:
                    return BOOLEAN;
                default:
                    return null;
            }
        }
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJavaClassName() {
		return javaClassName;
	}

	public void setJavaClassName(String javaClassName) {
		this.javaClassName = javaClassName;
	}
}
