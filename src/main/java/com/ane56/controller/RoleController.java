package com.ane56.controller;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ane56.domain.Role;
import com.ane56.service.RoleService;
import com.ane56.service.UserService;
import com.ane56.uitls.PageList;
import com.ane56.uitls.PageParam;

@Controller
public class RoleController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/role", method=RequestMethod.GET)
	public String index(Model model){
		return "user/role-list";
    }
	
	@RequestMapping(value="/roles", method=RequestMethod.GET)
	public @ResponseBody PageList<Role> roles(Integer start,Integer limit,String query){
		PageParam page = new PageParam(start, limit);
		Integer count = roleService.countRoles(query);
		PageList<Role> data = new PageList<>(count, limit);
		if(count>page.getStart()) data.setList(roleService.findRoles(query,page.getStart(), page.getLimit()));
		return data;
    }
}
