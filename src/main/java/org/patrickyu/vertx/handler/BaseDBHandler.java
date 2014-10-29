package org.patrickyu.vertx.handler;

import org.patrickyu.util.ExceptionUtils;
import org.patrickyu.vertx.daohandler.BaseDaoHandler;
import org.patrickyu.vertx.handler.VertxHandler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;

public abstract class BaseDBHandler extends VertxHandler<Message<JsonObject>> {

	private BaseDaoHandler callbackHandler;
	public BaseDBHandler(BaseDaoHandler callbackHandler) {
		this.callbackHandler = callbackHandler;
	}
	@Override
	protected void onException(Throwable e) {
		Logger logger = getLogger();
		if (logger != null) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		if (this.callbackHandler != null)
			this.callbackHandler.onError(e);
	}

	public abstract Logger getLogger();
}
