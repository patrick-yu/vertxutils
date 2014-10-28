package org.patrickyu.vertx.http;

import org.vertx.java.core.http.HttpClientResponse;

public interface HttpResponseHandler {

	void onResponse(HttpClientResponse res, String body);

}
