package com.ustc.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ustc.dao.PersonDao;
import com.ustc.domain.Person;
@Repository//("personDao")
public class PersonDaoImpl  implements PersonDao {
	@Autowired  
    private SessionFactory sessionFactory;  
	/*public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}*/
	@Override
	public void savePerson(Person person) {
		sessionFactory.getCurrentSession().save(person);
	}

}
