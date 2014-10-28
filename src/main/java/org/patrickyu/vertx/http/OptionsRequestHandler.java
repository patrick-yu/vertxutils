package org.patrickyu.vertx.http;

import org.vertx.java.core.Handler;

import com.jetdrone.vertx.yoke.Middleware;
import com.jetdrone.vertx.yoke.middleware.YokeRequest;

public class OptionsRequestHandler extends Middleware {

	@Override
	public void handle(YokeRequest request, Handler<Object> next) {
		request.response().headers()
			.add("Access-Control-Allow-Origin", "*")
			.add("Access-Control-Allow-Credentials", "true")
			.add("Access-Control-Max-Age", "1728000")
			.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
			.add("Access-Control-Allow-Headers", "Authorization,Content-Type,Accept,Origin,User-Agent,DNT,Cache-Control,X-Mx-ReqToken,Keep-Alive,X-Requested-With,If-Modified-Since")
			.add("Content-Length", "0")
			.add("Content-Type", "text/plain charset=UTF-8");
		request.response().setStatusCode(204);
		request.response().end();
	}
}
