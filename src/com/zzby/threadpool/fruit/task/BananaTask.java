package com.zzby.threadpool.fruit.task;

import com.zzby.threadpool.fruit.FruitTask;
import com.zzby.threadpool.fruit.FruitTaskInfo;
import com.zzby.threadpool.fruit.process.BananaProcess;

@FruitTaskInfo(taskClass = BananaTask.class,processClass = BananaProcess.class)
public class BananaTask extends FruitTask {
	
	private static final long serialVersionUID = -9034835499870366723L;
	
	private String banana;
	
	public BananaTask(){
		
	}
	
	public BananaTask(int id,String banana){
		super(id);
		this.banana = banana;
	}

	public String getBanana() {
		return banana;
	}

	public void setBanana(String banana) {
		this.banana = banana;
	}

}
