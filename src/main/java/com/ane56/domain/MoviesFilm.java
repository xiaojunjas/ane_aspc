package com.ane56.domain;


public class MoviesFilm {
	
	private Long id;
	private Long filmId;
	private Long moviesId;
	private int isDeleted;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFilmId() {
		return filmId;
	}
	public void setFilmId(Long filmId) {
		this.filmId = filmId;
	}
	public Long getMoviesId() {
		return moviesId;
	}
	public void setMoviesId(Long moviesId) {
		this.moviesId = moviesId;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	 
}
