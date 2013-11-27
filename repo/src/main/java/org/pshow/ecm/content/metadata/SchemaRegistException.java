package org.pshow.ecm.content.metadata;

public class SchemaRegistException extends Exception {

	private static final long serialVersionUID = -2753568154131448768L;

	public SchemaRegistException(String message, Throwable cause) {
		super(message, cause);
	}

	public SchemaRegistException(String message) {
		super(message);
	}

	public SchemaRegistException(Throwable cause) {
		super(cause);
	}
}
