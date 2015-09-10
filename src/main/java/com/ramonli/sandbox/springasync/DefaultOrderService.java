package com.ramonli.sandbox.springasync;

import java.util.UUID;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
// @Transactional
public class DefaultOrderService implements OrderService {
	private Logger logger = LoggerFactory.getLogger(DefaultOrderService.class);
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public String order(String req) {
		logger.debug("got request: {}", req);
		this.getJdbcTemplate().update("insert into async1(title) values('sync op')");
		/**
		 * Here the query() method won't be executed asynchronously, as it is a
		 * invocation inside the instance of <code>DefaultOrderService</code>.
		 * Think about the dynamical proxy mechanism of JDK.
		 * <p/>
		 * To make query() executed asynchronously, you have to call this method
		 * from outside of <code>DefaultOrderService</code>, by that approach,
		 * the method will be called on @Async proxy of
		 * <code>DefaultOrderService</code>.
		 */
		this.query(1);

		return "good deal";
	}

	/**
	 * Apply both @Transactional and @Async only make sure spring to manage the
	 * transaction of this method, however it won't connection this transaction
	 * with caller's transaction, as it is in a dedicated thread.
	 */
	@Async
	@Transactional
	@Override
	public Future<String> query(int time) {
		logger.debug("query order: {}", time + "");
		logger.debug("this.class: {}", this.getClass().getName());
		logger.debug("daemon thread? {}", Thread.currentThread().isDaemon());
		try {
			this.getJdbcTemplate().update("insert into async1(title) values('async op')");

			Thread.sleep(time * 1000);
			logger.debug("good sleep");
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		if (time > 5) {
			throw new RuntimeException("roll back exception");
		}
		return new AsyncResult<String>("find order information");
	}

	protected String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
