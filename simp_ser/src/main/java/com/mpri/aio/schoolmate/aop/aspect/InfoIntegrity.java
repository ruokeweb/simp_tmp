package com.mpri.aio.schoolmate.aop.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
* <p>Title: InfoIntegrity</p>  
* <p>Description: 信息完整度切面注解 </p>  
* @author syp  
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InfoIntegrity {
	
}