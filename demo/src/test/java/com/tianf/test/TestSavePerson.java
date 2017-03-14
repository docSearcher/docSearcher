package com.tianf.test;

import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ustc.domain.Person;
import com.ustc.service.PersonService;
public class TestSavePerson {
	private static ApplicationContext context;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("hello");
		context=new ClassPathXmlApplicationContext(new String[]{"spring-ApplicationContext.xml","spring-Hibernate.xml"});
	}
	///
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	@Test
	public void testSavePerson(){
		PersonService personService = (PersonService)context.getBean("personService");
		Person person = new Person();
		person.setPname("踏实哥");
		//person.setPid(4l);
		personService.savePerson(person);
		System.out.println("添加成功");
	}
}
