package com.zzby.threadpool.fruit.process;

import com.zzby.threadpool.fruit.FruitProcess;
import com.zzby.threadpool.fruit.task.BananaTask;

public class BananaProcess extends FruitProcess<BananaTask>{

	public BananaProcess(BananaTask task, boolean needTaskRemove) {
		super(task, needTaskRemove, false);
	}

	public BananaProcess(boolean needFailTaskRemove,BananaTask task) {
		super(task, false, needFailTaskRemove);
	}
	
	public BananaProcess(BananaTask task, boolean needTaskRemove,boolean needFailTaskRemove) {
		super(task, needTaskRemove, needFailTaskRemove);
	}
	
	@Override
	protected void fruitProcess() {
		System.out.println("买香蕉任务执行：" + task.getBanana());
		//throw new TaskProcessException("BananaProcess执行出错!");
	}

}
