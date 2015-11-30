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
public class ConcurrentOperationExecutor2 implements Ordered {
	private Logger logger = LoggerFactory.getLogger(ConcurrentOperationExecutor2.class);

	private static final int DEFAULT_MAX_RETRIES = 2;

	private int maxRetries = DEFAULT_MAX_RETRIES;

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	@Override
	public int getOrder() {
		/**
		 * What is the fuck?? If apply below 'return' statement, a exception
		 * "java.lang.IllegalStateException: Required to bind 2 arguments, but only bound 1 (JoinPointMatch was NOT bound in invocation)" will be
		 * thrown out...So strange.
		 */
		// return Ordered.HIGHEST_PRECEDENCE;
		return 1;
	}

	// /**
	// * In-place pointcut definition.
	// */
	// @Around("execution(* com.ramonli.sandbox.spring.aop.*Service.*(..)) && args(orderId,..) &&
	// @annotation(com.ramonli.sandbox.spring.aop.Idempotent)")
	// public Object monitor(ProceedingJoinPoint pjp, String orderId) throws Throwable {
	// logger.debug("@Around[monitor]: ENTER " + pjp.getSignature().getName());
	// logger.debug("declareName:" + pjp.getSignature().getDeclaringTypeName());
	// logger.debug("" + pjp.getSignature().getName());
	// logger.debug("ordreId: {}", orderId);
	//
	// return pjp.proceed();
	// }

	/**
	 * Define pointcut on {@link com.ramonli.sandbox.spring.aop.SystemArchitecture#bizService()}
	 */
	// @Around("com.ramonli.sandbox.spring.aop.SystemArchitecture.bizService() && args(orderInfo,..)")
	@Around("execution(* com.ramonli.sandbox.spring.aop.*Service.*(..)) && args(orderId,..) && @annotation(com.ramonli.sandbox.spring.aop.Idempotent)")
	public Object retry(ProceedingJoinPoint pjp, String orderId) throws Throwable {
		logger.debug("@Around[retry]: ENTER " + pjp.getSignature().getName());
		logger.debug("ordreInfo: {}", orderId);

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
