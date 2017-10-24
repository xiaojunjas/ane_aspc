package com.ane56.service;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;

import com.ane56.domain.Film;

public interface FilmService {
	 
	@GET("/v1/film/filmId")
	public Film getFilmById(@Query("filmId")Long filmId);
	
	@GET("/v1/film")
	public List<Film> listFilm(@Query("start")Integer start ,@Query("limit")Integer limit,@Query("query")String query);
	
	@GET("/v1/film/count")
	public Integer countFilm(@Query("query")String query);
	
	@POST("/v1/film")
	public Integer createFilm(@Body Film film);
	
	@PUT("/v1/film")
	public Integer editFilmByFilmId(@Body Film film);
	
	@DELETE("/v1/film/id")
	public Integer delFilmByFilmId(@Query("filmId")Long filmId);

}
