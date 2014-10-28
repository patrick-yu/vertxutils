package org.patrickyu.vertx.pgmysql;

import java.util.ArrayList;
import java.util.List;

import org.patrickyu.vertx.pgmysql.handler.InsertHandler;
import org.patrickyu.vertx.pgmysql.handler.PreparedHandler;
import org.patrickyu.vertx.pgmysql.handler.TransactionHandler;
import org.patrickyu.vertx.pgmysql.vo.BaseSQL;
import org.patrickyu.vertx.pgmysql.vo.InsertSQL;
import org.patrickyu.vertx.pgmysql.vo.PreparedSQL;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Container;

public class PgMySqlUtils {
	private Container container = null;
	private Vertx vertx = null;
	private String eventBusAddr = null;

	private PgMySqlUtils() {

	}
	private static class LazyHolder {
		private static final PgMySqlUtils INSTANCE = new PgMySqlUtils();
	}

	public static PgMySqlUtils getInstance() {
		return LazyHolder.INSTANCE;
	}

	public void load(Container container, Vertx vertx, PgMySqlConfig config) {
		this.container = container;
		this.vertx = vertx;
		this.eventBusAddr = config.getAddress();

		this.container.deployModule(config.getModuleName(), config.toJsonObject());
	}

	public void batchInsert(InsertSQL sql) {
		this.vertx.eventBus().send(this.eventBusAddr, sql.toJsonObject());
	}

	public void batchInsert(InsertSQL sql, InsertHandler handler) {
		this.vertx.eventBus().send(this.eventBusAddr, sql.toJsonObject(), handler);
	}

	public void execute(PreparedSQL sql) {
		this.vertx.eventBus().send(this.eventBusAddr, sql.toJsonObject());
	}

	public void execute(PreparedSQL sql, PreparedHandler handler) {
		this.vertx.eventBus().send(this.eventBusAddr, sql.toJsonObject(), handler);
	}

	public void transaction(List<BaseSQL> sqls) {
		transaction(sqls, null);
	}

	public void transaction(List<BaseSQL> sqls, TransactionHandler handler) {
		if (sqls == null)
			sqls = new ArrayList<BaseSQL>();
		JsonObject json = new JsonObject();
		json.putString("action", "transaction");
		JsonArray statements = new JsonArray();
		for (BaseSQL sql: sqls) {
			statements.add(sql.toJsonObject());
		}
		json.putArray("statements", statements);
		if (handler == null)
			this.vertx.eventBus().send(this.eventBusAddr, json);
		else
			this.vertx.eventBus().send(this.eventBusAddr, json, handler);
	}
}
