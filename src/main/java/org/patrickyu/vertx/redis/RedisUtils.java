package org.patrickyu.vertx.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.patrickyu.util.ClassUtils;
import org.patrickyu.vertx.redis.handler.RedisHandler;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Container;

public class RedisUtils {
	private Container container = null;
	private Vertx vertx = null;
	private String eventBusAddr = null;

	private RedisUtils() {

	}
	private static class LazyHolder {
		private static final RedisUtils INSTANCE = new RedisUtils();
	}

	public static RedisUtils getInstance() {
		return LazyHolder.INSTANCE;
	}

	public void load(Container container, Vertx vertx, RedisConfig config) {
		this.container = container;
		this.vertx = vertx;
		this.eventBusAddr = config.getAddress();

		this.container.deployModule(config.getModule_name(), config.toJsonObject());
	}

	public void load(Container container, Vertx vertx, RedisConfig config, AsyncResultHandler<String> handler) {
		this.container = container;
		this.vertx = vertx;
		this.eventBusAddr = config.getAddress();

		this.container.deployModule(config.getModule_name(), config.toJsonObject(), handler);
	}

	public JsonObject json(String command, Object... args){
		JsonObject json = new JsonObject();
		json.putString("command", command);
		if (args != null) {
			JsonArray jsonArgs = new JsonArray();
			for (Object o: args) {
				jsonArgs.add(o);
			}
			json.putArray("args", jsonArgs);
		}
		return json;
	}

	public Object[] args(String key, Object obj) {
		JsonObject json = ClassUtils.objectToJsonObject(obj);
		Set<String> fields = json.getFieldNames();
		List<Object> list = new ArrayList<Object>();
		list.add(key);
		for (String field: fields) {
			list.add(field);
			list.add(json.getValue(field));
		}
		return list.toArray();
	}

	public void execute(JsonObject query) {
		this.vertx.eventBus().send(this.eventBusAddr, query);
	}

	public void execute(JsonObject query, RedisHandler  handler) {
		this.vertx.eventBus().send(this.eventBusAddr, query, handler);
	}

	/* Command 별 result
	 * -----------------
	 * SET
	 * 	{"value":"OK","status":"ok"}
	 * GET
	 * 	{"value":"gmtsoft","status":"ok"}
	 * HGETALL
	 * 	{"value":{"id":"user1","name":"yym1"},"status":"ok"}
	 * HGET
	 *	{"value":"user1","status":"ok"}
	 * HMGET
	 * 	{"value":["user1","yym1"],"status":"ok"}
	 * HMSET
	 * 	{"value":"OK","status":"ok"}
	 */

}
