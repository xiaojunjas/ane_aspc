package com.ane56.domain;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Permission implements GrantedAuthority {
	
	private static final long serialVersionUID = 873629882503586074L;
	
	private Long id;
	private String code;
	private String title;
	private String url;//访问路径
	private Integer type;//类型 0 模块 1功能项
	private Long pid;
	private Integer leaf;//有无下级菜单(1有，0无)
	private String path;//菜单上级关联关系
	private Integer sort;
	private Integer isDeleted;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Integer getLeaf() {
		return leaf;
	}
	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	@Override
	public String getAuthority() {
		return null;
	}
	
	@Override
	public String toString() {
		return "Permission [id=" + id + ", code=" + code + ", title=" + title + ", url=" + url + ", type=" + type
				+ ", pid=" + pid + ", leaf=" + leaf + ", path=" + path + ", sort=" + sort + ", isDeleted=" + isDeleted
				+ "]";
	}
	
}
