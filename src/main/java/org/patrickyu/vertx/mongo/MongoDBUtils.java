package org.patrickyu.vertx.mongo;

import org.patrickyu.vertx.mongo.handler.CountHandler;
import org.patrickyu.vertx.mongo.handler.DeleteHandler;
import org.patrickyu.vertx.mongo.handler.FindHandler;
import org.patrickyu.vertx.mongo.handler.FindOneHandler;
import org.patrickyu.vertx.mongo.handler.SaveHandler;
import org.patrickyu.vertx.mongo.handler.UpdateHandler;
import org.patrickyu.vertx.mongo.query.CountQuery;
import org.patrickyu.vertx.mongo.query.DeleteQuery;
import org.patrickyu.vertx.mongo.query.FindOneQuery;
import org.patrickyu.vertx.mongo.query.FindQuery;
import org.patrickyu.vertx.mongo.query.SaveQuery;
import org.patrickyu.vertx.mongo.query.UpdateQuery;
import org.vertx.java.core.Vertx;
import org.vertx.java.platform.Container;

public class MongoDBUtils {
	private Container container = null;
	private Vertx vertx = null;
	private String eventBusAddr = null;

	private MongoDBUtils() {

	}
	private static class LazyHolder {
		private static final MongoDBUtils INSTANCE = new MongoDBUtils();
	}

	public static MongoDBUtils getInstance() {
		return LazyHolder.INSTANCE;
	}

	public void load(Container container, Vertx vertx, MongoDBConfig config) {
		this.container = container;
		this.vertx = vertx;
		this.eventBusAddr = config.getAddress();

		this.container.deployModule(config.getModule_name(), config.toJsonObject());
	}

	public void save(SaveQuery query, SaveHandler handler) {
		this.vertx.eventBus().send(this.eventBusAddr, query.toJsonObject(), handler);
	}

	public void count(CountQuery query, CountHandler handler ) {
		this.vertx.eventBus().send(this.eventBusAddr, query.toJsonObject(), handler);
	}

	public void delete(DeleteQuery query, DeleteHandler handler ) {
		this.vertx.eventBus().send(this.eventBusAddr, query.toJsonObject(), handler);
	}

	public void find(FindQuery query, FindHandler handler ) {
		this.vertx.eventBus().send(this.eventBusAddr, query.toJsonObject(), handler);
	}

	public void findOne(FindOneQuery query, FindOneHandler handler ) {
		this.vertx.eventBus().send(this.eventBusAddr, query.toJsonObject(), handler);
	}

	public void update(UpdateQuery query, UpdateHandler handler ) {
		this.vertx.eventBus().send(this.eventBusAddr, query.toJsonObject(), handler);
	}
}
