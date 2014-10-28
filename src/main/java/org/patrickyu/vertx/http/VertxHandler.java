package org.patrickyu.vertx.http;

import org.vertx.java.core.Handler;

public abstract class VertxHandler<E> implements Handler<E> {

	@Override
	public void handle(E value) {
		try {
			doHandle(value);
		} catch (Throwable e) {
			onException(e);
		}
	}

	public abstract void doHandle(E value);
	public abstract void onException(Throwable e);
}
