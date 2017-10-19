package com.ane56.controller;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ane56.domain.Role;
import com.ane56.service.RoleService;
import com.ane56.uitls.HttpResults;
import com.ane56.uitls.PageList;
import com.ane56.uitls.PageParam;

@Controller
public class RoleController {

	/*@Autowired
	private UserService userService;*/
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
	
	@RequestMapping(value="/roles", method=RequestMethod.POST)
	public @ResponseBody Object saveRole(Role role){
		HttpResults result = new HttpResults();
		Integer count = roleService.countRoles(role.getName());
		if(count>0) {
			result.init("10001","角色名称重复！");
			return result;
		}
		if(null == role.getId()){
			roleService.saveRole(role);
		}else{
			roleService.updateRoleSelected(role);
		} 
		return result;
    }
	
	@RequestMapping(value="/roles/{id}", method=RequestMethod.GET)
	public @ResponseBody Object getRoleById(@PathVariable("id")Long id){
		HttpResults result = new HttpResults();
		Role role = roleService.getRoleById(id);
		result.put("role", role);
		return result;
    }
}
