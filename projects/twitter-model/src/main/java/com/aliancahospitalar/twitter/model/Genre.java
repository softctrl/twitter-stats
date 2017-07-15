package com.aliancahospitalar.twitter.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * To get some time :D i will not create any get/set ok.
 * 
 * @author timoshenko
 *
 */
public class Genre implements IEntity<Genre, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6558867443957229809L;

	private static final class Constants {
		private static final String ID = "id";
		private static final String TITLE = "title";
	}

	public long id;
	public String title;

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
	public Genre parse(ResultSet resultSet) throws SQLException {
		this.id = resultSet.getLong(Constants.ID);
		this.title = resultSet.getString(Constants.TITLE);
		return this;
	}
	
	@Override
	public String getInsertSQL() {
		return null;
	}

}
