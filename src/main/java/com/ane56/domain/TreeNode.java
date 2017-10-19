package com.ane56.domain;

public class TreeNode {
	
	private String id;
	private String text;
	private boolean children;
	private String otype;
	private int refType;
	private Long refId;
	
	public TreeNode(String id, String text, boolean children, String otype, int refType, Long refId) {
		this.id = id;
		this.text = text;
		this.children = children;
		this.otype = otype;
		this.refType = refType;
		this.refId = refId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChildren() {
		return children;
	}

	public void setChildren(boolean children) {
		this.children = children;
	}

	public String getOtype() {
		return otype;
	}

	public void setOtype(String otype) {
		this.otype = otype;
	}

	public int getRefType() {
		return refType;
	}

	public void setRefType(int refType) {
		this.refType = refType;
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}
	
}
