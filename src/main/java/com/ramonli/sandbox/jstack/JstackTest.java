package com.ramonli.sandbox.jstack;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class JstackTest {
	private static BlockingQueue<Long> queue = new LinkedBlockingQueue<Long>();

	public static void main(String[] args) throws Exception {
		ExecutorService pool = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			pool.execute(new Runnable() {

				@Override
				public void run() {
					while (true) {
						try {
							Long item = queue.take();
							System.out.println(Thread.currentThread().getName() + " - " + item + "");
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				}

			});
		}
		pool.shutdown();

		long index = 0;
		while (true) {
			Thread.sleep(1 * 1000);
			queue.put((long)index++);
		}
	}
}
