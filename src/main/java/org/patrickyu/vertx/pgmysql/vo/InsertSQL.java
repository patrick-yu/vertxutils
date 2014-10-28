package org.patrickyu.vertx.pgmysql.vo;

import java.util.ArrayList;
import java.util.List;

import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

public class InsertSQL implements BaseSQL {
	private String table;
	private String[] fields;
	private List<Object[]> values;

	public InsertSQL() {

	}

	public InsertSQL(String table, String[] fields, List<Object[]> values) {
		this.table = table;
		this.fields = fields;
		this.values = values;
	}

	@Override
	public JsonObject toJsonObject() {
		JsonObject json = new JsonObject();
		json.putString("action", "insert");
		json.putString("table", this.table);
		if (this.fields == null)
			this.fields = new String[]{};
		json.putArray("fields", new JsonArray(this.fields));
		if (this.values == null)
			this.values = new ArrayList<Object[]>();
		JsonArray values = new JsonArray();
		for (Object[] record : this.values) {
			values.add(new JsonArray(record));
		}
		json.putArray("values", values);
		return json;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public List<Object[]> getValues() {
		return values;
	}

	public void setValues(List<Object[]> values) {
		this.values = values;
	}

}
