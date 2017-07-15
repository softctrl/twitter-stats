package com.aliancahospitalar.twitter.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * To get some time :D i will not create any get/set ok.
 * 
 * @author timoshenko
 *
 */
public class Rate implements IEntity<Rate, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1908428562720513311L;

	private static final class Constants {
		private static final String ID = "id";
		private static final String FEELING = "feeling";
	}

	public long id;
	public long feeling;

	/**
	 * So, i will use auto-boxing here ok ;)
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
	public Rate parse(ResultSet resultSet) throws SQLException {
		this.id = resultSet.getLong(Constants.ID);
		this.feeling = resultSet.getLong(Constants.FEELING);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aliancahospitalar.sqlite.client.model.IEntity#getInsertSQL()
	 */
	@Override
	public String getInsertSQL() {
		return null;
	}

}
