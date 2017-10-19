package com.ane56.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ane56.domain.User;
import com.ane56.service.UserService;
import com.ane56.uitls.PageList;
import com.ane56.uitls.PageParam;
import com.ane56.uitls.UserUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private Gson gson = new GsonBuilder().create();
	
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute("user",UserUtil.getUser());
		return "user/user-list";
    }
	
	@RequestMapping(value="/user-edit", method=RequestMethod.GET)
	public String userEdit(Model model,Long userId){
		model.addAttribute("userId",userId);
		return "user/user-edit";
    }
	
	
	@RequestMapping(value="/listUser", method=RequestMethod.GET)
	public @ResponseBody PageList<User> listUser(Integer start,Integer limit,String query){
//		int a;
//		a = (sex =="" || sex == null) ? 0 : sex.equals("男") ? 1: 2;
		PageParam page = new PageParam(start, limit);
		int count = userService.countListUser(query);
		PageList<User> result = new PageList<>(count, limit);
		if(count>page.getStart())
			result.setList(userService.listUser(page.getStart(), page.getLimit(),query));
		return result;
    }
	
	@RequestMapping(value="/delUserByUserId", method=RequestMethod.GET)
	public @ResponseBody Integer delUserByUserId(Long userId){
		return userService.delUserByUserId(userId);
    }
	
	@RequestMapping(value="/editUserByUserId", method=RequestMethod.GET)
	public @ResponseBody Integer editUserByUserId(String userInfo){
		User user =gson.fromJson(userInfo, User.class);
		return userService.editUserByUserId(user);
    }
	
	@RequestMapping(value="/getUserByUserId", method=RequestMethod.GET)
	public @ResponseBody User getUserByUserId(Long userId){
		return userService.getUserByUserId(userId);
    }
	
}
