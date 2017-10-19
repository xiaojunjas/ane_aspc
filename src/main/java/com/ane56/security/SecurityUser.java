package com.ane56.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.ane56.domain.User;

public class SecurityUser extends org.springframework.security.core.userdetails.User{

	private static final long serialVersionUID = 4231489117252871269L;
	
	private User user;
	
	public SecurityUser(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getAccount(), user.getPassword(), true, true, true, true, authorities);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
