package org.patrickyu.vertx.daohandler;

import org.vertx.java.core.Handler;

public abstract class BooleanDaoHandler extends BaseDaoHandler {
	public BooleanDaoHandler(Handler<Object> next) {
		super(next);
	}

	public abstract void onSuccess(boolean result);
}
