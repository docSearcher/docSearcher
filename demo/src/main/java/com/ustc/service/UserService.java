package com.ustc.service;

import com.ustc.domain.User01;

public interface UserService {
	
	public void regist(User01 user);
	
	public User01 login(String username,String password);
	
	public boolean delete(String ids);
	
	public void update(User01 user);

}
