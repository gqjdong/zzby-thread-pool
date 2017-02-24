package com.zzby.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.zzby.threadpool.fruit.FruitTaskContext;
import com.zzby.threadpool.fruit.FruitTaskExecutor;

public class ThreadPoolTest {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
		FruitTaskExecutor myExecutor = new FruitTaskExecutor();
		// 初始化水果任务
		myExecutor.initFruitTaskContext(5);
		// 执行所有水果任务
		for(int key : FruitTaskContext.getFruittaskmap().keySet()){
			myExecutor.executeFruitTask(FruitTaskContext.getFruittaskmap().get(key));
		}
		// 定时执行失败的水果任务
		myExecutor.scheduledFailFruitTask(0,3);
	}
	
}
