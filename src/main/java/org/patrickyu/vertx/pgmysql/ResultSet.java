package org.patrickyu.vertx.pgmysql;

import java.util.ArrayList;
import java.util.List;

import org.patrickyu.util.ClassUtils;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

public class ResultSet {
	private int row;
	private List<String> fields;
	private JsonArray records;
	public ResultSet(JsonArray fields, JsonArray records) {
		this.records = records;
		Object[] fl = fields.toArray();
		this.fields = new ArrayList<String>();
		for (Object v: fl) {
			String sv = (String)v;
			this.fields.add(sv.toLowerCase());
		}
		this.row = -1;
	}

	public List<String> getFields() {
		return fields;
	}

	public int size() {
		return records.size();
	}

	public void first() {
		this.row = -1;
	}

	public boolean next() {
		int cnt = this.records.size()-1;
		if (this.records.size() == 0)
			return false;
		if (row >= cnt)
			return false;
		this.row++;
		return true;
	}
	public Object get(String field) {
		if (this.row < 0 || this.row >= size())
			return null;

		int fidx = this.fields.indexOf(field.toLowerCase());
		if (fidx < 0)
			return null;
		JsonArray record = this.records.get(this.row);
		return record.get(fidx);
	}

	public JsonObject getRecord() {
		if (this.row < 0 || this.row >= size())
			return null;

		JsonObject json = new JsonObject();
		for (String fieldName: fields) {
			json.putValue(fieldName, this.get(fieldName));
		}

		return json;
	}

	public <T> T currentRecordToObject(Class<T> cls) {
		JsonObject json = getRecord();
		if (json == null)
			return null;
		return ClassUtils.jsonObjectToObject(json, cls);
	}

	public String getString(String field) {
		return String.valueOf(get(field));
	}

	public Integer getInteger(String field) {
		return Integer.valueOf(getString(field));
	}

	public Long getLong(String field) {
		return Long.valueOf(getString(field));
	}

	public Double getDouble(String field) {
		return Double.valueOf(getString(field));
	}

	public Boolean getBoolean(String field) {
		return Boolean.parseBoolean(getString(field));
	}

	public JsonArray toJsonArray() {
		int row = this.row;
		this.row = -1;
		JsonArray arr = new JsonArray();
		while( this.next() ) {
			JsonObject json = new JsonObject();
			for (String fieldName: fields) {
				json.putString(fieldName, this.getString(fieldName));
			}
			arr.add(json);
		}
		this.row = row;
		return arr;
	}

	public <T> List<T> toObjectList(Class<T> cls) {
		int row = this.row;
		this.row = -1;
		List<T> list = new ArrayList<T>();
		while( this.next() ) {
			JsonObject json = new JsonObject();
			for (String fieldName: fields) {
				json.putString(fieldName, this.getString(fieldName));
			}
			T obj = ClassUtils.jsonObjectToObject(json, cls);
			list.add(obj);
		}
		this.row = row;
		return list;
	}
}
