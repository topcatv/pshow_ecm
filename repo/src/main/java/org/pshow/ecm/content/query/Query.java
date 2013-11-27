package org.pshow.ecm.content.query;

import java.util.List;

public interface Query {
	public Query from(String typeName);
	public Query select(List<String> fields);
	public Condition where();
	public Query orderBy(List<String> fileds, boolean isAsc);
	public Query setPageSize(int ps);
	public Query setStartPage(int sp);
}
