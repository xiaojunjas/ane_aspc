package com.ane56.service;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;

import com.ane56.domain.Permission;

public interface PermissionService {

	/*@GET("/v1/permissions/{id}")
	public Permission getPermissionById(@Path("id")Long id);
	*/
	@GET("/v1/permissions/pid")
	public List<Permission> findPermissionsByPId(@Query("pid")Long pid);
	
	@GET("/v1/permissions/user")
	public List<Permission> findPermissions(@Query("userId") Long userId, @Query("path") String path);
}
