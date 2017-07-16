package com.aliancahospitalar.stats.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.softctrl.net.rest.StringHTTPRestfulClient;
import br.com.softctrl.net.rest.Sync;

/**
 * 
 * @author timoshenko
 *
 */
@Controller
public class ElasticStatsController {
	
	private static final String ELASTIC_SERVICE = "http://localhost:9200/twitter/";
	private static final String MOVIE_STS = "movie-sts";
	private static final String GENRE_STS = "genre-sts";

	/**
	 * 1. Quais os filmes com melhor avaliação média? GET
	 * GET /twitter/movie-sts/_search?sort=avg_rating:desc&size=20
	 * 
	 * @param size
	 */
	@RequestMapping(value="/movies/best", produces={"application/json"})
	public @ResponseBody String getBestMovies(@RequestParam(value = "size", required = false, defaultValue = "20") int size) {

		Sync<String, String> client = new StringHTTPRestfulClient().Sync();
		final String url = ELASTIC_SERVICE + MOVIE_STS + "/_search?sort=avg_rating:desc&size=" + size;
		return client.get(url);

	}
	
	/**
	 * 2. Quais os gêneros com melhor avaliação média?
	 * GET /twitter/genre-sts/_search?sort=avg_rating:desc
	 * 
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/genres/best", produces={"application/json"})
	public @ResponseBody String getBestGenres() {

		Sync<String, String> client = new StringHTTPRestfulClient().Sync();
		final String url = ELASTIC_SERVICE + GENRE_STS + "/_search?sort=avg_rating:desc";
		return client.get(url);

	}

	/**
	 * 3. Quais os gêneros com mais filmes?
	 * GET /twitter/genre-sts/_search?sort=m_count:desc
	 * 
	 * @return
	 */
	@RequestMapping(value="/genres/countmovies", produces={"application/json"})
	public @ResponseBody String getGenresMoreMovies() {

		Sync<String, String> client = new StringHTTPRestfulClient().Sync();
		final String url = ELASTIC_SERVICE + GENRE_STS + "/_search?sort=m_count:desc";
		return client.get(url);

	}

	/**
	 * 4. Qual a avaliação média por gênero?
	 * GET /twitter/genre-sts/_search?q=id:2
	 * 
	 * @return
	 */
	@RequestMapping(value="/genres/ratingavgbygenre", produces={"application/json"})
	public @ResponseBody String getGdenresMoreMovies(@RequestParam("id") int id) {

		Sync<String, String> client = new StringHTTPRestfulClient().Sync();
		final String url = ELASTIC_SERVICE + GENRE_STS + "/_search?q=id:" + id;
		return client.get(url);

	}

	/**
	 * 5. Qual a avaliação média por ano?
	 * GET /twitter/movie-sts/_search
	 * {
	 *    "size": 0,
	 *    "aggs": {
	 *       "group_by_year": {
	 *          "terms": {
	 *             "field": "m_year"
	 *          },
	 *          "aggs": {
	 *             "average_rating": {
	 *                "avg": {
	 *                   "field": "avg_rating",
	 *                   "missing": 0
	 *                }
	 *             }
	 *          }
	 *       }
	 *    }
	 * }
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/movies/ratingavgyear", produces={"application/json"})
	public @ResponseBody String getRatingByYear() {

		Sync<String, String> client = new StringHTTPRestfulClient().Sync();
		final String url = ELASTIC_SERVICE + MOVIE_STS + "/_search";
		final String body = "{\"size\": 0,\"aggs\": {\"group_by_year\": {\"terms\": {\"field\": \"m_year\"},\"aggs\": {\"average_rating\": {\"avg\": {\"field\": \"avg_rating\",\"missing\": 0}}}}}}";
		return client.post(url, body);

	}

	/**
	 * 6. Qual a distribuição do número de filmes produzidos por ano?
	 * GET /twitter/movie-sts/_search
	 * {
	 *   "size": 0,
	 *   "aggs": {
	 *     "group_by_year": {
	 *       "terms": {
	 *         "field": "m_year"
	 *       }
	 *     }
	 *   }
	 * }
	 * 
	 * @return
	 */
	@RequestMapping(value="/movies/countbyyear", produces={"application/json"})
	public @ResponseBody String getMoviesByYear() {

		Sync<String, String> client = new StringHTTPRestfulClient().Sync();
		final String url = ELASTIC_SERVICE + MOVIE_STS + "/_search";
		final String body = "{\"size\": 0,\"aggs\": {\"group_by_year\": {\"terms\": {\"field\": \"m_year\"}}}}";
		return client.post(url, body);

	}

	/**
	 * 7. Qual a distribuição do número de filmes produzidos por década?
	 * GET /twitter/movie-sts/_search
	 * {
	 *    "size": 0,
	 *    "aggs": {
	 *       "group_by_centure": {
	 *          "terms": {
	 *             "field": "m_centure"
	 *          },
	 *          "aggs": {
	 *             "group_by_decade": {
	 *                "terms": {
	 *                   "field": "m_decade"
	 *                }
	 *             }
	 *          }
	 *       }
	 *    }
	 * }
	 * 
	 * @return
	 */
	@RequestMapping(value="/movies/countbydecade", produces={"application/json"})
	public @ResponseBody String gedtMoviesByDecade() {

		Sync<String, String> client = new StringHTTPRestfulClient().Sync();
		final String url = ELASTIC_SERVICE + MOVIE_STS + "/_search";
		final String body = "{\"size\":0,\"aggs\":{\"group_by_centure\":{\"terms\":{\"field\":\"m_centure\"},\"aggs\":{\"group_by_decade\":{\"terms\":{\"field\":\"m_decade\"}}}}}}";
		return client.post(url, body);

	}

}
