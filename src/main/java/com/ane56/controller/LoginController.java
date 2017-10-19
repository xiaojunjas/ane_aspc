package com.ane56.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ane56.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Model model){
		return "content/login";
    }
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String register(Model model){
		return "content/register";
    }
	
	@RequestMapping(value="/loginUser", method=RequestMethod.GET)
	public @ResponseBody Integer loginUser(String name,String password){
		return userService.loginUser(name,password);
    }
	
	@RequestMapping(value="/createUser", method=RequestMethod.POST)
	public @ResponseBody Integer createUser(String name,String password){
		return userService.createUser(name, password);
    }
}
