package org.patrickyu.vertx.pgmysql.handler;

import org.patrickyu.util.ExceptionUtils;
import org.patrickyu.vertx.http.VertxHandler;
import org.patrickyu.vertx.pgmysql.ResultSet;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;

public abstract class PreparedHandler extends VertxHandler<Message<JsonObject>> {

	@Override
	public void doHandle(Message<JsonObject> value) {
		JsonObject body = value.body();
		if (body.getString("status").equals("ok")) {
			int rows = body.getInteger("rows");
			JsonArray fields = body.getArray("fields");
			JsonArray records = body.getArray("results");
			ResultSet rs = new ResultSet(fields, records);
			this.onSuccess(rows, rs);
		} else {
			Logger logger = getLogger();
			if (logger != null) {
				logger.error(body.getString("error") + "\n" + body.getString("message"));
			}
			this.onError(body.getString("message"), body.getString("error"));
		}
	}

	@Override
	public void onException(Throwable e) {
		Logger logger = getLogger();
		if (logger != null) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		onError(e.getMessage(), e.getClass().getCanonicalName());
	}

	public abstract Logger getLogger();
	public abstract void onSuccess(int affectedRows, ResultSet resultSet);
	public abstract void onError(String message, String error);
}
