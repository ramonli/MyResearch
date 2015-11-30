package com.ramonli.sandbox.springasync;

import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
// By default the transaction manager will be a bean named 'transactionManager'.
@Transactional
public class DefaultOrderFacade implements OrderFacade {
	private Logger logger = LoggerFactory.getLogger(DefaultOrderFacade.class);
	@Resource(name = "defaultOrderService")
	private OrderService orderService;

	@Override
	public String facade(String orderId, int waitTime) throws Exception {
		logger.debug("orderService - aop proxy: {}", AopUtils.isAopProxy(orderService));
		logger.debug("orderService - jdk proxy: {}", AopUtils.isJdkDynamicProxy(orderService));
		logger.debug("orderService - cglib proxy: {}", AopUtils.isCglibProxy(orderService));
		this.getOrderService().order(orderId);
		
		String result = null;
		Future<String> future = this.getOrderService().update(waitTime);
		try {
			result = future.get();
		} catch (Exception e) {
			/**
			 * here if we don't catch the exception, the transaction of this
			 * 'facade()' method will be committed, as future.get() will throw a
			 * ExecutionException which isn't a unchecked exception.
			 * <p/>
			 * The default behaviour of transaction manager is to rollback if a
			 * unchecked exception(RuntimeException) thrown out.
			 */
			logger.error(e.getMessage());
		}

		// register a transaction event listener
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
			private boolean committed = false;

			@Override
			public void suspend() {
				logger.debug("suspend()");
			}

			@Override
			public void resume() {
				logger.debug("resume()");
			}

			@Override
			public void flush() {
				logger.debug("flush()");
			}

			/**
			 * The order of callback methods is as below:
			 * <ol>
			 * <li>beforeCommit()</li>
			 * <li>beforeCompletion()</li>
			 * <li>afterCommit()</li>
			 * <li>afterCompletion()</li>
			 * </ol>
			 * If transaction will be rolled back at last, both
			 * <code>beforeCommit()</code> and <code>afterCommit()</code> won't
			 * be called.
			 */
			@Override
			public void beforeCommit(boolean readOnly) {
				logger.debug("beforeCommit()..readOnly: {}", readOnly);
			}

			@Override
			public void beforeCompletion() {
				logger.debug("beforeCompletion()");
			}

			@Override
			public void afterCommit() {
				logger.debug("afterCommit()");
				this.committed = true;
			}

			@Override
			public void afterCompletion(int status) {
				logger.debug("afterCompletion()");
				logger.debug(committed ? "committed" : "roll back");
			}
		});

		if (waitTime > 5) {
			throw new RuntimeException("exception to trigger transaction rollback.");
		}
		return result;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

}
