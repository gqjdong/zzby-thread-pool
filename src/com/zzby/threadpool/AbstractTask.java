package com.zzby.threadpool;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class AbstractTask implements Serializable{
	
	// 任务是否已经执行
	protected boolean executed;
	
	// 任务是否执行成功
	protected boolean success;

	protected AbstractTask(){
		
	}
	
	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
