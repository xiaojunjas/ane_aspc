package com.ane56.service;


import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Query;

import com.ane56.domain.User;

public interface UserService {
	
	@GET("/v1/user/account")
	public User getUserByAccount(@Query("account")String account);
	
	@GET("/v1/users")
	public List<User> listUser(@Query("start") Integer start,@Query("limit") Integer limit,@Query("query")String query);
	
	@GET("/v1/users/count")
	public Integer countListUser(@Query("query")String query);
	
	@DELETE("/v1/user/userId")
	public Integer delUserByUserId(@Query("userId")Long userId);
	
	@GET("/v1/user/id")
	public User getUserByUserId(@Query("userId")Long userId);
	
	@PUT("/v1/user/userId")
	public Integer editUserByUserId(@Body User user);
	
}
