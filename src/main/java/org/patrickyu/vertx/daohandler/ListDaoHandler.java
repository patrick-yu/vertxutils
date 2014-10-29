package org.patrickyu.vertx.daohandler;

import java.util.List;

import org.vertx.java.core.Handler;


public abstract class ListDaoHandler<E> extends ObjectDaoHandler<List<E>> {

	public ListDaoHandler(Handler<Object> next) {
		super(next);
	}

}
