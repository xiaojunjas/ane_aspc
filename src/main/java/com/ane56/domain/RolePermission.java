package com.ane56.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RolePermission {

	private Long id;
	private Long permissionId;
	private Integer type;
	private Long refId;
	private Integer isDeleted;
	
	public RolePermission() {
		// TODO Auto-generated constructor stub
	}
	
	public RolePermission(Long permissionId, Integer type, Long refId, Integer isDeleted) {
		this.permissionId = permissionId;
		this.type = type;
		this.refId = refId;
		this.isDeleted = isDeleted;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getRefId() {
		return refId;
	}
	public void setRefId(Long refId) {
		this.refId = refId;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
}
