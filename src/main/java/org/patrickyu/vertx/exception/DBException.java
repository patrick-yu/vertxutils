package org.patrickyu.vertx.exception;

public class DBException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -8071837188045114066L;

	public DBException() {
		super();
	}

	public DBException(Throwable e) {
		super(e);
	}

	public DBException(String message, Throwable e) {
		super(message, e);
	}

	public DBException(String message) {
		super(message);
	}
}
