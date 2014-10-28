package org.patrickyu.vertx.mongo.query;

import org.vertx.java.core.json.JsonObject;

public class FindOneQuery extends BaseQuery {
	public String collection;
	public JsonObject matcher;
	public JsonObject keys;

	public FindOneQuery() {
	}

	public FindOneQuery(String collection) {
		this.collection = collection;
	}

	public FindOneQuery(String collection, JsonObject matcher) {
		this.collection = collection;
		this.matcher = matcher;
	}

	public FindOneQuery(String collection, JsonObject matcher, JsonObject keys) {
		this.collection = collection;
		this.matcher = matcher;
		this.keys = keys;
	}

	public String getCollection() {
		return this.collection;
	}
	public FindOneQuery collection(String collection) {
		this.collection = collection;
		return this;
	}

	public JsonObject getMatcher() {
		return matcher;
	}
	public FindOneQuery matcher(JsonObject matcher) {
		this.matcher = matcher;
		return this;
	}
	public JsonObject getKeys() {
		return keys;
	}
	public FindOneQuery keys(JsonObject keys) {
		this.keys = keys;
		return this;
	}

	@Override
	public JsonObject toJsonObject() {
		JsonObject json = new JsonObject()
			.putString("action", "findone")
			.putString("collection", this.collection);
		if (this.matcher != null)
			json.putObject("matcher", this.matcher);
		return json;
	}
}
