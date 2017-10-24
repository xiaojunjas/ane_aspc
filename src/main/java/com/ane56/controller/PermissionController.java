package com.ane56.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ane56.domain.Permission;
import com.ane56.domain.RolePermission;
import com.ane56.domain.TreeNode;
import com.ane56.service.PermissionService;
import com.ane56.service.RolePermissionService;
import com.ane56.uitls.HttpResults;
import com.ane56.uitls.UserUtil;
import com.google.common.collect.Lists;

@Controller
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@RequestMapping(value="/permissions/pid", method=RequestMethod.GET)
	public @ResponseBody List<TreeNode> findPermissionsByPid(Long pid){
		List<TreeNode> trees = new ArrayList<TreeNode>();
		pid = pid==null?0L:pid;
		List<Permission> list = permissionService.findPermissionsByPId(pid);
		if(UserUtil.getUser().getAccount() .equalsIgnoreCase("admin")){
			for (Permission perm : list) trees.add(new TreeNode(perm.getId()+"", perm.getTitle(), perm.getLeaf()==0?false:true, null, 0, perm.getPid()));
		}else{
			for (Permission perm : list) {
				trees.add(new TreeNode(perm.getId()+"", perm.getTitle(), perm.getLeaf()==0?false:true, null, 0, perm.getPid()));
			}
		}
		
		return trees;
    }
	
	@RequestMapping(value="/rolePermissions", method=RequestMethod.POST)
	public @ResponseBody HttpResults saveRolePermissions(Integer type,Long refId
			,@RequestParam(value = "permIds[]",required = false) Long[] permIds){
		HttpResults result = new HttpResults();
		if(permIds!=null && permIds.length>0){
			List<RolePermission> oldList = rolePermissionService.findRolePermissions(type, refId);
			List<Long> oldPermIds = oldList.stream().collect(Collectors.mapping(RolePermission::getPermissionId, Collectors.toList()));
			List<RolePermission> list = Lists.newArrayList();
			for (Long permId : permIds) {
				if(oldPermIds.contains(permId)) oldPermIds.remove(permId);
				else list.add(new RolePermission(permId, type, refId , 0));
			}
			if(oldPermIds.size()>0) {
				Long[] removeIds =oldPermIds.toArray(new Long[]{});
				rolePermissionService.removeRolePermissionsByRefId(removeIds, type, refId);
			}
			if(list.size()>0)rolePermissionService.saveRolePermissionBatch(list);
		}else{
			List<RolePermission> oldList = rolePermissionService.findRolePermissions( type, refId);
			List<Long> oldPermIds = oldList.stream().collect(Collectors.mapping(RolePermission::getPermissionId, Collectors.toList()));
			if(oldPermIds.size()>0) {
				Long[] removeIds =oldPermIds.toArray(new Long[]{});
				rolePermissionService.removeRolePermissionsByRefId(removeIds, type, refId);
			}
		}
		return result;
    }
	
	@RequestMapping(value="/rolePermissions/refid", method=RequestMethod.GET)
	public @ResponseBody HttpResults findRolePermissionsByRefId(Integer type,Long refId){
		HttpResults result = new HttpResults();
		List<RolePermission> list = rolePermissionService.findRolePermissions(type, refId);
		List<Long> permIds = list.stream().collect(Collectors.mapping(RolePermission::getPermissionId, Collectors.toList()));
		result.put("permIds", permIds);
		return result;
    }
	
	@RequestMapping(value="/permissions", method=RequestMethod.GET)
	public @ResponseBody List<Permission> findPermissions(){
		return UserUtil.getPermissions();
	}
	
}
