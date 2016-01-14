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
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
// @Transactional
public class DefaultOrderService implements OrderService {
	private Logger logger = LoggerFactory.getLogger(DefaultOrderService.class);
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@Async
	@Transactional
	public String order(String req) {
		logger.debug("got request: {}", req);
		this.getJdbcTemplate().update("insert into async1(title) values('sync order')");
		/**
		 * Here the query() method won't be executed asynchronously, as it is a invocation inside the instance of <code>DefaultOrderService</code>.
		 * Think about the dynamical proxy mechanism of JDK.
		 * <p/>
		 * To make query() executed asynchronously, you have to call this method from outside of <code>DefaultOrderService</code>, by that approach,
		 * the method will be called on @Async proxy of <code>DefaultOrderService</code>.
		 */
		this.update(1);

		return "good deal";
	}

	/**
	 * Apply both @Transactional and @Async only make sure spring to manage the transaction of this method, however it won't connect this transaction
	 * with caller's transaction, as it is in a dedicated thread.
	 * <p/>
	 * That says we must declare @Transactional on either <code>DefaultOrderService</code> or on <code>query(int time)</code> methods, otherwise
	 * Spring won't generate transactional proxy for this instance.
	 */
	@Transactional
	@Async
	@Override
	public Future<String> update(int time) {
		logger.debug("query order: {}", time + "");
		logger.debug("this.class: {}", this.getClass().getName());
		logger.debug("daemon thread? {}", Thread.currentThread().isDaemon());

		// // register a transaction event listener..this method will be executed
		// // in a independent transaction context.
		// TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
		// private boolean committed = false;
		//
		// @Override
		// public void suspend() {
		// logger.debug("suspend()");
		// }
		//
		// @Override
		// public void resume() {
		// logger.debug("resume()");
		// }
		//
		// @Override
		// public void flush() {
		// logger.debug("flush()");
		// }
		//
		// @Override
		// public void beforeCommit(boolean readOnly) {
		// logger.debug("beforeCommit()");
		// }
		//
		// @Override
		// public void beforeCompletion() {
		// logger.debug("beforeCompletion()");
		// }
		//
		// @Override
		// public void afterCommit() {
		// logger.debug("afterCommit()");
		// this.committed = true;
		// }
		//
		// @Override
		// public void afterCompletion(int status) {
		// logger.debug("afterCompletion()");
		// logger.debug(committed ? "committed" : "roll back");
		// }
		// });

		try {
			this.getJdbcTemplate().update("insert into async1(title) values('async update')");

			Thread.sleep(time * 1000);
			logger.debug("good sleep {} seconds", time);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		if (time > 3) {
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
