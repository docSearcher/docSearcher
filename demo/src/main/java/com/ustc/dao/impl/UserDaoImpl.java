package com.ustc.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ustc.dao.UserDao;
import com.ustc.domain.User01;
@Repository("userDao")
public class UserDaoImpl implements UserDao {
    @Autowired
	private SessionFactory sessionFactory;
    
    public Session getSession(){
    	return sessionFactory.getCurrentSession();
    }
	@Override
	public void insertUser(User01 user) {
		// TODO Auto-generated method stub
       getSession().save(user);
    }

	@Override
	public User01 getUser(String ids) {
		return (User01) getSession().get("id", ids);
	}

	//@Override
	public void updateUser(User01 user) {
		// TODO Auto-generated method stub
        //getSession().get("id", user.getUid());
		getSession().update(user);
	}
   
	@Override
	public boolean deleteUser(String ids) {
		// TODO Auto-generated method stub
		String hql = "delete from User where uid =?";
		return getSession().createQuery(hql).setParameter(0, ids).executeUpdate() >0;

	}

	@Override
	public User01 checkLogin(String username, String password) {
		// TODO Auto-generated method stub
       String hql ="from User where uname =? and upassword =?";
       Query query =  getSession().createQuery(hql);
       query.setParameter(0, username);
       query.setParameter(1, password);
       
       return (User01) query.uniqueResult();
	}
	@Override
	public int checkUser(String username) {
		String sql = "from User  where uname =?";
		Query query = getSession().createQuery(sql);
		query.setParameter(0, username);
		return  query.uniqueResult()== null?0:(int)query.uniqueResult();
	}
	
	
    
}
