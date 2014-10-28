package org.patrickyu.vertx.mongo.query;

import org.vertx.java.core.json.JsonObject;

public class FindQuery extends BaseQuery {
	private String collection;
	private JsonObject matcher;
	private JsonObject keys;
	private JsonObject sort;
	private Number skip;
	private Number limit;
	private Number timeout;

	public FindQuery() {
	}

	public FindQuery(String collection) {
		this.collection = collection;
	}

	public FindQuery(String collection, JsonObject matcher) {
		this.collection = collection;
		this.matcher = matcher;
	}

	public FindQuery(String collection, JsonObject matcher, JsonObject keys) {
		this.collection = collection;
		this.matcher = matcher;
		this.keys = keys;
	}

	public String getCollection() {
		return this.collection;
	}
	public FindQuery collection(String collection) {
		this.collection = collection;
		return this;
	}

	public JsonObject getMatcher() {
		return matcher;
	}
	public FindQuery matcher(JsonObject matcher) {
		this.matcher = matcher;
		return this;
	}
	public JsonObject getSort() {
		return sort;
	}
	public FindQuery sort(JsonObject sort) {
		this.sort = sort;
		return this;
	}
	public JsonObject getKeys() {
		return keys;
	}
	public FindQuery keys(JsonObject keys) {
		this.keys = keys;
		return this;
	}
	public Number getSkip() {
		return skip;
	}
	public FindQuery skip(Number skip) {
		this.skip = skip;
		return this;
	}
	public Number getLimit() {
		return limit;
	}
	public FindQuery limit(Number limit) {
		this.limit = limit;
		return this;
	}
	public Number getTimeout() {
		return timeout;
	}
	public FindQuery timeout(Number timeout) {
		this.timeout = timeout;
		return this;
	}

	@Override
	public JsonObject toJsonObject() {
		JsonObject json = new JsonObject()
			.putString("action", "find")
			.putString("collection", this.collection);
		if (this.matcher != null)
			json.putObject("matcher", this.matcher);
		if (this.keys != null)
			json.putObject("keys", this.keys);
		if (this.sort != null)
			json.putObject("sort", this.sort);
		if (this.skip != null)
			json.putNumber("skip", this.skip);
		if (this.limit != null)
			json.putNumber("limit", this.limit);
		if (this.timeout != null)
			json.putNumber("timeout", this.timeout);
		return json;
	}
}
