package com.ramonli.sandbox.spring.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("defaultConcurrentService")
public class DefaultConcurrentService implements ConcurrentService {
	private Logger logger = LoggerFactory.getLogger(DefaultConcurrentService.class);
	
	@Override
	public Order bizService(OrderInfo info) {
		logger.debug("bizService:: Enter");
		Order order = new Order();
		order.setCount(info.getCount());
		order.setSerialNo(info.getSerialNo());
		order.setResult("OK");
		return order;
	}

	@Idempotent
	@Override
    public void cancel(String orderId) {
		logger.debug("cancel the order with id={}", orderId);
    }

}
