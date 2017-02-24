package com.zzby.threadpool.fruit.task;

import com.zzby.threadpool.fruit.FruitTask;
import com.zzby.threadpool.fruit.FruitTaskInfo;
import com.zzby.threadpool.fruit.process.AppleProcess;

@FruitTaskInfo(taskClass = AppleTask.class,processClass = AppleProcess.class)
public class AppleTask extends FruitTask{
	
	private static final long serialVersionUID = -6516443345916959778L;
	
	private String apple;

	public AppleTask(){
		
	}
	
	public AppleTask(int id,String apple){
		super(id);
		this.apple = apple;
	}

	public String getApple() {
		return apple;
	}

	public void setApple(String apple) {
		this.apple = apple;
	}

}
