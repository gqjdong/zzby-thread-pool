package com.zzby.threadpool.fruit;

import java.util.LinkedList;
import java.util.List;

public class FruitTaskEntity {

	// 每组任务中的任务列表
	private List<FruitTask> fruitTasks;
	
	// 每组任务的执行次数
	private int executeTime;
	
	public FruitTaskEntity(){
		fruitTasks = new LinkedList<FruitTask>();
	}
	
	public void addFruitTask(FruitTask fruitTask){
		fruitTasks.add(fruitTask);
	}
	
	public void removeFruitTask(FruitTask fruitTask){
		fruitTasks.remove(fruitTask);
	}
	
	public List<FruitTask> getFruitTasks() {
		return fruitTasks;
	}

	public void setFruitTasks(List<FruitTask> fruitTasks) {
		this.fruitTasks = fruitTasks;
	}

	public int getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(int executeTime) {
		this.executeTime = executeTime;
	}

	/**
	 * 任务是否已经全部执行过
	 * @return
	 */
	public boolean hasAllExcuted(){
		for(FruitTask fruitTask : fruitTasks){
			if(!fruitTask.isExecuted()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 任务是否全部执行成功
	 * @return
	 */
	public boolean hasAllSuccess(){
		for(FruitTask fruitTask : fruitTasks){
			if(!fruitTask.isSuccess()){
				return false;
			}
		}
		return true;
	}
	
	public boolean isEmpty(){
		if(fruitTasks == null || fruitTasks.isEmpty()){
			return true;
		}
		return false;
	}
	
}
