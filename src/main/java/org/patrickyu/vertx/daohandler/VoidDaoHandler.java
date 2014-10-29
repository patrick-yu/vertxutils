package org.patrickyu.vertx.daohandler;

import org.vertx.java.core.Handler;



public abstract class VoidDaoHandler extends BaseDaoHandler {
	public VoidDaoHandler(Handler<Object> next) {
		super(next);
	}

	public abstract void onSuccess();
}
