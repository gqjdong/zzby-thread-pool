package com.zzby.threadpool.fruit.process;

import com.zzby.threadpool.TaskProcessException;
import com.zzby.threadpool.fruit.FruitProcess;
import com.zzby.threadpool.fruit.task.MelonTask;

public class MelonProcess extends FruitProcess<MelonTask>{

	public MelonProcess(MelonTask task, boolean needTaskRemove) {
		super(task, needTaskRemove, false);
	}

	public MelonProcess(boolean needFailTaskRemove,MelonTask task) {
		super(task, false, needFailTaskRemove);
	}
	
	public MelonProcess(MelonTask task, boolean needTaskRemove,boolean needFailTaskRemove) {
		super(task, needTaskRemove, needFailTaskRemove);
	}
	
	@Override
	protected void fruitProcess() {
		//System.out.println("买西瓜任务执行：" + task.getMelon());
		throw new TaskProcessException("MelonProcess执行出错!");
	}

}
