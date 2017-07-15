package com.aliancahospitalar.data.dump;

import java.io.IOException;
import java.sql.SQLException;

import com.aliancahospitalar.data.export.DataExport;
import com.aliancahospitalar.twitter.persistence.DBConn;

/**
 * After you can execute:
 * split -l 10000 ../bulkinsert.dump
 * 
 * for f in `ls`; do
 *   curl -s -XPOST http://localhost:9200/twitter/rating/_bulk --data-binary @${f};
 *   mv ${f} OK_${f};
 * done
 * 
 * @author timoshenko
 *
 */
public class Main {

	/**
	 * 
	 * @param args
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void main(String[] args) throws SQLException, IOException {

		System.out.println("Please hold on...");

		// Only for test purposes:
		// args = new String[] { "twitter-movie-ratings.db", "bulkinsert.dump" };

		if (args.length != 2) {
			System.out.println("So, where is the SQLite file and the file that i need to create?");
			System.exit(-1);
			return;
		}

		DBConn conn = DBConn.getInstance();
		conn.connect(args[0]);

		new DataExport().generateInitialData(args[1]);

		System.out.println("Initial data export generated with success.");

		conn.disconnect();

	}

}
