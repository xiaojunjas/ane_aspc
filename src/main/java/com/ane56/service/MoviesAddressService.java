package com.ane56.service;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;

import com.ane56.domain.MoviesAddress;
import com.ane56.domain.MoviesFilm;

public interface MoviesAddressService {
	
	@GET("/v1/movies/moviesId")
	public MoviesAddress getMoviesAddressById(@Query("moviesId")Long moviesId);
	
	@GET("/v1/movies")
	public List<MoviesAddress> listMoviesAddress(@Query("start")Integer start ,@Query("limit")Integer limit,@Query("query")String query);
	
	@GET("/v1/movies/count")
	public Integer countMoviesAddress(@Query("query")String query);
	
	@POST("/v1/movies")
	public Integer createMoviesAddress(@Body MoviesAddress moviesAddress);
	
	@PUT("/v1/movies")
	public Integer editMoviesAddressByMoviesId(@Body MoviesAddress moviesAddress);
	
	@DELETE("/v1/movies/id")
	public Integer delMoviesAddressByMoviesId(@Query("moviesId")Long moviesId);
	
	@GET("/v1/movies/film/id")
	public List<MoviesFilm> findFilmMoviesByMoviesId(@Query("moviesId")Long moviesId,@Query("filmId")Long filmId);
	
	@POST("/v1/movies/film/batch")
	public Integer saveFilmMovies(@Body List<MoviesFilm> moviesFilm);
	
	@DELETE("/v1/movies/ids")
	public Integer delFilmMoviesByMoviesId(@Query("delteaIds")Long[] delteaIds);
	 
}
