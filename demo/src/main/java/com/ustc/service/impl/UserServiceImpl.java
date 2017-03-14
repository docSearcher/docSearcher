package com.ustc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustc.dao.UserDao;
import com.ustc.domain.User01;
import com.ustc.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private  UserDao userDao;
	@Override
	public void regist(User01 user) {
		//对用户名做一个判断，如果存在的话，抛出一个自定义的异常
		//如果用户不存在，则可以进行注册
		boolean exists = userDao.checkUser(user.getUname())>0;
		if(exists){
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		userDao.insertUser(user);
	}

	@Override
	public User01 login(String username, String password)  {
		//注册时候对密码进行加密处理，反正进行登陆判断的时候要对用户的密码进行加密的比较
		//登陆成功要返回，用于存入cookie，然后给用户使用
        User01 user = userDao.checkLogin(username, password);
        if(user == null){
        	try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return user;
	}
	
	@Override
	public boolean delete(String ids){
		
		return userDao.deleteUser(ids);
	}
	
	public void update(User01 user){
		userDao.updateUser(user);
	}

}
