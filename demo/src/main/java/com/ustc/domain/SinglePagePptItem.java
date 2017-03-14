package com.ustc.domain;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings(value = { "serial" })
public class SinglePagePptItem implements java.io.Serializable {

	private Integer itemId;
	private Dividefileinfo dividefileinfo;
	private Dividepresspicinfo dividepresspicinfo;
	private Picinfo picinfo;
	private Integer pptPageNumber;
	private String pptFileName;
	private String itemContent;
	private Date createTime;
	private String createBy;
	private Set<User> user;
	
	/*
	 * 2017-2-18添加字段
	 */
	private Date updateTime;
	private String updateBy;	
	private String filePath;
	//private Set<UserPptitem> userPptitems = new HashSet<UserPptitem>(0);


	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Dividefileinfo getDividefileinfo() {
		return this.dividefileinfo;
	}

	public void setDividefileinfo(Dividefileinfo dividefileinfo) {
		this.dividefileinfo = dividefileinfo;
	}

	public Dividepresspicinfo getDividepresspicinfo() {
		return this.dividepresspicinfo;
	}

	public void setDividepresspicinfo(Dividepresspicinfo dividepresspicinfo) {
		this.dividepresspicinfo = dividepresspicinfo;
	}

	public Picinfo getPicinfo() {
		return this.picinfo;
	}

	public void setPicinfo(Picinfo picinfo) {
		this.picinfo = picinfo;
	}

	public Integer getPptPageNumber() {
		return this.pptPageNumber;
	}

	public void setPptPageNumber(Integer pptPageNumber) {
		this.pptPageNumber = pptPageNumber;
	}

	public String getPptFileName() {
		return this.pptFileName;
	}

	public void setPptFileName(String pptFileName) {
		this.pptFileName = pptFileName;
	}

	public String getItemContent() {
		return this.itemContent;
	}

	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


}
