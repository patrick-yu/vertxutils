package org.patrickyu.vertx.handler;

import java.lang.reflect.Constructor;

import org.patrickyu.vertx.exception.DBException;
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

	@SuppressWarnings("unchecked")
	protected Throwable getThrowableInstance(String className, String message) {
		try {
			Class<Throwable> cls = (Class<Throwable>)Class.forName(className);
			Class<Object>[] types = new Class[]{String.class};
			Constructor<Throwable> con = cls.getConstructor(types);
			Throwable t = (Throwable)con.newInstance(message);
			return t;
		} catch (Throwable e) {
			return new DBException(message);
		}
	}

	protected abstract void doHandle(E value);
	protected abstract void onException(Throwable e);
}
