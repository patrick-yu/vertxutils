package org.patrickyu.vertx.handler;

import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;

import com.jetdrone.vertx.yoke.Middleware;
import com.jetdrone.vertx.yoke.core.YokeException;
import com.jetdrone.vertx.yoke.middleware.YokeRequest;
import com.jetdrone.vertx.yoke.middleware.YokeResponse;

public abstract class ErrorHandler extends Middleware {

    @Override
    public boolean isErrorHandler() {
        return true;
    }

    @SuppressWarnings("unchecked")
	private String getMessage(Object error) {
        if (error instanceof Throwable) {
        	return getExceptionMessage((Throwable)error);
        } else if (error instanceof String) {
            return (String) error;
        } else if (error instanceof Integer) {
            return HttpResponseStatus.valueOf((Integer) error).reasonPhrase();
        } else if (error instanceof JsonObject) {
            return ((JsonObject) error).getString("message");
        } else if (error instanceof Map) {
            return (String) ((Map<String, Object>) error).get("message");
        } else {
            return error.toString();
        }
    }

    @SuppressWarnings("unchecked")
	private int getErrorCode(Object error) {
        if (error instanceof Number) {
            return ((Number) error).intValue();
        } else if (error instanceof YokeException) {
            return ((YokeException) error).getErrorCode().intValue();
        } else if (error instanceof JsonObject) {
            return ((JsonObject) error).getInteger("errorCode", 500);
        } else if (error instanceof Map) {
            Integer tmp = (Integer) ((Map<String, Object>) error).get("errorCode");
            return tmp != null ? tmp : 500;
        } else {
            return 500;
        }
    }

    private List<String> getStackTrace(Object error) {
        if (error instanceof Throwable) {
            List<String> stackTrace = new ArrayList<>();
            for (StackTraceElement t : ((Throwable) error).getStackTrace()) {
                stackTrace.add(t.toString());
            }
            return stackTrace;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void handle( final YokeRequest request,  final Handler<Object> next) {

        YokeResponse response = request.response();

        if (response.getStatusCode() < 400) {
            response.setStatusCode(getErrorCode(request.get("error")));
        }

        if (request.get("error") == null) {
            request.put("error", response.getStatusCode());
        }
        String errorMessage = getMessage(request.get("error"));
        int errorCode = response.getStatusCode();

        // set the status message also to the right error code
        response.setStatusMessage(HttpResponseStatus.valueOf(errorCode).reasonPhrase());

        List<String> stackTrace = getStackTrace(request.get("error"));

        // does the response already set the mime type?
        String mime = response.getHeader("content-type");

        if (mime != null) {
            if (sendError(request, mime, errorCode, errorMessage, stackTrace)) {
                return;
            }
        }

        // respect the client accept order
        List<String> acceptedMimes = request.sortedHeader("accept");

        for (String accept : acceptedMimes) {
            if (sendError(request, accept, errorCode, errorMessage, stackTrace)) {
                return;
            }
        }

        // fall back plain/text
        sendError(request, "text/plain", errorCode, errorMessage, stackTrace);
    }

    public abstract boolean sendError(YokeRequest request, String contentType, int errorCode, String errorMessage, List<String> stackTrace);
    public abstract String getExceptionMessage(Throwable error);

}
