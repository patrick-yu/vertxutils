package org.patrickyu.vertx.mongo.handler;

import org.patrickyu.vertx.http.VertxHandler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

public abstract class DeleteHandler extends VertxHandler<Message<JsonObject>> {

	@Override
	public void doHandle(Message<JsonObject> value) {
		JsonObject body = value.body();
		if (body.getString("status").equals("ok"))
			this.onSuccess(body.getNumber("number"));
		else
			this.onError(body.getString("message"), body.getString("error"));
	}

	@Override
	public void onException(Throwable e) {
		onError(e.getMessage(), e.getClass().getCanonicalName());
	}

	public abstract void onSuccess(Number number);
	public abstract void onError(String message, String error);
}
