package com.ane56.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ane56.domain.Role;
import com.ane56.domain.RoleUser;
import com.ane56.service.RoleService;
import com.ane56.uitls.HttpResults;
import com.ane56.uitls.PageList;
import com.ane56.uitls.PageParam;

@Controller
public class RoleController {

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
	
	@RequestMapping(value="/findRoleUserByRoleId", method=RequestMethod.GET)
	public @ResponseBody List<RoleUser> findRoleUserByRoleId(Long roleId){
		return roleService.findRoleUserByRoleId(null,roleId);
    }
	
	@RequestMapping(value = "/roleUser", method = RequestMethod.POST)
	public @ResponseBody Integer roleUser(@RequestParam(value = "userIds[]", required = false) Long[] userIds,
			@RequestParam(value = "delteaIds[]", required = false) Long[] delteaIds, Long roleId) {
		int count = 1;
		List<RoleUser> roles = new ArrayList<RoleUser>();
		if (userIds != null) {
			for (Long userId : userIds) {
				RoleUser role = new RoleUser();
				role.setUserId(userId);
				role.setRefId(roleId);
				role.setType(1);
				roles.add(role);
			}
		}
		if (userIds == null && delteaIds == null) {
			count = 0;// 没有进行操作直接返回
		} else if (delteaIds == null) {
			count = 1;
			roleService.saveUserRoles(roles);
		} else if (userIds == null) {
			count = 1;
			roleService.delRoleUserByUserId(delteaIds,roleId);
		} else {
			count = 1;
			for (Long userId : userIds) {
				List<RoleUser> a = roleService.findRoleUserByRoleId(roleId, userId);
				if (a.size() ==0) {
					roleService.saveUserRoles(roles);
				}
			}
			roleService.delRoleUserByUserId(delteaIds,roleId);
		}
		return count;
	}
}
