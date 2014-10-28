package org.patrickyu.vertx.mongo.query;

import org.vertx.java.core.json.JsonObject;

public class DeleteQuery extends BaseQuery {
	private String collection;
	private JsonObject matcher;

	public DeleteQuery() {
	}

	public DeleteQuery(String collection) {
		this.collection = collection;
	}

	public DeleteQuery(String collection, JsonObject matcher) {
		this.collection = collection;
		this.matcher = matcher;
	}

	public String getCollection() {
		return this.collection;
	}
	public DeleteQuery collection(String collection) {
		this.collection = collection;
		return this;
	}

	public JsonObject getMatcher() {
		return matcher;
	}

	public DeleteQuery matcher(JsonObject matcher) {
		this.matcher = matcher;
		return this;
	}

	@Override
	public JsonObject toJsonObject() {
		JsonObject json = new JsonObject()
			.putString("action", "delete")
			.putString("collection", this.collection);
		if (this.matcher != null)
			json.putObject("matcher", this.matcher);
		return json;
	}


}
