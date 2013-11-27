package org.pshow.ecm.content;

import org.pshow.ecm.content.query.Query;
import org.pshow.ecm.content.query.Result;


public interface QueryService {
	public Result query(Query queryObject);
}
