package org.patrickyu.vertx.daohandler;

import org.vertx.java.core.Handler;



public abstract class ObjectDaoHandler<T> extends BaseDaoHandler {
	public ObjectDaoHandler(Handler<Object> next) {
		super(next);
	}

	public abstract void onSuccess(T result);
}
