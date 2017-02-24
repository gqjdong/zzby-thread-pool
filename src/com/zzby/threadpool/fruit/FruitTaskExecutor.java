package com.zzby.threadpool.fruit;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zzby.threadpool.MyThreadPoolExecutor;
import com.zzby.threadpool.fruit.task.AppleTask;
import com.zzby.threadpool.fruit.task.BananaTask;
import com.zzby.threadpool.fruit.task.MelonTask;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FruitTaskExecutor {

	private MyThreadPoolExecutor myExecutor;

	private ScheduledThreadPoolExecutor scheduledExcecutor;
	
	public FruitTaskExecutor(){
		myExecutor = new MyThreadPoolExecutor(6, 12, 5, TimeUnit.MINUTES,
				 	 new LinkedBlockingQueue(3),
				 	 new FruitRejectedExecutionHanlder());
		scheduledExcecutor = new ScheduledThreadPoolExecutor(6);
	}
	
	/**
	 * 初始化水果任务
	 * @param taskCount 任务的组数
	 */
	public void initFruitTaskContext(int taskCount){
		for(int i = 0;i < taskCount;i ++){
			FruitTaskEntity task = new FruitTaskEntity();
			task.addFruitTask(new AppleTask(i,"buy a small apple " + i + " time"));
			task.addFruitTask(new BananaTask(i,"buy a small banana " + i + " time"));
			task.addFruitTask(new MelonTask(i,"buy a small melon " + i + " time"));
			FruitTaskContext.putFruitTaskEntity(i, task);
		}
	}
	
	/**
	 * 执行正常的水果任务
	 * @param fruitTaskEntity 每组任务的封装类
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void executeFruitTask(FruitTaskEntity fruitTaskEntity) throws IOException, ClassNotFoundException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
		if(fruitTaskEntity == null || fruitTaskEntity.isEmpty()){
			return;
		}
		List<FruitTask> fruitTasks = fruitTaskEntity.getFruitTasks();
		for(FruitProcess furitProcess : getFruitProcess(fruitTasks)){
			myExecutor.execute(furitProcess);
		}
	}
	
	/**
	 * 定时任务执行已经执行过，但失败了的任务
	 * @param initialDelay
	 * @param seconds
	 */
	public void scheduledFailFruitTask(long initialDelay,long seconds){
		scheduledExcecutor.scheduleWithFixedDelay(new ScheduledFruitTask(), initialDelay, seconds, TimeUnit.SECONDS);
	}
	
	/**
	 * 执行已经执行过，但失败了的任务
	 * @param fruitTaskEntity
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 */
	private void executeFailFruitTask(FruitTaskEntity fruitTaskEntity) throws IOException, ClassNotFoundException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, InterruptedException{
		if(fruitTaskEntity == null || fruitTaskEntity.isEmpty()){
			return;
		}
		System.out.println("失败任务数------------------------------" + fruitTaskEntity.getFruitTasks().size());
		int fruitTaskKey = fruitTaskEntity.getFruitTasks().get(0).getId();
		
		// 获取失败的任务列表和任务处理类，执行任务
		// 通过latch阻塞任务，任务全部执行完才会运行之后的代码
		List<FruitTask> fruitTasks = fruitTaskEntity.getFruitTasks();
		List<? extends FruitProcess> processes = getFailFruitProcess(fruitTasks);
		CountDownLatch latch = new CountDownLatch(processes.size());
		for(FruitProcess furitProcess : processes){
			furitProcess.setLatch(latch);
			myExecutor.execute(furitProcess);
		}
		// 设置latch阻塞的超时时间为3分钟
		latch.await(3, TimeUnit.MINUTES);
		
		// 重试的执行次数递增，超过3次后，移除该组任务
		// 该组任务全部执行成功，移除该组任务
		int executeTime = fruitTaskEntity.getExecuteTime();
		fruitTaskEntity.setExecuteTime(++ executeTime);
		System.out.println("失败任务执行次数-------------------------------------" + executeTime);
		if(executeTime >= 3){
			FruitTaskContext.removeFailFruitEntity(fruitTaskKey);
		}
		FruitTaskContext.removeFailFruitEntityWhenSuccess(fruitTaskKey);
		
	}
	
	/**
	 * 获取处理失败任务的处理类
	 * @param fruitTasks
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private List<? extends FruitProcess> getFailFruitProcess(List<FruitTask> fruitTasks) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
		return getProcess(fruitTasks,false,true);
	}
	
	/**
	 * 获取处理正常任务的处理类
	 * @param fruitTasks
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private List<? extends FruitProcess> getFruitProcess(List<FruitTask> fruitTasks) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
		return getProcess(fruitTasks,true,false);
	}
	
	/**
	 * 通过注解和反射获取每个任务的处理类
	 * @param fruitTasks
	 * @param needTaskRemove
	 * @param needFailTaskRemove
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private List<? extends FruitProcess> getProcess(List<FruitTask> fruitTasks,boolean needTaskRemove,boolean needFailTaskRemove) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException{
		List<FruitProcess> processes = new ArrayList<FruitProcess>();
		for(FruitTask task : fruitTasks){
			FruitTaskInfo taskInfo = task.getClass().getAnnotation(FruitTaskInfo.class);
			if(taskInfo == null) continue;
			Class<? extends FruitTask> taskClass = taskInfo.taskClass();
			Constructor<? extends FruitProcess> constuctor = taskInfo.processClass().getConstructor(taskClass,boolean.class,boolean.class);
			processes.add(constuctor.newInstance(taskClass.cast(task),needTaskRemove,needFailTaskRemove));
		}
		return processes;
	}
	
	private class ScheduledFruitTask implements Runnable{

		@Override
		public void run() {
			try {
				System.out.println("##################定时任务开始##################");
				for(int key : FruitTaskContext.getFailfruittaskmap().keySet()){
					executeFailFruitTask(FruitTaskContext.getFailfruittaskmap().get(key));
				}
				System.out.println("##################定时任务结束##################");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 通过序列化方式深度拷贝list
	 * @param src
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
//	private <T> List<T> deepCopy(List<T> src) throws IOException,ClassNotFoundException {
//		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//		ObjectOutputStream out = new ObjectOutputStream(byteOut);
//		out.writeObject(src);
//
//		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
//		ObjectInputStream in = new ObjectInputStream(byteIn);
//		List<T> dest = (List<T>) in.readObject();
//		
//		return dest;
//	}
	
}
