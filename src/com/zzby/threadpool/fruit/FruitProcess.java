package com.zzby.threadpool.fruit;

import com.zzby.threadpool.AbstractProcess;
import com.zzby.threadpool.TaskProcessException;

public abstract class FruitProcess<T extends FruitTask> extends AbstractProcess{

	// 要处理的任务信息
	protected T task;
	
	// 正常任务执行过后，是否需要移除
	protected boolean needTaskRemove; 
	
	// 失败任务执行过后，是否需要移除
	protected boolean needFailTaskRemove; 
	
	protected FruitProcess(T task, boolean needTaskRemove, boolean needFailTaskRemove) {
		this.task = task;
		this.needTaskRemove = needTaskRemove;
		this.needFailTaskRemove = needFailTaskRemove;
	}

	@Override
	protected void process() {
		if(task == null){
			return;
		}
		try {
			// 执行水果任务，如果出错，会抛出TaskProcessException异常
			fruitProcess();
			task.setSuccess(true);
		} catch(TaskProcessException tpe){
			//tpe.printStackTrace();
			task.setSuccess(false);
		} finally{
			task.setExecuted(true);
			// 如果是需要阻塞的任务，使其计数器递减
			if (getLatch() != null) {
				getLatch().countDown();
			}
		}
		if(needTaskRemove){
			// 这里需要同步，因为是非原子性操作
			synchronized (FruitTaskContext.getFruittaskmap()) {
				// 移除执行成功的任务
				FruitTaskContext.removeFruitTask(task);
				// 如果任务全部执行过，且还有未成功的任务，则将任务列表转移到失败任务map中
				FruitTaskContext.moveEntityToFailFuritTaskMap(task.getId());
			}
		}
		if(needFailTaskRemove){
			// 移除执行成功的失败任务
			FruitTaskContext.removeFailFruitTask(task);
		}
	}

	protected abstract void fruitProcess();
	
}
