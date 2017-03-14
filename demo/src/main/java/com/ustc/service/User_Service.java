package com.ustc.service;

import com.ustc.domain.User;

public interface User_Service {
    /**
     * 注册操作
     * @param user
     */
	public void regist(User user);
    
    
    /**
     * 登陆操作
     * @param name
     * @param password
     * @return
     */
    public User login(String phone,String password);
    /**
     * 发送激活邮件
     */
    public void sendActivateMail();
    /**
     * 邮件激活操作
     */
    public void processActive();
    
    public boolean checkRegister(User user);
    
    public User getUserToken(String token);
    
    //修改密码
    public User updatePassword(User user);
}
