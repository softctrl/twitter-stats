package com.aliancahospitalar.twitter.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 
 * @author timoshenko
 *
 * @param <T> The Entity class.
 * @param <I> The id type of this entity class.
 */
public interface IEntity<T, I> extends Serializable {

	/**
	 * 
	 * @return
	 */
	I getId();

	/**
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	T parse(final ResultSet resultSet) throws SQLException;

	/**
	 * 
	 * @return
	 */
	String getInsertSQL();

}
