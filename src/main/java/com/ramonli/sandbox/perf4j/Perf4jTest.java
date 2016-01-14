package com.ramonli.sandbox.perf4j;

import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;

/**
 * Use <code>LogParser</code> to provide the main method for reading a log of StopWatch output and generating statistics and graphs from that output.
 * Run "java -jar pathToPerf4jJar --help" for instructions.
 */
public class Perf4jTest {
	public static void main(String[] args) throws Exception {
		String tag = "perf4j";
		StopWatch stopWatch = new Slf4JStopWatch();
		// for (int i = 0; i < 1000000; i++) {
		// // ... execute code here to be timed
		// if (i % 100000 == 0) {
		// tag = "perf4j" + i;
		// }
		// stopWatch.stop(tag, "custom message " + i);
		// }

		stopWatch.lap("1");
		Thread.sleep(1 * 1000);
		stopWatch.lap("2");
		Thread.sleep(2 * 1000);
		stopWatch.lap("3");
		Thread.sleep(3 * 1000);
		stopWatch.lap("4");
	}
}
