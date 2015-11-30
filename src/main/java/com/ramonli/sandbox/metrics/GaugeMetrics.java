package com.ramonli.sandbox.metrics;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;

public class GaugeMetrics {
	public static final MetricRegistry metrics = new MetricRegistry();
	private List queue = new LinkedList();

//	private final Counter pendingJobs = metrics.counter(name(CounterMetrics.class, "pending-jobs"));

	public GaugeMetrics() {
		final ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
		reporter.start(1, TimeUnit.SECONDS);
		
		metrics.register(MetricRegistry.name(GaugeMetrics.class, " queue", "size"), new Gauge<Integer>() {
			@Override
			public Integer getValue() {
				return queue.size();
			}
		});
	}

	public void addJob(Object job) {
		this.queue.add(new Object());
	}

	public static void main(String[] args) throws Exception {
		GaugeMetrics counterMetrics = new GaugeMetrics();
		Thread.currentThread().sleep(2 * 1000);
		counterMetrics.addJob(new Object());
		Thread.currentThread().sleep(2 * 1000);
	}
}
