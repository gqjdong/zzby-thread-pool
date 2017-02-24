package com.zzby.threadpool.fruit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class FruitTaskContext {
	
	// 正常任务Map
	private static final Map<Integer,FruitTaskEntity> fruitTaskMap = new ConcurrentHashMap<Integer,FruitTaskEntity>(128);
	
	// 失败任务Map
	private static final Map<Integer,FruitTaskEntity> failFruitTaskMap = new ConcurrentHashMap<Integer,FruitTaskEntity>(128);
	
	/**
	 * 添加正常任务
	 * @param fruitTaskKey 任务组标识
	 * @param fruitTaskEntity 任务组封装类
	 */
	public static void putFruitTaskEntity(Integer fruitTaskKey,FruitTaskEntity fruitTaskEntity){
		fruitTaskMap.put(fruitTaskKey, fruitTaskEntity);
	}
	
	/**
	 * 添加失败任务
	 * @param fruitTaskKey 任务组标识
	 * @param fruitTaskEntity 任务组封装类
	 */
	public static void putFailFruitTaskEntity(Integer fruitTaskKey,FruitTaskEntity fruitTaskEntity){
		failFruitTaskMap.put(fruitTaskKey, fruitTaskEntity);
	}
	
	/**
	 * 移除正常任务组中的单个任务
	 * @param fruitTask
	 */
	public static void removeFruitTask(FruitTask fruitTask){
		remove(fruitTaskMap,fruitTask.getId(),fruitTask);
	}
	
	/**
	 * 移除失败任务组中的单个任务
	 * @param fruitTask
	 */
	public static void removeFailFruitTask(FruitTask fruitTask){
		remove(failFruitTaskMap,fruitTask.getId(),fruitTask);
	}
	
	/**
	 * 移除已经执行过的正常任务组，如果有执行失败的任务，则将任务组转移到失败任务Map中
	 * @param fruitTaskKey
	 */
	public static void moveEntityToFailFuritTaskMap(Integer fruitTaskKey){
		FruitTaskEntity fruitTaskEntity = removeAll(fruitTaskMap,fruitTaskKey,false);
		if(fruitTaskEntity != null && !fruitTaskEntity.hasAllSuccess()){
			putFailFruitTaskEntity(fruitTaskKey,fruitTaskEntity);
		}
	}
	
	/**
	 * 移除失败任务组
	 * @param fruitTaskKey
	 */
	public static void removeFailFruitEntity(Integer fruitTaskKey){
		removeAll(failFruitTaskMap,fruitTaskKey,false);
	}
	
	/**
	 * 当全部任务执行成功后，移除失败任务组
	 * @param fruitTaskKey
	 */
	public static void removeFailFruitEntityWhenSuccess(Integer fruitTaskKey){
		removeAll(failFruitTaskMap,fruitTaskKey,true);
	}
	
	/**
	 * 移除某个任务组中的已经执行成功的某个任务
	 * @param fruitMap
	 * @param fruitTaskKey
	 * @param fruitTask
	 */
	private static void remove(Map<Integer,FruitTaskEntity> fruitMap,Integer fruitTaskKey,FruitTask fruitTask){
		FruitTaskEntity fruitTaskEntity = fruitMap.get(fruitTaskKey);
		if(fruitTaskEntity == null || !fruitTask.isSuccess()){
			return;
		}
		fruitTaskEntity.removeFruitTask(fruitTask);
	}
	
	/**
	 * 移除任务Map中的某个任务组
	 * @param fruitMap 任务Map
	 * @param fruitTaskKey 任务组标识
	 * @param needAllSuccess 是否需要任务全部执行成功
	 * @return
	 */
	private static FruitTaskEntity removeAll(Map<Integer,FruitTaskEntity> fruitMap,Integer fruitTaskKey,boolean needAllSuccess){
		synchronized (fruitMap) {
			FruitTaskEntity fruitTaskEntity = fruitMap.get(fruitTaskKey);
			// 如果任务组中的任务还没有全部执行过，则返回
			if(fruitTaskEntity == null || !fruitTaskEntity.hasAllExcuted()){
				return null;
			}
			
			// 如果需要全部成功而且已经全部执行成功，则移除该任务组
			if(needAllSuccess && fruitTaskEntity.hasAllSuccess()){
				return fruitMap.remove(fruitTaskKey);
			}
			
			// 如果不需要全部成功，则移除该任务组
			if(!needAllSuccess){
				return fruitMap.remove(fruitTaskKey);
			}
		}
		return null;
	}

	public static Map<Integer, FruitTaskEntity> getFruittaskmap() {
		return fruitTaskMap;
	}

	public static Map<Integer, FruitTaskEntity> getFailfruittaskmap() {
		return failFruitTaskMap;
	}
	
}
