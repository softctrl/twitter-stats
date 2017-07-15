package com.aliancahospitalar.sqlite.client.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aliancahospitalar.twitter.model.Genre;
import com.aliancahospitalar.twitter.model.Movie;
import com.aliancahospitalar.twitter.model.Rate;
import com.aliancahospitalar.twitter.model.User;

/**
 * 
 * @author timoshenko
 *
 */
@RestController
public class InsertController {

	/**
	 * 
	 * @param user
	 */
	@RequestMapping(value = "/twitter/user", method = RequestMethod.POST)
	public void insertUser(@RequestBody User user) {

		System.out.format("InsertController.insertUser(%s)\n", user.toString());

	}

	/**
	 * 
	 * @param movie
	 */
	@RequestMapping(value = "/twitter/movie", method = RequestMethod.POST)
	public void insertMovie(@RequestBody Movie movie) {

		System.out.format("InsertController.insertMovie(%s)\n", movie.toString());

	}

	/**
	 * 
	 * @param genre
	 */
	@RequestMapping(value = "/twitter/genre", method = RequestMethod.POST)
	public void insertGenre(@RequestBody Genre genre) {

		System.out.format("InsertController.insertGenre(%s)\n", genre.toString());

	}

	/**
	 * 
	 * @param rate
	 */
	@RequestMapping(value = "/twitter/rate", method = RequestMethod.POST)
	public void insertRate(@RequestBody Rate rate) {

		System.out.format("InsertController.insertRate(%s)\n", rate.toString());

	}

}
