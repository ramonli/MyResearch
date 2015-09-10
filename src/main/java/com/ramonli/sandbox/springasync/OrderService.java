package com.ramonli.sandbox.springasync;

import java.util.concurrent.Future;

public interface OrderService {

	String order(String req);
	
	Future<String> query(int time);
}
