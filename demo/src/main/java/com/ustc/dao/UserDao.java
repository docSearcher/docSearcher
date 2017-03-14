package com.ustc.dao;


import com.ustc.domain.User01;

public interface UserDao {
    public void insertUser(User01 user);
    public User01 getUser(String ids);
    public void updateUser(User01 user);
    public boolean deleteUser(String ids);
    public User01 checkLogin(String username,String password);
    public int checkUser(String username);
}
