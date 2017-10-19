package com.ane56.service;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import com.ane56.domain.Role;

public interface RoleService {

	@GET("/v1/roles/{id}")
	public Role getRoleById(@Path("id") Long id);

	@GET("/v1/roles")
	public List<Role> findRoles(@Query("query") String query,@Query("start") Integer start, @Query("limit") Integer limit);

	@GET("/v1/roles/count")
	public Integer countRoles(@Query("query") String query);

	/*@POST("/v1/roles")
	public Long saveRole(@Body Role role);

	@PUT("/v1/roles")
	public Integer updateRole(@Body Role role);

	@PATCH("/v1/roles")
	public Integer updateRoleSelected(@Body Role role);

	@DELETE("/v1/roles")
	public Integer deleteRole(@Query("id") Long id);
	
	@POST("/v1/userRoles/batch")
	public Integer saveUserRoles(@Body List<UserRole> userRoles);
	
	@DELETE("/v1/userRoles")
	public Integer removeUserRolesById(@Query("id")Long id);
	
	@DELETE("/v1/userRoles/userid/roleid")
	public Integer removeUserRolesByUserIdAndRoleId(@Query("userId")Long userId,@Query("roleId")Long roleId);
	*/

}
