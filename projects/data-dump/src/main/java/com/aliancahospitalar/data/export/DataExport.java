/**
 * 
 */
package com.aliancahospitalar.data.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.aliancahospitalar.twitter.persistence.DBConn;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * @author timoshenko
 *
 */
public class DataExport {
	
	private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
/*
	private static final String SQL_MOV_DATA = "SELECT mr.id,"
			                                 + "       m.id movie_id,"
			                                 + "       m.year,"
			                                 + "       mg.genre_id genre_id,"
			                                 + "       mr.rating_id rating_id"
			                                 + "  FROM movies m"
			                                 + " INNER JOIN movie_genre mg ON mg.movie_id = m.id"
			                                 + " INNER JOIN movie_ratings mr ON mr.movie_id = m.id"
//			                                 + " WHERE mr.id < 6" // For test purposes
			                                 + " ORDER BY mr.id, m.id, mg.genre_id;";
*/

	private static final String SQL_MOV_DATA = "SELECT m.id,"
                                             + "       mr.id mr_id,"
                                             + "       mg.genre_id genre_id,"
                                             + "       mr.rating_id rating_id,"
                                             + "       m.year"
                                             + "  FROM movies m"
                                             + " LEFT JOIN movie_genre mg ON mg.movie_id = m.id"
                                             + " LEFT JOIN movie_ratings mr ON mr.movie_id = m.id"
//                                             + " WHERE mr.id < 6" // For test purposes
                                             + " ORDER BY m.id, mr.id, mg.genre_id;";

	
	private DBConn dbConn = DBConn.getInstance();
	
	@SuppressWarnings("serial")
	public static final class GenreStatsHashMap extends HashMap<String, GenreStats> {
		public GenreStats put(GenreStats genre) {
			return super.put(genre.id, genre);
		}
	}
	
	public static final class GenreStats {
		
		private static final String BULK = "{ \"index\" : { \"_index\" : \"twitter\", \"_type\" : \"genre-sts\", \"_id\" : \"%s\" } }";
		@Expose public String id;
		@Expose public Integer m_count = 0;
		public Set<Long> m_ids = new HashSet<Long>();
		public Long rating_count = 0l;
 		@Expose public Double avg_rating = 0.0;
		
		public GenreStats(final ResultSet rs) throws SQLException {
			

			this.id = rs.getString("genre_id");
			this.m_ids.add(rs.getLong("id"));
			this.rating_count++;
			this.avg_rating = (1.0) *rs.getLong("rating_id");

		}
		
		public void append(final ResultSet rs) throws SQLException {
			this.rating_count++;
			this.avg_rating += rs.getInt("rating_id");
			this.m_ids.add(rs.getLong("id"));
		}
		
		public GenreStats calculate() {
			
			if (this.rating_count > 0) {
			  this.avg_rating = (1.0 * this.avg_rating) /  (1.0 * this.rating_count);
			}
			this.m_count = this.m_ids.size();
			return this;

		}
		


		
		public byte[] toJsonBytes() {

			StringBuilder json = new StringBuilder();
			json.append(String.format(BULK, this.id)).append('\n');
			json.append(GSON.toJson(this)).append('\n');
			return json.toString().getBytes();

		}

		
	}
	
	public static final class Rating {
		@Expose public Long id;
		@Expose public Integer rate;
		
		public Rating(Long id, Integer rate) {
			this.id = id;
			this.rate = rate;
		}
		
		@Override
		public boolean equals(Object obj) {
			return this.id == Rating.class.cast(obj).id; // Who cares about nullpointers? \o/ no no no, i just do not have much time :(
		}
		
		@Override
		public int hashCode() {
			return 31 * this.id.hashCode();
		}
		
	}
	
	public static final class MovieStats {
		
		private static final String BULK = "{ \"index\" : { \"_index\" : \"twitter\", \"_type\" : \"movie-sts\", \"_id\" : \"%s\" } }";

		@Expose public String id;
		public Long mr_id;
		@Expose public Integer m_year;
		@Expose public Integer m_centure;
		@Expose public Integer m_decade;
		@Expose public Double avg_rating = 0.0;
		public Long rating_count = 0l;
		@Expose public Set<Rating> ratings = new HashSet<>();
		@Expose public Set<Long> genre_ids = new HashSet<Long>();
		
		public MovieStats(final ResultSet rs) throws SQLException {

			this.id = rs.getString("id");
			this.mr_id = rs.getLong("mr_id");
			this.m_year = rs.getInt("year");
			this.m_centure = (this.m_year / 100);
			this.m_decade = (this.m_year - this.m_centure*100)/10;
			this.m_decade *= 10;
			if (this.m_year > this.m_centure*100) this.m_centure++; 
			this.genre_ids.add(rs.getLong("genre_id"));
			
			this.rating_count++;
			this.avg_rating += rs.getInt("rating_id");
			
			this.ratings.add(new Rating(rs.getLong("mr_id"), rs.getInt("rating_id")));

		}
		
		public void append(final ResultSet rs) throws SQLException {
			
			if (this.mr_id != rs.getLong("mr_id")) {
				this.rating_count++;
				this.avg_rating += rs.getInt("rating_id");
				
				this.ratings.add(new Rating(rs.getLong("mr_id"), rs.getInt("rating_id")));
			}
			this.genre_ids.add(rs.getLong("genre_id"));
		}
		
		public MovieStats calculate() {
			
			if (this.rating_count > 0) {
			  this.avg_rating = (1.0 * this.avg_rating) /  (1.0 * this.rating_count);
			}
			return this;

		}
		
		/**
		 * 
		 * @return
		 */
		public byte[] toJsonBytes() {

			StringBuilder json = new StringBuilder();
			json.append(String.format(BULK, this.id)).append('\n');
			json.append(GSON.toJson(this)).append('\n');
			return json.toString().getBytes();

		}
		
		public boolean equals(MovieStats obj) {
			
			return (this.id == obj.id);

		}

	}

	/**
	 * 
	 * @param filename
	 * @throws SQLException
	 * @throws IOException
	 */
	public void generateInitialData(final String filename) throws SQLException, IOException {
		
		ResultSet rs = dbConn.executeQuery(SQL_MOV_DATA);
		if (rs.next()) {

			OutputStream os = new FileOutputStream(new File(filename));
			
			MovieStats data = new MovieStats(rs);
			
			GenreStatsHashMap genres = new GenreStatsHashMap();
			genres.put(new GenreStats(rs));

			boolean next = rs.next();
			while (next) {
				
				// Same movie:
				if ((data.id+"").equals(rs.getString("id"))) {
					data.append(rs);
				} else {
					os.write(data.calculate().toJsonBytes());
					data = new MovieStats(rs);
				}
				
				if (genres.containsKey(rs.getString("genre_id"))) {
					genres.get(rs.getString("genre_id")).append(rs);
				} else {
					genres.put(new GenreStats(rs));
				}
				
				
				next = rs.next();

			}
			
			os.write(data.calculate().toJsonBytes());

			for (Entry<String, GenreStats> element : genres.entrySet()) {
				os.write(element.getValue().calculate().toJsonBytes());
			}
			
			os.close();

		}

	}

	/**
	 * 
	 * @param filename
	 * @throws SQLException
	 * @throws IOException
	 */
//	public void generateInitialGenreStatsFile(final String filename) throws SQLException, IOException {
//		
//		ResultSet rs = dbConn.executeQuery(SQL_G);
//		if (rs.next()) {
//
//			OutputStream os = new FileOutputStream(new File(filename));
//			
//			MovieStats data = new MovieStats(rs); 
//			GenreStats genre = new GenreStats(rs);
//
//			boolean next = rs.next();
//			while (next) {
//				
//				if ((data.id+"").equals(rs.getString("id"))) {
//					data.append(rs);
//				} else {
//					os.write(data.calculate().toJsonBytes());
//					data = new MovieStats(rs);
//				}
//				
//				if ((genre.id+"").equals(rs.getString("genre_id"))) {
//					genre.append(rs);
//				} else {
//					os.write(genre.calculate().toJsonBytes());
//					genre = new GenreStats(rs);
//				}
//				
//				
//				next = rs.next();
//
//			}
//			os.write(data.calculate().toJsonBytes());
//			os.close();
//
//		}
//
//	}

}
