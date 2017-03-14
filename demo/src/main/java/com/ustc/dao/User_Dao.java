package com.ustc.dao;

import com.ustc.domain.User;

public interface User_Dao extends BaseDao<User>{
    
	/**
	 * 保存用户
	 * @param user
	 */
	public void saveUser(User user);
    /**
     * 更新用户操作
     * @param user
     */
    public void updateUser(User user);
    /**
     *系统是否已存在用户
     * @param user
     * @return
     */
    public int checkUser(User user);
    /**
     * 验证用户名和密码
     * @param name
     * @param password
     * @return
     */
    public User checkLogin(String name,String password);
    
    public User getUserByToken(String token);
    
}
