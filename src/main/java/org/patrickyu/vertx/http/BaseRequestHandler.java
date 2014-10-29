package org.patrickyu.vertx.http;

import org.vertx.java.core.Handler;

import com.jetdrone.vertx.yoke.Middleware;
import com.jetdrone.vertx.yoke.middleware.YokeRequest;

public abstract class BaseRequestHandler extends Middleware {

	@Override
	public void handle(YokeRequest request, Handler<Object> next) {
		try {
			request.response().headers()
				.add("Access-Control-Allow-Origin", "*")
				.add("Access-Control-Allow-Credentials", "true");
			run(request, next);
		} catch (Exception e) {
			next.handle(e);
		}
	}

	public abstract void run(YokeRequest request, Handler<Object> next);
}
