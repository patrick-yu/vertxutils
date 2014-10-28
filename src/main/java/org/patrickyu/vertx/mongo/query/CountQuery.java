package org.patrickyu.vertx.mongo.query;

import org.vertx.java.core.json.JsonObject;

public class CountQuery extends BaseQuery {
	private String collection;
	private JsonObject matcher;

	public CountQuery() {
	}

	public CountQuery(String collection) {
		this.collection = collection;
	}

	public CountQuery(String collection, JsonObject matcher) {
		this.collection = collection;
		this.matcher = matcher;
	}

	public String getCollection() {
		return this.collection;
	}
	public CountQuery collection(String collection) {
		this.collection = collection;
		return this;
	}
	public JsonObject getMatcher() {
		return matcher;
	}

	public CountQuery matcher(JsonObject matcher) {
		this.matcher = matcher;
		return this;
	}

	public JsonObject toJsonObject() {
		JsonObject json = new JsonObject()
			.putString("action", "count")
			.putString("collection", this.collection);
		if (this.matcher != null)
			json.putObject("matcher", this.matcher);
		return json;
	}
}
