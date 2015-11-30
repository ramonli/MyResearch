package com.ramonli.sandbox.spring.aop;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConcurrentServiceTest {
	private static Logger logger = LoggerFactory.getLogger(ConcurrentServiceTest.class);
	private static ConcurrentService concurrentService;

	@BeforeClass
	public static void init() {
		ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("spring-aop.xml");
		concurrentService = (ConcurrentService) springContext.getBean("defaultConcurrentService");
	}

	@Ignore
	@Test
	public void testBizService() {
		logger.debug("+----------------------------------------------------------+");
		logger.debug("+ testBizService()                                         +");
		logger.debug("+----------------------------------------------------------+");
		
		OrderInfo info = new OrderInfo();
		info.setCount(2);
		info.setSerialNo("1281231254");
		concurrentService.bizService(info);
	}

	@Test
	public void testCancelOrder() {
		logger.debug("+----------------------------------------------------------+");
		logger.debug("+ testCancelOrder()                                        +");
		logger.debug("+----------------------------------------------------------+");
		
		concurrentService.cancel("78124123");
	}
}
