package com.ramonli.sandbox.springasync;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class DefaultOrderFacadeTest {
	private static Logger logger = LoggerFactory.getLogger(DefaultOrderFacadeTest.class);
	private static OrderFacade orderFacade;
	private static JdbcTemplate jdbcTemplate;

	@BeforeClass
	public static void init() {
		ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("spring.xml");
		orderFacade = (OrderFacade) springContext.getBean("defaultOrderFacade");
		logger.debug("aop proxy: {}", AopUtils.isAopProxy(orderFacade));
		logger.debug("jdk proxy: {}", AopUtils.isJdkDynamicProxy(orderFacade));
		jdbcTemplate = (JdbcTemplate) springContext.getBean("jdbcTemplate");
	}

	@Test
	public void testFacade() throws Exception {
		String resp = orderFacade.facade("xixi", 4);
		logger.debug("response is {}", resp);
	}

}
