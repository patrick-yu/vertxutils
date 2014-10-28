package org.patrickyu.vertx.mongo.query;

import org.vertx.java.core.json.JsonObject;


public abstract class BaseQuery {
	abstract public JsonObject toJsonObject();
}
