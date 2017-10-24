package com.ane56.controller;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ane56.domain.Film;
import com.ane56.service.FilmService;
import com.ane56.uitls.PageList;
import com.ane56.uitls.PageParam;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * @author 作者 肖军
 * @Date 创建时间：2017年10月24日 下午2:00:28
 * @version 电影名称
 */
@Controller
public class FilmController {

	@Autowired
	private FilmService filmService;
	
	private Gson gson = new GsonBuilder().create();
	
	@RequestMapping(value="/film", method=RequestMethod.GET)
	public String film(Model model){
		return "film/film-list";
    }
	
	@RequestMapping(value="/film-add", method=RequestMethod.GET)
	public String filmAdd(Model model){
		return "film/film-add";
    }
	
	@RequestMapping(value="/film-edit", method=RequestMethod.GET)
	public String filmEdit(Model model,Long filmId){
		model.addAttribute("film",filmService.getFilmById(filmId));
		return "film/film-edit";
    }
	
	@RequestMapping(value="/listFilm", method=RequestMethod.GET)
	public @ResponseBody PageList<Film> findFilm(Integer start,Integer limit,String query){
		PageParam page = new PageParam(start, limit);
		int count = filmService.countFilm(query);
		PageList<Film> result = new PageList<>(count, limit);
		if(count>page.getStart())
			result.setList(filmService.listFilm(page.getStart(), page.getLimit(),query));
		return result;
    }
	
	@RequestMapping(value="/filmMessage", method=RequestMethod.GET)
	public @ResponseBody Integer filmMessage(String filmInfo){
		Film film =gson.fromJson(filmInfo, Film.class);
		if(film.getId()==null){
			filmService.createFilm(film);
		}else{
			filmService.editFilmByFilmId(film);
		}
		return 0;
    }
	
	@RequestMapping(value="/deleFilm", method=RequestMethod.GET)
	public @ResponseBody Integer deleFilm(Long filmId){
		return filmService.delFilmByFilmId(filmId);
    }
	
}
