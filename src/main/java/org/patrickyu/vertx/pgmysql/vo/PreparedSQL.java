package org.patrickyu.vertx.pgmysql.vo;

import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

public class PreparedSQL implements BaseSQL {
	private String statement;
	private Object[] values;

	public PreparedSQL() {

	}

	public PreparedSQL(String statement, Object[] values) {
		this.statement = statement;
		this.values = values;
	}
	@Override
	public JsonObject toJsonObject() {
		JsonObject json = new JsonObject();
		json.putString("action", "prepared");
		json.putString("statement", this.statement);
		json.putArray("values", new JsonArray(this.values));
		return json;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}

}
