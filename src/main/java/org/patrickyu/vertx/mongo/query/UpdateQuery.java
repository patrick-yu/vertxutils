package org.patrickyu.vertx.mongo.query;

import org.vertx.java.core.json.JsonObject;

public class UpdateQuery extends BaseQuery {
	private String collection;
	private JsonObject criteria;
	private JsonObject objNew;
	private Boolean upsert;
	private Boolean multi;

	public UpdateQuery() {
	}

	public UpdateQuery(String collection) {
		this.collection = collection;
	}

	public UpdateQuery(String collection, JsonObject criteria, JsonObject objNew) {
		this.collection = collection;
		this.criteria = criteria;
		this.objNew = objNew;
	}

	public String getCollection() {
		return this.collection;
	}
	public UpdateQuery collection(String collection) {
		this.collection = collection;
		return this;
	}
	public JsonObject getCriteria() {
		return criteria;
	}
	public UpdateQuery criteria(JsonObject criteria) {
		this.criteria = criteria;
		return this;
	}
	public JsonObject getObjNew() {
		return objNew;
	}
	public UpdateQuery objNew(JsonObject objNew) {
		this.objNew = objNew;
		return this;
	}
	public Boolean getUpsert() {
		return upsert;
	}
	public UpdateQuery upsert(Boolean upsert) {
		this.upsert = upsert;
		return this;
	}
	public Boolean getMulti() {
		return multi;
	}
	public UpdateQuery multi(Boolean multi) {
		this.multi = multi;
		return this;
	}

	@Override
	public JsonObject toJsonObject() {
		JsonObject json = new JsonObject()
			.putString("action", "update")
			.putString("collection", this.collection);
		if (this.criteria != null)
			json.putObject("criteria", this.criteria);
		if (this.objNew != null)
			json.putObject("objNew", this.objNew);
		if (this.upsert != null)
			json.putBoolean("upsert", this.upsert);
		if (this.multi != null)
			json.putBoolean("multi", this.multi);
		return json;

	}
}
