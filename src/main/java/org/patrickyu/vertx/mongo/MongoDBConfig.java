package org.patrickyu.vertx.mongo;

import java.io.File;

import org.patrickyu.util.ClassUtils;
import org.patrickyu.util.FileUtils;
import org.vertx.java.core.json.JsonObject;

public class MongoDBConfig {

	// event bus address
	private String address;
	private String host;
	private int port;
	private String db_name;
	private int pool_size;
	private boolean fake = false;
	private boolean use_ssl = false;
	private String read_preference = "primary";
	private String module_name = "";

	public static MongoDBConfig loadConfig(File configFile) {
		String jsonString = FileUtils.readFileToString(configFile);
		return ClassUtils.jsonStringToObject(jsonString, MongoDBConfig.class);
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

	public String getDb_name() {
		return db_name;
	}

	public void setDb_name(String db_name) {
		this.db_name = db_name;
	}

	public int getPool_size() {
		return pool_size;
	}

	public void setPool_size(int pool_size) {
		this.pool_size = pool_size;
	}

	public boolean isFake() {
		return fake;
	}

	public void setFake(boolean fake) {
		this.fake = fake;
	}

	public boolean isUse_ssl() {
		return use_ssl;
	}

	public void setUse_ssl(boolean use_ssl) {
		this.use_ssl = use_ssl;
	}

	public String getRead_preference() {
		return read_preference;
	}

	public void setRead_preference(String read_preference) {
		this.read_preference = read_preference;
	}

	public void setJsonString(String jsonString) {
		MongoDBConfig conf = ClassUtils.jsonStringToObject(jsonString, MongoDBConfig.class);
		ClassUtils.copy(this, conf);
	}

	public JsonObject toJsonObject() {
		String jsonString = ClassUtils.objectToJsonString(this);
		JsonObject configJson = new JsonObject(jsonString);
		return configJson;
	}

	public String getModule_name() {
		return module_name;
	}

	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}

//	public static void main(String[] args) {
//		String conf = "{\"address\" : \"address1\",\"connection\" : \"mysql\",\"host\" : \"host1\",\"port\" : 8888,\"maxPoolSize\" :100,\"username\" : \"yym\",\"password\" :\"pass\",\"database\" : \"database1\"}";
//		MongoDBConfig c = new MongoDBConfig();
//		c.setJsonString(conf);
//		System.out.println(c.host);
//	}
}
