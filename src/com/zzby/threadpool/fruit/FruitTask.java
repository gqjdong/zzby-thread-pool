package com.zzby.threadpool.fruit;

import com.zzby.threadpool.AbstractTask;

@SuppressWarnings("serial")
public class FruitTask extends AbstractTask{

	private int id;
	
	public FruitTask(){
		
	}
	
	public FruitTask(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
