package com.ramonli.sandbox.springasync;

import java.util.concurrent.Future;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class DefaultOrderServiceTest {
	private Logger logger = LoggerFactory.getLogger(DefaultOrderServiceTest.class);
	private static OrderService orderService;
	private static JdbcTemplate jdbcTemplate;

	@BeforeClass
	public static void init() {
		ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("spring-async.xml");
		orderService = (OrderService) springContext.getBean("defaultOrderService");
		jdbcTemplate = (JdbcTemplate) springContext.getBean("jdbcTemplate");
	}

	@Test
	public void testOrder() throws Exception {
		this.orderService.order("menu pls");
		logger.debug("call query service.");
		Future<String> future = this.orderService.query(5);
		logger.debug("finish calling query service.");
		logger.debug("Result is {}", future.get());
		logger.debug("got result from query service.");
	}

}
