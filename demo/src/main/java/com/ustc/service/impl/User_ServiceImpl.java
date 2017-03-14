package com.ustc.service.impl;

import java.util.Date;

import javax.mail.MessagingException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustc.dao.User_Dao;
import com.ustc.domain.User;
import com.ustc.exception.UserAlreadyExistsException;
import com.ustc.exception.UserLoginFailedException;
import com.ustc.service.User_Service;
import com.ustc.utils.GloablMessage;
import com.ustc.utils.SendEmail;

@Service
public class User_ServiceImpl implements User_Service {
	@Autowired
	private User_Dao userDao;

	@Override
	public void regist(User user) {
		// TODO Auto-generated method stub
		String token = null;
		boolean exists = userDao.checkUser(user) > 0;
		if (exists) {			
				// 处理自定义异常
				throw new UserAlreadyExistsException(GloablMessage.USER_NAME_ALREADY_EXISTS);			
		} else {
			token = DigestUtils.md5Hex(System.currentTimeMillis() + user.getUserName());
			user.setToken(token);
			user.setActiveId("1");
			user.setGenter(1);
			user.setPassword(DigestUtils.md5Hex(user.getPassword()));
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			userDao.saveUser(user);
			/**
			 * 处理发送邮件
			 */
			StringBuilder sb = new StringBuilder("点击下面链接激活账号，链接只能使用一次，请尽快激活！<br>");
			sb.append("http://localhost:8080/demo/login?action=activate& email=");
			sb.append(user.getEmail());
			sb.append("");
			SendEmail.send(user.getEmail(), sb.toString());
		}

	}

	@Override
	public User login(String phone, String password) {
		// TODO Auto-generated method stub
		String token = null;
		User user = userDao.checkLogin(phone, password);
		if (user == null) {
			// throw new
			// UserLoginFailedException(GloablMessage.USER_LOGIN_FAILED);
			return null;
		} else {
			token = DigestUtils.md5Hex(System.currentTimeMillis() + user.getUserName());
			user.setToken(token);
			user.setUpdateTime(new Date());
			userDao.updateEntity(user);
		}
		return user;
	}

	@Override
	public void sendActivateMail() {
		// 讲邮箱的相关字段直接设置为“1”
		// TODO Auto-generated method stub
		// userDao.updateUser();
	}

	@Override
	public void processActive() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkRegister(User user) {
		boolean exists = userDao.checkUser(user) > 0;
		if (exists) {
			return false;
		} else
			return exists;
	}

	@Override
	public User getUserToken(String token) {
		// TODO Auto-generated method stub

		User user = userDao.getUserByToken(token);
		if (user != null)
			return user;
		else
			return null;
	}

	public User updatePassword(User user){
		
		user.setUpdateTime(new Date());
		user.setToken("");
		userDao.updateEntity(user);
		return user;
	}
}
