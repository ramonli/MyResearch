package com.ramonli.sandbox.spring.aop;

public interface ConcurrentService {

	Order bizService(OrderInfo info);
	
	void cancel(String orderId);
}
