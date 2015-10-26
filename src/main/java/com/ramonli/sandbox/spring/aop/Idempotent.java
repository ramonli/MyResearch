package com.ramonli.sandbox.spring.aop;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
	// marker annotation
}
