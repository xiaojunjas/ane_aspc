package com.ane56.service;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

import com.ane56.domain.Role;
import com.ane56.domain.RoleUser;

public interface RoleService {

	@GET("/v1/roles/{id}")
	public Role getRoleById(@Path("id") Long id);

	@GET("/v1/roles")
	public List<Role> findRoles(@Query("query") String query,@Query("start") Integer start, @Query("limit") Integer limit);

	@GET("/v1/roles/count")
	public Integer countRoles(@Query("query") String query);

	@POST("/v1/roles")
	public Long saveRole(@Body Role role);

	@PUT("/v1/roles")
	public Integer updateRole(@Body Role role);

	@PATCH("/v1/roles")
	public Integer updateRoleSelected(@Body Role role);
	
	@GET("/v1/userRoles/userId/roleId")
	public List<RoleUser> findRoleUserByRoleId(@Query("userId") Long userId,@Query("roleId") Long roleId);
	
	@POST("/v1/userRoles/batch")
	public Integer saveUserRoles(@Body List<RoleUser> userRoles);
	
	@PUT("/v1/userRoles/userId")
	public Integer delRoleUserByUserId(@Query("delteaIds")Long[] delteaIds,@Query("roleId")Long roleId);
}
