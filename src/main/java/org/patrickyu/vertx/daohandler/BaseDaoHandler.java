package org.patrickyu.vertx.daohandler;

import org.vertx.java.core.Handler;

public class BaseDaoHandler {
	private Handler<Object> next;
	public BaseDaoHandler(Handler<Object> next) {
		this.next = next;
	}

	public void onError(Throwable e) {
		next.handle(e);
	}
}
