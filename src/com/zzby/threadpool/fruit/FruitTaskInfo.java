package com.zzby.threadpool.fruit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FruitTaskInfo {
	
	// 任务类
	public Class<? extends FruitTask> taskClass();
	
	// 任务处理类
	public Class<? extends FruitProcess<?>> processClass();
	
}
