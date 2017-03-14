package com.ustc.dao.impl;

import org.springframework.stereotype.Repository;

import com.ustc.dao.User_Dao;
import com.ustc.domain.User;

@Repository
public class User_DaoImpl extends BaseDaoImpl<User> implements User_Dao {

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
        saveEntity(user);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
        updateEntity(user);
	}

	@Override
	public int checkUser(User user) {
		// TODO Auto-generated method stub
		String hql = "From User u where u.mobile =?";
		return getQuery(hql, user.getMobile()).uniqueResult()== null ? 0:1;
		
		//return 0;
	}

	@Override
	public User checkLogin(String phone, String password) {
		// TODO Auto-generated method stub
		String hql = "from User u where u.mobile=? and u.password=?";
		return (User) getQuery(hql,phone,password).uniqueResult();
		//return null;
	}
	//SELECT fileinfo.* FROM UploadFileInfo fileinfo LEFT JOIN USER u ON u.`user_id` 
	//=fileinfo.`user_id` WHERE u.user_id='2c96f18358b51db10158b51e2c9a0000';
    //select file.* from UploadFileInfo file left join file.user u where u.user_id=?

	@Override
	public User getUserByToken(String token) {
		// TODO Auto-generated method stub
		String hql = "from User u where u.token =?";
		return (User) getQuery(hql,token).uniqueResult();
	}
}
