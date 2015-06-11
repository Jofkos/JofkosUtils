package com.jofkos.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MySQL {

	private ExecutorService executor = Executors.newCachedThreadPool();
	private String host, user, password, database;

	private Plugin plugin;
	private int port;

	private Connection conn;

	public MySQL(Plugin owner, String host, int port, String user, String password, String database) throws Exception {
		this.plugin = owner;
		this.host = host;
		this.port = port > 0 ? port : 3306;
		this.user = user;
		this.password = password;
		this.database = database;

		this.openConnection();
	}
	
	/* Update - Statement */
	public void queryUpdate(PreparedStatement statement) {
		checkConnection();
		try {
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void queryUpdateAsync(PreparedStatement statement) {
		executor.execute(() -> queryUpdate(statement));
	}
	
	/* Update - String */
	public void queryUpdate(String query) {
		checkConnection();
		try (PreparedStatement statement = conn.prepareStatement(query)) {
			queryUpdate(statement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void queryUpdateAsync(String statement) {
		executor.execute(() -> queryUpdate(statement));
	}

	/* Query - Statement */
	public ResultSet query(PreparedStatement statement) {
		checkConnection();
		try {
			return statement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void queryAsync(PreparedStatement statement, Consumer<ResultSet> consumer) {
		executor.execute(() -> {
			ResultSet result = query(statement);
			Bukkit.getScheduler().runTask(plugin, () -> consumer.accept(result));
		});
	}
	
	/* Query - String */
	public ResultSet query(String query) {
		checkConnection();
		try {
			return query(conn.prepareStatement(query));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void queryAsync(String statement, Consumer<ResultSet> consumer) {
		executor.execute(() -> {
			ResultSet result = query(statement);
			Bukkit.getScheduler().runTask(plugin, () -> consumer.accept(result));
		});
	}
	
	/* Connection etc */
	public PreparedStatement prepare(String query) {
		try {
			return getConnection().prepareStatement(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Connection getConnection() {
		return this.conn;
	}

	private void checkConnection() {
		try {
			if (this.conn == null || !this.conn.isValid(10) || this.conn.isClosed()) openConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection openConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		return this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.user, this.password);
	}

	public void closeConnection() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conn = null;
		}
	}

}
