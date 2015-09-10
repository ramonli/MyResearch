package com.ramonli.sandbox.springasync;

public interface OrderFacade {
	
	String facade(String orderId, int waitTime) throws Exception;
}
