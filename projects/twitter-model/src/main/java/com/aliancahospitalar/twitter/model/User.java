package com.aliancahospitalar.twitter.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * To get some time :D i will not create any get/set ok.
 * 
 * @author timoshenko
 *
 */
public class User implements IEntity<User, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6019037152737957006L;

	private static final class Constants {
		private static final String ID = "id";
		private static final String TWITTER_ID = "twitter_id";
	}

	public long id;
	public long twitterId;

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
	public User parse(ResultSet resultSet) throws SQLException {
		this.id = resultSet.getLong(Constants.ID);
		this.twitterId = resultSet.getLong(Constants.TWITTER_ID);
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", twitterId=" + twitterId + "]";
	}

}
