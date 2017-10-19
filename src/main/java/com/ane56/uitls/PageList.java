package com.ane56.uitls;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 带分页信息集合辅助类
 * @author tangyf
 * @data 2017年1月18日 下午3:26:34
 * @param <T>
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class PageList<T> {
	
	private Integer count;//总数据条数
	private Integer current;//当前页
	private Integer pages;//总页数
	private Integer limit = 10;//页面显示数据条数
	private List<T> list = Lists.newArrayList();
	private Map<String,Object> attr = Maps.newHashMap();
	
	private void setPages(){
		if(count<1) this.pages = 0;
		else if(limit > count) this.pages = 1;
		else this.pages = Math.round(count/limit)+((count%limit)>0?1:0);
	}
	
	public PageList(Integer count) {
		this.count = count;
		setPages();
	}
	
	public PageList(Integer count, Integer limit) {
		this.count = count;
		this.limit = limit;
		setPages();
	}
	
	public PageList(Integer count, Integer limit, List<T> list) {
		this.count = count;
		this.limit = limit;
		this.list = list;
		setPages();
	}
	
	public PageList(Integer count, Integer current,Integer limit) {
		this.count = count;
		this.current = current;
		this.limit = limit;
		setPages();
	}
	
	public PageList(Integer count, Integer current,Integer limit, List<T> list) {
		this.count = count;
		this.current = current;
		this.limit = limit;
		this.list = list;
		setPages();
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getCurrent() {
		return current;
	}
	public void setCurrent(Integer current) {
		this.current = current;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public Map<String, Object> getAttr() {
		return attr;
	}
	public void setAttr(Map<String, Object> attr) {
		this.attr = attr;
	}
	public Integer getPages() {
		return pages;
	}
	
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public void put(String key,Object value ){
		this.attr.put(key, value);
	}
	
	
	
	
}
