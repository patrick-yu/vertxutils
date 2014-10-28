package org.patrickyu.vertx.pgmysql.handler;

import org.patrickyu.util.ExceptionUtils;
import org.patrickyu.vertx.http.VertxHandler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;

public abstract class InsertHandler extends VertxHandler<Message<JsonObject>> {

	@Override
	public void doHandle(Message<JsonObject> value) {
		//{"message":"UPDATE 1","rows":1,"fields":[],"results":[],"status":"ok"}
		JsonObject body = value.body();
		if (body.getString("status").equals("ok"))
			this.onSuccess(body.getInteger("rows"));
		else {
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
	public abstract void onSuccess(int updatedRows);
	public abstract void onError(String message, String error);
}
