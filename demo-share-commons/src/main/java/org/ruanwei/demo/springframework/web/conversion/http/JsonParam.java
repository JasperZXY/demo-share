package org.ruanwei.demo.springframework.web.conversion.http;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonParam {

	@AliasFor("name")
	String value() default "json";

	@AliasFor("value")
	String name() default "json";

	boolean required() default false;
	
	String defaultValue() default "{}";
}
