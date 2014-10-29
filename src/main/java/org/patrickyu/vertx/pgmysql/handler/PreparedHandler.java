package org.patrickyu.vertx.pgmysql.handler;

import org.patrickyu.vertx.daohandler.BaseDaoHandler;
import org.patrickyu.vertx.handler.BaseDBHandler;
import org.patrickyu.vertx.pgmysql.ResultSet;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

public abstract class PreparedHandler extends BaseDBHandler {

	public PreparedHandler(BaseDaoHandler callbackHandler) {
		super(callbackHandler);
	}

	@Override
	protected void doHandle(Message<JsonObject> value) {
		JsonObject body = value.body();
		if (body.getString("status").equals("ok")) {
			int rows = body.getInteger("rows");
			JsonArray fields = body.getArray("fields");
			JsonArray records = body.getArray("results");
			ResultSet rs = new ResultSet(fields, records);
			this.onSuccess(rows, rs);
		} else {
			String className = body.getString("error");
			String message = body.getString("message");
			Throwable e = this.getThrowableInstance(className, message);
			onException(e);
		}
	}

	public abstract void onSuccess(int affectedRows, ResultSet resultSet);
}
