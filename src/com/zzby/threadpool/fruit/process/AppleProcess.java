package com.zzby.threadpool.fruit.process;

import com.zzby.threadpool.fruit.FruitProcess;
import com.zzby.threadpool.fruit.task.AppleTask;

public class AppleProcess extends FruitProcess<AppleTask>{

	public AppleProcess(AppleTask task, boolean needTaskRemove) {
		super(task, needTaskRemove, false);
	}

	public AppleProcess(boolean needFailTaskRemove,AppleTask task) {
		super(task, false, needFailTaskRemove);
	}
	
	public AppleProcess(AppleTask task, boolean needTaskRemove,boolean needFailTaskRemove) {
		super(task, needTaskRemove, needFailTaskRemove);
	}
	
	@Override
	public void fruitProcess() {
		System.out.println("买苹果任务执行：" + task.getApple());
		//throw new TaskProcessException("AppProcess执行出错!");
	}
	
}
