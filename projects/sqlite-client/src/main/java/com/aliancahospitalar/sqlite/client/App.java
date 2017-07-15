package com.aliancahospitalar.sqlite.client;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aliancahospitalar.twitter.persistence.DBConn;

/**
 * 
 * @author timoshenko
 *
 */
@SpringBootApplication
public class App {

	private static final String DBASE = "/Applications/Development/Java/eclipse/jee-neon/workspace/sqlite-client/database/twitter-movie-ratings.db";

	public static void main(String[] args) throws SQLException {

		DBConn conn = DBConn.getInstance();
		conn.connect(DBASE);
		SpringApplication.run(App.class, args);

	}

}
