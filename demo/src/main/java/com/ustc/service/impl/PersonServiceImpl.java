package com.ustc.service.impl;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.ustc.dao.PersonDao;
import com.ustc.domain.Person;
import com.ustc.service.PersonService;

@Service("personService")
public class PersonServiceImpl implements PersonService {
	@Autowired
    private PersonDao personDao;
	/*public PersonDao getPersonDao() {
		return personDao;
	}
	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}*/
	@Override
	public void savePerson(Person person) {
		// TODO Auto-generated method stub
		personDao.savePerson(person);
	}

}
