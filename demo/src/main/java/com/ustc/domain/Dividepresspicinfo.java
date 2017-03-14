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

/*@Entity
@Table(name = "dividepresspicinfo", catalog = "test_02")*/
@SuppressWarnings("serial")
public class Dividepresspicinfo implements java.io.Serializable {

	private Integer pressPicId;
	private Uploadfileinfo uploadfileinfo;
	private String filename;
	private String fileUrl;
	private String createBy;
	private Date createTime;
	private SinglePagePptItem singlepagepptitems;

	/*@Id
	@Column(name = "pressPic_id", unique = true, nullable = false, length = 50)*/
	public Integer getPressPicId() {
		return this.pressPicId;
	}

	public void setPressPicId(Integer pressPicId) {
		this.pressPicId = pressPicId;
	}

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id")*/
	public Uploadfileinfo getUploadfileinfo() {
		return this.uploadfileinfo;
	}

	public void setUploadfileinfo(Uploadfileinfo uploadfileinfo) {
		this.uploadfileinfo = uploadfileinfo;
	}

	//@Column(name = "filename", length = 100)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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

	//@OneToMany(fetch = FetchType.LAZY, mappedBy = "dividepresspicinfo")
	public SinglePagePptItem getSinglepagepptitems() {
		return this.singlepagepptitems;
	}

	public void setSinglepagepptitems(SinglePagePptItem singlepagepptitems) {
		this.singlepagepptitems = singlepagepptitems;
	}

}
