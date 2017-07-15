package com.aliancahospitalar.twitter.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * To get some time :D i will not create any get/set ok.
 * 
 * @author timoshenko
 *
 */
public class Movie implements IEntity<Movie, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2812405635960223036L;

	private static final class Constants {
		private static final String ID = "id";
		private static final String TITLE = "title";
		private static final String YEAR = "year";
	}

	public long id;
	public String title;
	public int year;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aliancahospitalar.sqlite.client.model.IEntity#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aliancahospitalar.sqlite.client.model.IEntity#parse(java.sql.
	 * ResultSet)
	 */
	public Movie parse(ResultSet resultSet) throws SQLException {
		this.id = resultSet.getLong(Constants.ID);
		this.title = resultSet.getString(Constants.TITLE);
		this.year = resultSet.getInt(Constants.YEAR);
		return this;
	}
	
	@Override
	public String getInsertSQL() {
		return null;
	}

}
