package com.ane56.uitls;

import java.io.Serializable;

/**
 * 
 * 分页参数对象
 *
 */
@SuppressWarnings("serial")
public class PageParam implements Serializable {
	
	private Integer start = 0;    //分页起始数
	private Integer limit = 10;	  //页面数据显示条数
	
	public PageParam() {
		
	}
	public PageParam(Integer start,Integer limit) {
		this.start = start * limit;
		this.limit = limit;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	
	

	
	

}
