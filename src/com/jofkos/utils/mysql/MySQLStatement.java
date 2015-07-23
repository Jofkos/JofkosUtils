package com.jofkos.utils.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class MySQLStatement {
	
	private MySQL mysql;
	private PreparedStatement statement;
	
	public MySQLStatement(MySQL mysql, String statement, Object... args) {
		this.mysql = mysql;
		this.statement = mysql.prepare(statement);
		int counter = 0;
		for (Object arg : args) {
			try {
				this.statement.setObject(++counter, arg);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void queryUpdate() {
		this.mysql.queryUpdate(statement);
	}
	
	public void queryUpdateAsync() {
		this.mysql.queryUpdateAsync(statement);
	}
	
	public ResultSet query() {
		return this.mysql.query(statement);
	}
	
	public void queryAsync(Consumer<ResultSet> consumer) {
		this.mysql.queryAsync(statement, consumer);
	}
}
