package org.patrickyu.vertx.mongo.query;

import org.vertx.java.core.json.JsonObject;

public class SaveQuery extends BaseQuery {
	private String collection;
	private JsonObject document;

	public SaveQuery() {
	}

	public SaveQuery(String collection) {
		this.collection = collection;
	}

	public String getCollection() {
		return this.collection;
	}
	public SaveQuery collection(String collection) {
		this.collection = collection;
		return this;
	}

	public JsonObject getDocument() {
		return document;
	}

	public SaveQuery document(JsonObject document) {
		this.document = document;
		return this;
	}

	@Override
	public JsonObject toJsonObject() {
		JsonObject json = new JsonObject()
			.putString("action", "save")
			.putString("collection", this.collection);
		if (this.document != null)
			json.putObject("document", this.document);
		return json;

	}

}
