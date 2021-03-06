package org.patrickyu.vertx.mongo.handler;

import org.patrickyu.vertx.daohandler.BaseDaoHandler;
import org.patrickyu.vertx.handler.BaseDBHandler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

public abstract class SaveHandler extends BaseDBHandler {

	public SaveHandler(BaseDaoHandler callbackHandler) {
		super(callbackHandler);
	}

	@Override
	public void doHandle(Message<JsonObject> value) {
		JsonObject body = value.body();
		if (body.getString("status").equals("ok")) {
			this.onSuccess(body.getString("_id"));
		}
		else {
			String className = body.getString("error");
			String message = body.getString("message");
			Throwable e = this.getThrowableInstance(className, message);
			onException(e);
		}
	}

	public abstract void onSuccess(String _id);
}
