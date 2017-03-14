package com.ustc.viewDomain;

import java.util.Date;

import com.ustc.domain.User;
import com.ustc.utils.ConvertDate;
import com.ustc.utils.GloablNames;

public class ViewUser {
	private Integer user_id;	
	private String token;
	private String user_name;
	private Integer gender;	
	private String email;
	private String mobile;
	private String pic_url;
	private String active_id;
	private String password;
	private String create_time;
	private String last_access_at;
	private Integer collection_count;
	private Integer note_count;

	
	public ViewUser(User user){		
		this.user_id =GloablNames.PRE_CODE + user.getUserId();
		this.token = user.getToken();
		this.user_name = user.getUserName();
		this.gender = user.getGenter();
		this.mobile = user.getMobile();
		this.pic_url = user.getPicUrl()==null?GloablNames.SERVERPATH +"123.png":user.getPicUrl();
		this.create_time = ConvertDate.convertDate(user.getCreateTime());
		//this.create_time = String.valueOf(user.getCreateTime().getTime());
		this.last_access_at =ConvertDate.convertDate(user.getUpdateTime()) ;//String.valueOf(user.getUpdateTime().getTime());
		this.collection_count = 5;
		this.note_count = 12;
	}
	
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer genter) {
		this.gender = genter;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPic_url() {
		return pic_url;
	}
	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}
	public String getActive_id() {
		return active_id;
	}
	public void setActive_id(String active_id) {
		this.active_id = active_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getLast_access_at() {
		return last_access_at;
	}

	public void setLast_access_at(String last_access_at) {
		this.last_access_at = last_access_at;
	}

	public Integer getCollection_count() {
		return collection_count;
	}

	public void setCollection_count(Integer collection_count) {
		this.collection_count = collection_count;
	}

	public Integer getNote_count() {
		return note_count;
	}

	public void setNote_count(Integer note_count) {
		this.note_count = note_count;
	}

	
	
}
