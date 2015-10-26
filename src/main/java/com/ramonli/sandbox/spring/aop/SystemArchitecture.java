package com.ramonli.sandbox.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SystemArchitecture {

	@Pointcut("execution(* com.ramonli.sandbox.spring.aop.*Service.*(..))")
	public void bizService(){};
}
