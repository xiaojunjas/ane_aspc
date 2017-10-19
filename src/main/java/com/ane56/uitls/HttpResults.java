package com.ane56.uitls;

import java.util.Map;

import com.google.common.collect.Maps;

public class HttpResults {
	
	private String status = "10000";//请求状态：请求成功
	private String message = "请求成功";//提示语
	private Map<String,Object> attrs = Maps.newHashMap();
	
	public HttpResults() { }
	
	public void init(String status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public void put(String key,Object value){
		this.attrs.put(key, value);
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, Object> getAttrs() {
		return attrs;
	}
	public void setAttrs(Map<String, Object> attrs) {
		this.attrs = attrs;
	}
	
}
