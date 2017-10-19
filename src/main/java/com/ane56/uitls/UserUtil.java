package com.ane56.uitls;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ane56.domain.Permission;
import com.ane56.domain.User;
import com.ane56.security.SecurityUser;
import com.google.common.collect.Lists;

public class UserUtil {

	public static User getUser(){
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUser();
	}
	
	public static List<Permission> getPermissions(){
		 Collection<GrantedAuthority> authorities = ((SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities();
		 List<Permission> permissions = Lists.newArrayList();
		 if(authorities !=null && authorities.size()>0){
			 for (GrantedAuthority grantedAuthority : authorities) {
				 permissions.add((Permission)grantedAuthority);
			 }
		 }
		 return permissions;
	}
}
