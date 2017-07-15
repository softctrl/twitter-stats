/**
 * 
 */
package com.aliancahospitalar.twitter.persistence;

import java.sql.SQLException;

import com.aliancahospitalar.twitter.model.IEntity;

/**
 * So, i do consider this a anti-pattern, but to solve this problem i need this.
 * :(
 * 
 * @author timoshenko
 *
 */
public class EntityBaseDAO<E extends IEntity<?, ?>> {

	private final DBConn conn;

	/**
	 * 
	 */
	public EntityBaseDAO() {
		this.conn = DBConn.getInstance();
	}

	/**
	 * So, to make a single poc, i will cover only the C letter of the CRUD
	 * acronym.
	 * 
	 * @param entity
	 * @return
	 * @throws SQLException
	 */
	public int insert(final E entity) throws SQLException {
		
		int result = this.conn.executeUpdate(entity.getInsertSQL());
		if (result != -1) {
			// TODO: Send to the rest service here in another thread:
		}
		return result;
		
	}

}
