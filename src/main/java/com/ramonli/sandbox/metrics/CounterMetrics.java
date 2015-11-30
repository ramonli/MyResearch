package com.ramonli.sandbox.metrics;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

public class CounterMetrics {
	public static final MetricRegistry metrics = new MetricRegistry();
	private List queue = new LinkedList();

	private final Counter pendingJobs = metrics.counter(name(CounterMetrics.class, "pending-jobs"));

	public CounterMetrics() {
		// final ConsoleReporter reporter =
		// ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
		// reporter.start(1, TimeUnit.SECONDS);
		final SmartSlf4jReporter reporter = SmartSlf4jReporter.forRegistry(metrics).outputTo(LoggerFactory.getLogger("com.ramonli.sandbox")).convertRatesTo(TimeUnit.SECONDS)
		        .convertDurationsTo(TimeUnit.MILLISECONDS).skipIdleMetrics(true).build();
		reporter.start(1, TimeUnit.SECONDS);
	}

	public void addJob(Object job) {
		pendingJobs.inc();
		queue.add(job);
	}

	public Object takeJob() {
		pendingJobs.dec();
		return queue.remove(0);
	}

	public Counter getPendingJobs() {
		return this.pendingJobs;
	}

	public static void main(String[] args) throws Exception {
		CounterMetrics counterMetrics = new CounterMetrics();
		counterMetrics.addJob(new Object());
		Thread.currentThread().sleep(2 * 1000);
		counterMetrics.takeJob();
		Thread.currentThread().sleep(2 * 1000);
		counterMetrics.addJob(new Object());
		Thread.currentThread().sleep(2 * 1000);
		counterMetrics.addJob(new Object());
		Thread.currentThread().sleep(2 * 1000);
		counterMetrics.addJob(new Object());
		Thread.currentThread().sleep(2 * 1000);
		counterMetrics.addJob(new Object());
		Thread.currentThread().sleep(2 * 1000);
		counterMetrics.addJob(new Object());
		Thread.currentThread().sleep(2 * 1000);
		counterMetrics.addJob(new Object());
		Thread.currentThread().sleep(2 * 1000);
		counterMetrics.addJob(new Object());
		Thread.currentThread().sleep(2 * 1000);
		
		System.out.println(counterMetrics.getPendingJobs());
	}
}
