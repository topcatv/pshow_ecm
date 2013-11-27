package org.pshow.ecm.content.query;


public interface Condition {
	public Condition and(Operator operator);
	public Condition or(Operator operator);
	public Condition not(Operator operator);
}
