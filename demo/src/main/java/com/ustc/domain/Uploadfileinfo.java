// default package
// Generated 2016-11-27 13:36:33 by Hibernate Tools 4.3.1.Final
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


public class Uploadfileinfo implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer fileId;
	private User user;
	private String fileName;
	private String fileSize;
	private String fileFormats;
	private String fileUrl;
	private Date fileUploadtime;
	private Set<Dividefileinfo> dividefileinfos = new HashSet<Dividefileinfo>(0);
	private Set<Picinfo> picinfos = new HashSet<Picinfo>(0);
	private Set<Dividepresspicinfo> dividepresspicinfos = new HashSet<Dividepresspicinfo>(0);

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public String getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	
	public String getFileFormats() {
		return this.fileFormats;
	}

	public void setFileFormats(String fileFormats) {
		this.fileFormats = fileFormats;
	}

	
	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	
	public Date getFileUploadtime() {
		return this.fileUploadtime;
	}

	public void setFileUploadtime(Date fileUploadtime) {
		this.fileUploadtime = fileUploadtime;
	}

	
	public Set<Dividefileinfo> getDividefileinfos() {
		return this.dividefileinfos;
	}

	public void setDividefileinfos(Set<Dividefileinfo> dividefileinfos) {
		this.dividefileinfos = dividefileinfos;
	}

	
	public Set<Picinfo> getPicinfos() {
		return this.picinfos;
	}

	public void setPicinfos(Set<Picinfo> picinfos) {
		this.picinfos = picinfos;
	}

	
	public Set<Dividepresspicinfo> getDividepresspicinfos() {
		return this.dividepresspicinfos;
	}

	public void setDividepresspicinfos(Set<Dividepresspicinfo> dividepresspicinfos) {
		this.dividepresspicinfos = dividepresspicinfos;
	}

	@Override
	public String toString() {
		return "Uploadfileinfo [fileId=" + fileId + ", user=" + user + ", fileName=" + fileName + ", fileSize="
				+ fileSize + ", fileFormats=" + fileFormats + ", fileUrl=" + fileUrl + ", fileUploadtime="
				+ fileUploadtime + ", dividefileinfos=" + dividefileinfos + ", picinfos=" + picinfos
				+ ", dividepresspicinfos=" + dividepresspicinfos + "]";
	}

}
