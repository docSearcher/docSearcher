// default package
// Generated 2016-11-27 13:36:33 by Hibernate Tools 4.3.1.Final
package com.ustc.domain;

import java.util.Date;

/**
 * Picinfo generated by hbm2java
 */
/*@Entity
@Table(name = "picinfo", catalog = "test_02")*/
@SuppressWarnings(value = { "serial" })
public class Picinfo implements java.io.Serializable {

	private Integer picInfoId;
	private Uploadfileinfo uploadfileinfo;
	private String fileName;
	private String fileUrl;
	private String createBy;
	private Date createTime;
	//private SinglePagePptItem singlepagepptitems = new SinglePagePptItem();
	private String fileContent;
	private Integer filePageNumber;
	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public Integer getFilePageNumber() {
		return filePageNumber;
	}

	public void setFilePageNumber(Integer filePageNumber) {
		this.filePageNumber = filePageNumber;
	}

	


	/*@Id
	@Column(name = "picInfo_id", unique = true, nullable = false, length = 50)*/
	public Integer getPicInfoId() {
		return this.picInfoId;
	}

	public void setPicInfoId(Integer picInfoId) {
		this.picInfoId = picInfoId;
	}

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id")*/
	public Uploadfileinfo getUploadfileinfo() {
		return this.uploadfileinfo;
	}

	public void setUploadfileinfo(Uploadfileinfo uploadfileinfo) {
		this.uploadfileinfo = uploadfileinfo;
	}

	//@Column(name = "fileName", length = 100)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	//@Column(name = "fileUrl", length = 100)
	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	//@Column(name = "createBy", length = 50)
	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/*@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", length = 19)*/
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "picinfo")*/
	/*public SinglePagePptItem getSinglepagepptitems() {
		return this.singlepagepptitems;
	}

	public void setSinglepagepptitems(SinglePagePptItem singlepagepptitems) {
		this.singlepagepptitems = singlepagepptitems;
	}*/

}