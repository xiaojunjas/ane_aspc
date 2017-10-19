package com.ane56.service;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

import com.ane56.domain.RolePermission;

public interface RolePermissionService {

	@POST("/v1/rolePermissions")
	public Long saveRolePermission(@Body RolePermission rolePermission);
	
	@POST("/v1/rolePermissions/batch")
	public Long saveRolePermissionBatch(@Body List<RolePermission> list);
	
	@GET("/v1/rolePermissions")
	public List<RolePermission> findRolePermissions(@Query("type")Integer type,@Query("refIds")Long... refId);
	
	@DELETE("/v1/rolePermissions")
	public Integer removeRolePermissionsByRefId(@Query("permIds")Long[] permIds,@Query("type")Integer type,@Query("refId")Long refId);
	
}
