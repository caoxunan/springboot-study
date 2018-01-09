package com.cxn.model;

import java.io.Serializable;

public class StudyTableModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String text;
	private Integer flag;
	private String createTime;
	private String updateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "TestTableModel [id=" + id + ", text=" + text + ", flag=" + flag + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
	
}
