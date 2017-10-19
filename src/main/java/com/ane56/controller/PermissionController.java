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
import com.ane56.service.UserService;
import com.ane56.uitls.HttpResults;
import com.ane56.uitls.UserUtil;
import com.google.common.collect.Lists;

@Controller
public class PermissionController {

	//private Logger log = LoggerFactory.getLogger(getClass());
	
	/*@Autowired
	private RoleService roleService;*/
	@Autowired
	private UserService userService;
	/*@Autowired
	private SchoolService schoolService;
	@Autowired
	private OrganizationService organizationService;*/
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RolePermissionService rolePermissionService;
	
	/*@RequestMapping(value="/permissions/{page}", method=RequestMethod.GET)
	public String index(Model model,@PathVariable("page") String page){
		User user = UserUtil.getUser();
		List<School> schools = user.getAccount().equals("admin") ? 
				schoolService.findAllSchools() : Arrays.asList(schoolService.getSchoolById(user.getSchoolId()));
		model.addAttribute("user", user);
		model.addAttribute("schools", schools);
		return "permission/" + page;
    }
	
	@RequestMapping(value="/permissions/roles", method=RequestMethod.GET)
	public @ResponseBody List<TreeNode> rolesTree(Long pid){
		List<TreeNode> trees = new ArrayList<TreeNode>();
		if(null==pid){
			User user = UserUtil.getUser();
			List<School> schools = user.getAccount().equals("admin") ? 
					schoolService.findAllSchools() : Arrays.asList(schoolService.getSchoolById(user.getSchoolId()));
			for (School school : schools) trees.add(new TreeNode(school.getId()+"_", school.getName(), true, null, 0, null));
			return trees;
		}
		List<Role> roles = roleService.findRolesBySchoolId(pid, null, null, null);
		for (Role role : roles) trees.add(new TreeNode(role.getId()+"", role.getName(), false, null, 1, pid));
		return trees;
    }
	
	@RequestMapping(value="/permissions/orgs", method=RequestMethod.GET)
	public @ResponseBody List<TreeNode> orgsTree(Long pid,Long schoolId){
		List<TreeNode> trees = new ArrayList<TreeNode>();
		Long[] schoolIds = schoolId!=null?new Long[]{schoolId}:new Long[]{};
		if(null==pid&&schoolId==null){
			User user = UserUtil.getUser();
			List<School> schools = user.getAccount().equals("admin") ? 
					schoolService.findAllSchools() : Arrays.asList(schoolService.getSchoolById(user.getSchoolId()));
			schoolIds = new Long[schools.size()];
			for (int i = 0; i < schools.size(); i++) {
				schoolIds[i]= Long.parseLong(""+schools.get(i).getId());
			}
		}
		List<Organization> orgs = organizationService.findOrganizationsByPid(pid, schoolIds);
		for(Organization org : orgs){
			trees.add(new TreeNode(org.getId().toString(), org.getName(), 
					org.getLeaf() == 0 ? true : false, org.getType(), org.getRefType(), org.getRefId()));
		}
		return trees;
    }*/
	
	/*@RequestMapping(value="/permissions/users", method=RequestMethod.GET)
	public @ResponseBody PageList<User> findUsers(Long orgId, String query, Integer current, Integer limit,Integer queryType){
		queryType = Strings.isNullOrEmpty(query)? 1 : 0;
		//queryType =queryType == null?1:queryType;//根据名称模糊查询事 queryType = 0
		PageParam page = new PageParam(current, limit);
		int count = userService.countUsersByOrgId(orgId, queryType, query);
		PageList<User> result = new PageList<>(count, limit);
		if(count>page.getStart())
			result.setList(userService.findUsersByOrgId(orgId, queryType, query,page.getStart(), limit));
		return result;
    }*/
	
	
	@RequestMapping(value="/permissions/pid", method=RequestMethod.GET)
	public @ResponseBody List<TreeNode> findPermissionsByPid(Long pid){
		List<TreeNode> trees = new ArrayList<TreeNode>();
		pid = pid==null?0L:pid;
		List<Permission> list = permissionService.findPermissionsByPId(pid);
		if(UserUtil.getUser().getAccount() .equalsIgnoreCase("admin")){
			for (Permission perm : list) trees.add(new TreeNode(perm.getId()+"", perm.getTitle(), perm.getLeaf()==0?false:true, null, 0, perm.getPid()));
		}else{
			for (Permission perm : list) {
//				if(!perm.getTitle().equals("学校管理")){
					trees.add(new TreeNode(perm.getId()+"", perm.getTitle(), perm.getLeaf()==0?false:true, null, 0, perm.getPid()));
//				}
			}
		}
		
		return trees;
    }
	
	@RequestMapping(value="/rolePermissions", method=RequestMethod.POST)
//	public @ResponseBody HttpResults saveRolePermissions(Integer type,Long refId,Long schoolId
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
//	public @ResponseBody HttpResults findRolePermissionsByRefId(Integer type,Long refId,Long schoolId){
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
