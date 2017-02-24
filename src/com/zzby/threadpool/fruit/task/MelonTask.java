package com.zzby.threadpool.fruit.task;

import com.zzby.threadpool.fruit.FruitTask;
import com.zzby.threadpool.fruit.FruitTaskInfo;
import com.zzby.threadpool.fruit.process.MelonProcess;

@FruitTaskInfo(taskClass = MelonTask.class,processClass = MelonProcess.class)
public class MelonTask extends FruitTask{

	private static final long serialVersionUID = -5349292780481780510L;
	
	private String melon;
	
	public MelonTask(){
		
	}
	
	public MelonTask(int id,String melon){
		super(id);
		this.melon = melon;
	}

	public String getMelon() {
		return melon;
	}

	public void setMelon(String melon) {
		this.melon = melon;
	}

}
