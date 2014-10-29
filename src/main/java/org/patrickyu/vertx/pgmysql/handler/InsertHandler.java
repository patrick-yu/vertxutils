package org.patrickyu.vertx.pgmysql.handler;

import org.patrickyu.vertx.daohandler.BaseDaoHandler;
import org.patrickyu.vertx.handler.BaseDBHandler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

public abstract class InsertHandler extends BaseDBHandler {


	public InsertHandler(BaseDaoHandler callbackHandler) {
		super(callbackHandler);
	}

	@Override
	public void doHandle(Message<JsonObject> value) {
		//{"message":"UPDATE 1","rows":1,"fields":[],"results":[],"status":"ok"}
		JsonObject body = value.body();
		if (body.getString("status").equals("ok"))
			this.onSuccess(body.getInteger("rows"));
		else {
			String className = body.getString("error");
			String message = body.getString("message");
			Throwable e = this.getThrowableInstance(className, message);
			onException(e);
		}
	}

	public abstract void onSuccess(int updatedRows);
}
