package org.patrickyu.vertx.pgmysql;

import java.io.File;

import org.patrickyu.util.ClassUtils;
import org.patrickyu.util.FileUtils;
import org.vertx.java.core.json.JsonObject;

public class PgMySqlConfig {

	// event bus address
	private String address;
	// "mysql" or "postgresql"
	private String connection;
	private String host;
	private int port;
	private int maxPoolSize;
	private String username;
	private String password;
	private String database;
	private String moduleName;

	public static PgMySqlConfig loadConfig(File file) {
		String jsonString = FileUtils.readFileToString(file);
		return ClassUtils.jsonStringToObject(jsonString, PgMySqlConfig.class);
	}

	public void setJsonString(String jsonString) {
		PgMySqlConfig config = ClassUtils.jsonStringToObject(jsonString, PgMySqlConfig.class);
		ClassUtils.copy(this, config);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		if (connection == null)
			connection = "postgresql";
		connection = connection.toLowerCase();
		if (!connection.equals("mysql") && !connection.equals("postgresql"))
			connection = "postgresql";
		this.connection = connection;
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

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public JsonObject toJsonObject() {
		JsonObject configJson = new JsonObject();
		configJson.putString("address", this.address);
		configJson.putString("connection",this.connection);
		configJson.putString("host",this.host);
		configJson.putNumber("port", this.port);
		configJson.putNumber("maxPoolSize", this.maxPoolSize);
		configJson.putString("username",this.username);
		configJson.putString("password",this.password);
		configJson.putString("database",this.database);
		return configJson;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

//	public static void main(String[] args) {
//		String conf = "{\"address\" : \"address1\",\"connection\" : \"mysql\",\"host\" : \"host1\",\"port\" : 8888,\"maxPoolSize\" :100,\"username\" : \"yym\",\"password\" :\"pass\",\"database\" : \"database1\"}";
//		PgMySqlConfig c = new PgMySqlConfig();
//		c.setJsonString(conf);
//		System.out.println(c.address);
//	}

}
