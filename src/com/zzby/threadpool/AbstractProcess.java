package com.zzby.threadpool;

import java.util.concurrent.CountDownLatch;


public abstract class AbstractProcess implements Runnable{

	private CountDownLatch latch;
	
	@Override
	public void run(){
		try {
			process();
		} catch(TaskProcessException tpe){
			tpe.printStackTrace();
		}
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	protected abstract void process();
	
}
