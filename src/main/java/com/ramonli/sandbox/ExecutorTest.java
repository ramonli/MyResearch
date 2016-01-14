package com.ramonli.sandbox;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorTest {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(new Runnable() {

			@Override
			public void run() {
				while (true) {
					/**
					 * with shutdownNow, if you comment out the lines within the while loop, you will get Still waiting after 100ms: calling
					 * System.exit(0)... because the interruption is not handled by the running task any longer.
					 */
					// if (Thread.currentThread().isInterrupted()) {
		            // System.out.println("interrupted");
		            // break;
		            // }
				}
			}
		});

		/**
		 * with shutdown, the output is Still waiting after 100ms: calling System.exit(0)... because the running task is not interrupted and continues
		 * to run.
		 */
		// executor.shutdown();
		/**
		 * with shutdownNow, the output is interrupted and Exiting normally... because the running task is interrupted, catches the interruption and
		 * then stops what it is doing (breaks the while loop).
		 */
		executor.shutdownNow();
		System.out.println(executor.isShutdown());
		if (!executor.awaitTermination(100, TimeUnit.MICROSECONDS)) {
			System.out.println("Still waiting after 100ms: calling System.exit(0)...");
			// System.exit(0);
		}
		System.out.println("Exiting normally...");
	}
}
