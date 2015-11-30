package com.ramonli.sandbox.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Component;

// must be stated as @Component explicitly, otherwise spring container won't realize this @Aspect
@Component
@Aspect
public class ConcurrentOperationExecutor implements Ordered {
	private Logger logger = LoggerFactory.getLogger(ConcurrentOperationExecutor.class);

	private static final int DEFAULT_MAX_RETRIES = 2;

	private int maxRetries = DEFAULT_MAX_RETRIES;
	// 优先级别越高，那么那么代理就在整个代理层次的越外面
	private int order = 1;

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * In-place pointcut definition.
	 */
	@Around("@annotation(com.ramonli.sandbox.spring.aop.Idempotent)")
	public Object monitor(ProceedingJoinPoint pjp) throws Throwable {
		logger.debug("@Around[monitor]: ENTER " + pjp.getSignature().getName());
		logger.debug("declareName:" + pjp.getSignature().getDeclaringTypeName());
		logger.debug("" + pjp.getSignature().getName());
		// logger.debug("ordreId: {}", orderId);

		return pjp.proceed();
	}

	/**
	 * Define pointcut on {@link com.ramonli.sandbox.spring.aop.SystemArchitecture#bizService()}
	 */
	@Around("com.ramonli.sandbox.spring.aop.SystemArchitecture.bizService() && args(orderInfo,..)")
	public Object retry(ProceedingJoinPoint pjp, OrderInfo orderInfo) throws Throwable {
		logger.debug("@Around[retry]: ENTER " + pjp.getSignature().getName());
		logger.debug("ordreInfo: {}", orderInfo);

		int numAttempts = 0;
		PessimisticLockingFailureException lockFailureException;
		do {
			numAttempts++;
			try {
				return pjp.proceed();
			} catch (PessimisticLockingFailureException ex) {
				lockFailureException = ex;
			}
		} while (numAttempts <= this.maxRetries);
		throw lockFailureException;
	}

}
