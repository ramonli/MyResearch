package com.ramonli.sandbox.spring.customschema;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PeopleTest {
	private static Logger logger = LoggerFactory.getLogger(PeopleTest.class);
	private static ApplicationContext appContext;

	@BeforeClass
	public static void init() {
		appContext = new ClassPathXmlApplicationContext("spring-schema.xml");
	}

	@Test
	public void test() {
		Department dep = (Department)appContext.getBean("department");
		People people = dep.getPeople();
		assertEquals("gulu Papa", people.getName());
		assertEquals(2, people.getAddresses().size());
		
		Address add = people.getAddresses().get(0);
		assertEquals("shanghai", add.getCity());
	}

}
