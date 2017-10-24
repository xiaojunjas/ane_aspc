package com.ane56.controller;

 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ane56.domain.MoviesAddress;
import com.ane56.domain.MoviesFilm;
import com.ane56.service.MoviesAddressService;
import com.ane56.uitls.PageList;
import com.ane56.uitls.PageParam;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * @author 作者 xiaojun
 * @Date 创建时间：2017年10月24日 下午2:00:28
 * @version 影院地址信息
 */
@Controller
public class MoviesAddressController {

	@Autowired
	private MoviesAddressService moviesAddressService;
	
	private Gson gson = new GsonBuilder().create();
	
	@RequestMapping(value="/movies", method=RequestMethod.GET)
	public String movies(Model model){
		return "movies/movies-list";
    }
	
	@RequestMapping(value="/movies-add", method=RequestMethod.GET)
	public String moviesAdd(Model model){
		return "movies/movies-add";
    }
	
	@RequestMapping(value="/movies-edit", method=RequestMethod.GET)
	public String moviesEdit(Model model,Long moviesId){
		model.addAttribute("movies",moviesAddressService.getMoviesAddressById(moviesId));
		return "movies/movies-edit";
    }
	
	@RequestMapping(value="/listMovies", method=RequestMethod.GET)
	public @ResponseBody PageList<MoviesAddress> listMoviesAddress(Integer start,Integer limit,String query){
		PageParam page = new PageParam(start, limit);
		int count = moviesAddressService.countMoviesAddress(query);
		PageList<MoviesAddress> result = new PageList<>(count, limit);
		if(count>page.getStart())
			result.setList(moviesAddressService.listMoviesAddress(page.getStart(), page.getLimit(),query));
		return result;
    }
	
	@RequestMapping(value="/moviesMessage", method=RequestMethod.GET)
	public @ResponseBody Integer moviesMessage(String moviesInfo){
		MoviesAddress movies =gson.fromJson(moviesInfo, MoviesAddress.class);
		if(movies.getId()==null){
			moviesAddressService.createMoviesAddress(movies);
		}else{
			moviesAddressService.editMoviesAddressByMoviesId(movies);
		}
		return 0;
    }
	
	@RequestMapping(value="/deleMovies", method=RequestMethod.GET)
	public @ResponseBody Integer deleMovies(Long moviesId){
		return moviesAddressService.delMoviesAddressByMoviesId(moviesId);
    }
	
	@RequestMapping(value="/findFilmMoviesByMoviesId", method=RequestMethod.GET)
	public @ResponseBody List<MoviesFilm> findFilmMoviesByMoviesId(Long moviesId){
		return moviesAddressService.findFilmMoviesByMoviesId(moviesId,null);
    }
	
	@RequestMapping(value = "/filmMovies", method = RequestMethod.POST)
	public @ResponseBody Integer roleUser(@RequestParam(value = "filmIds[]", required = false) Long[] filmIds,
			@RequestParam(value = "delteaIds[]", required = false) Long[] delteaIds, Long moviesId) {
		int count = 1;
		List<MoviesFilm> moviesFilm = new ArrayList<MoviesFilm>();
		if (filmIds != null) {
			for (Long filmId : filmIds) {
				MoviesFilm moiv = new MoviesFilm();
				moiv.setFilmId(filmId);;
				moiv.setMoviesId(moviesId);
				moviesFilm.add(moiv);
			}
		}
		if (filmIds == null && delteaIds == null) {
			count = 0;// 没有进行操作直接返回
		} else if (delteaIds == null) {
			count = 1;
			moviesAddressService.saveFilmMovies(moviesFilm);
		} else if (filmIds == null) {
			count = 1;
			moviesAddressService.delFilmMoviesByMoviesId(delteaIds);
		} else {
			count = 1;
			for (Long filmId : filmIds) {
				List<MoviesFilm> a = moviesAddressService.findFilmMoviesByMoviesId(moviesId, filmId);
				if (a.size() ==0) {
					moviesAddressService.saveFilmMovies(moviesFilm);
				}
			}
			moviesAddressService.delFilmMoviesByMoviesId(delteaIds);
		}
		return count;
	}
	
}
