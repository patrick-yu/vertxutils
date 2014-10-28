package org.patrickyu.vertx.http;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.patrickyu.util.ArrayUtils;
import org.patrickyu.util.HttpUrl;
import org.patrickyu.util.StringUtils;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientRequest;
import org.vertx.java.core.http.HttpClientResponse;

import com.jetdrone.vertx.yoke.middleware.YokeRequest;

public class HttpUtils {

	public static String getParamString(YokeRequest request, String name) {
		if (StringUtils.equalsIgnoreCase(request.method(), "POST")) {
			request.expectMultiPart(true);
			return request.getFormParameter(name);
		}
		return request.getParameter(name);
	}

	public static String getParamString(YokeRequest request, String name, String defaultValue) {
		String value = getParamString(request, name);
		if (StringUtils.isEmpty(value))
			return defaultValue;
		return value;
	}

	public static boolean getParamBoolean(YokeRequest request, String name) {
		return getParamBoolean(request, name, false);
	}

	public static boolean getParamBoolean(YokeRequest request, String name, boolean defaultValue) {
		String[] trueValue = {"1","true"};
		String[] falseValue = {"0","false"};
		String value = getParamString(request, name);
		if (StringUtils.isEmpty(value))
			return defaultValue;
		value = value.toLowerCase();
		if (ArrayUtils.contains(trueValue, value))
			return true;
		else if (ArrayUtils.contains(falseValue, value))
			return false;
		else
			return defaultValue;
	}

	public static Long getParamLong(YokeRequest request, String name) {
		return getParamLong(request, name, null);
	}

	public static Long getParamLong(YokeRequest request, String name, Long defaultValue) {
		String val = getParamString(request, name);
		if (StringUtils.isEmpty(val))
			return defaultValue;
		return Long.parseLong(val);
	}

	public static Integer getParamInteger(YokeRequest request, String name) {
		return getParamInteger(request, name, null);
	}

	public static Integer getParamInteger(YokeRequest request, String name, Integer defaultValue) {
		String val = getParamString(request, name);
		if (StringUtils.isEmpty(val))
			return defaultValue;
		return Integer.parseInt(val);
	}

	public static <T> T getParamObject(YokeRequest request, Class<T> cls) {
		T obj = null;
		try {
			obj = cls.newInstance();
		} catch (Exception e) {
			return null;
		}

		Field[] fields = cls.getDeclaredFields();
		for (Field field: fields) {
			String fieldName = field.getName();
			String methodName = "set" + StringUtils.capitalize(fieldName);
			try {
				cls.getDeclaredMethod(methodName, field.getType());
			} catch (Exception e) {
				continue;
			}

			String value = getParamString(request, fieldName);
			try {
				field.setAccessible(true);
				if (value == null)
					continue;
				else if (field.getType() == String.class)
					field.set(obj, value);
				else if (field.getType() == int.class)
					field.set(obj, Integer.parseInt(value));
				else if (field.getType() == long.class)
					field.set(obj, Long.parseLong(value));
				else if (field.getType() == double.class)
					field.set(obj, Double.parseDouble(value));
				else if (field.getType() == boolean.class || field.getType() == Boolean.class)
					field.set(obj, getParamBoolean(request, fieldName, false));
				else {
					Object v = field.getType().getDeclaredMethod("valueOf", String.class).invoke(null, value);
					field.set(obj, v);
				}
			} catch (Exception e) {
				continue;
			}
		}
		return obj;
	}

	public static String getAcceptLanguage(YokeRequest request) {
		String acceptLanguage = request.headers().get("Accept-Language");
	    String[] languages = acceptLanguage.split(",");
	    String lang = null;
	    double order = 0;
	    for (String language: languages) {
	    	String[] data = language.split(";");
	    	if (data.length == 1) {
	    		lang = data[0];
	    		order = 1.0;
	    	}
	        else {
	            String[] orders = data[1].split("=");
	            if (orders.length == 1)
	            	continue;
	            double thisOrder = Double.parseDouble(orders[1]);
	            if (thisOrder > order) {
	            	lang = data[0];
	            	order = thisOrder;
	            }
	        }
	    }
	    return lang;
	}

	public static String encodeParam(Map<String, String> param) {
		if (param == null)
			return "";
		Set<String> names = param.keySet();
		List<String> list = new ArrayList<String>();
		for (String name: names) {
			String value = "";
			try {
				value = URLEncoder.encode(param.get(name), "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
			list.add(name + "=" + value);
		}
		return ArrayUtils.join(list.toArray(), '&');
	}

	public static boolean isAjaxRequest(YokeRequest request) {
		String value = request.headers().get("X-Requested-With");
		if (StringUtils.isNotEmpty(value)) {
			return true;
		}
		String accept = request.headers().get("Accept");
		if (StringUtils.isEmpty(accept)) {
			return true;
		}
		String[] a = accept.split("\\;");
		if (a.length < 1)
			return false;
		String[] t = a[0].split("\\,");
		if (t.length < 1)
			return false;
		if (StringUtils.equalsIgnoreCase(t[0].trim(), "application/json"))
			return true;
		return false;
	}

	public static void postHttp(Vertx vertx, String url, Map<String, String> params, final HttpResponseHandler handler) {
		String strParams = HttpUtils.encodeParam(params);
		HttpUrl httpUrl = new HttpUrl(url);
		final HttpClient client = vertx.createHttpClient();
		client.setHost(httpUrl.getHost());
		client.setPort(httpUrl.getPort());
		client.setSSL(httpUrl.isSsl());
		if (httpUrl.isSsl())
			client.setTrustAll(true);

		HttpClientRequest clientRequest = client.post(httpUrl.getFullPath(), new Handler<HttpClientResponse>() {
			@Override
			public void handle(final HttpClientResponse res) {
				res.bodyHandler(new Handler<Buffer>() {
					@Override
					public void handle(Buffer body) {
						String data = body.toString();
						handler.onResponse(res, data);
					}
				});
			}
		});

		clientRequest.putHeader("Content-Length", String.valueOf(strParams.getBytes().length));
		clientRequest.putHeader("Content-Type", "application/x-www-form-urlencoded");
		clientRequest.write(strParams);
		clientRequest.end();
	}

	public static void getHttp(Vertx vertx, String url, Map<String, String> params, final HttpResponseHandler handler) {
		String strParams = HttpUtils.encodeParam(params);
		if (StringUtils.isNotEmpty(strParams)) {
			if (url.indexOf("?") > 0)
				url += "&" + strParams;
			else
				url += "?" + strParams;
		}
		HttpUrl httpUrl = new HttpUrl(url);
		final HttpClient client = vertx.createHttpClient();
		client.setHost(httpUrl.getHost());
		client.setPort(httpUrl.getPort());
		client.setSSL(httpUrl.isSsl());
		if (httpUrl.isSsl())
			client.setTrustAll(true);

		client.getNow(httpUrl.getFullPath(), new Handler<HttpClientResponse>() {
			@Override
			public void handle(final HttpClientResponse res) {
				res.bodyHandler(new Handler<Buffer>() {
					@Override
					public void handle(Buffer body) {
						String data = body.toString();
						handler.onResponse(res, data);
					}
				});
			}
		});
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		System.out.println(ArrayUtils.join(list.toArray(), '&'));
	}
}
