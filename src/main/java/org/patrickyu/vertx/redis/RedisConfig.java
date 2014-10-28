package org.patrickyu.vertx.redis;

import java.io.File;

import org.patrickyu.util.ClassUtils;
import org.patrickyu.util.FileUtils;
import org.vertx.java.core.json.JsonObject;

public class RedisConfig {

	// event bus address
	private String address;
	private String host;
	private int port;
	private String encoding;
	private boolean binary;
	private String auth;
	private Number select;
	private String module_name = "";

	public static RedisConfig loadConfig(File configFile) {
		String jsonString = FileUtils.readFileToString(configFile);
		return ClassUtils.jsonStringToObject(jsonString, RedisConfig.class);
	}

	public JsonObject toJsonObject() {
		String jsonString = ClassUtils.objectToJsonString(this);
		JsonObject configJson = new JsonObject(jsonString);
		return configJson;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public boolean isBinary() {
		return binary;
	}

	public void setBinary(boolean binary) {
		this.binary = binary;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public Number getSelect() {
		return select;
	}

	public void setSelect(Number select) {
		this.select = select;
	}

	public String getModule_name() {
		return module_name;
	}

	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}

}
