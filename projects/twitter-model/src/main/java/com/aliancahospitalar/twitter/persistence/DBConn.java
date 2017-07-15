package com.aliancahospitalar.twitter.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.aliancahospitalar.twitter.model.IEntity;

/**
 * To get some time :D i put a lot of db util methods here.
 * 
 * @author timoshenko
 *
 */
public class DBConn {

	private static final class Constants {
		private static final String CONNECTION_STRING = "jdbc:sqlite:%s";
	}
	
	public interface Action {
		void insert();
	}

	private static DBConn THIS = null;

	private Connection connection = null;
	private static final ConcurrentHashMap<Date, Action> INSERT_STACK = new ConcurrentHashMap<>();

	/**
	 * 
	 */
	private DBConn() {
	}

	/**
	 * Here will be a great place to validate the existence of this file before
	 * use it, but again i need time for now :D
	 * 
	 * @param file
	 * @throws SQLException
	 */
	public void connect(final String file) throws SQLException {

		this.connection = DriverManager.getConnection(String.format(Constants.CONNECTION_STRING, file));

	}

	/**
	 * 
	 * @throws SQLException
	 */
	public void disconnect() throws SQLException {
		if (this.connection != null && !this.connection.isClosed()) {
			this.connection.close();
		}
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Statement createStatement() throws SQLException {
		Statement sts = connection.createStatement();
		sts.setQueryTimeout(30);
		return sts;
	}

	/**
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate(final String sql) throws SQLException {
		return this.createStatement().executeUpdate(sql);

	}

	/**
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(final String sql) throws SQLException {
		return this.createStatement().executeQuery(sql);

	}

	/**
	 * 
	 * @param clazz
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public <T extends IEntity<?, ?>> List<T> findAll(Class<T> clazz, final String sql)
			throws SQLException, InstantiationException, IllegalAccessException {

		ResultSet rs = executeQuery(sql);
		List<T> list = new ArrayList<T>();
		while (rs.next()) {
			list.add((T) clazz.newInstance().parse(rs));
		}
		return list;

	}

	/**
	 * 
	 * @param clazz
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public <T extends IEntity<?, ?>> T findOne(Class<T> clazz, final String sql)
			throws SQLException, InstantiationException, IllegalAccessException {

		ResultSet rs = executeQuery(sql);
		T result = null;
		if (rs.next()) {
			result = (T) clazz.newInstance().parse(rs);
		}
		return result;

	}

	/**
	 * 
	 * @return
	 */
	public static final DBConn getInstance() {
		if (THIS == null)
			THIS = new DBConn();
		return THIS;
	}

}
