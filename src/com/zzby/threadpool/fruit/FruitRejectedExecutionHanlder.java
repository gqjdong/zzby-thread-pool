package com.zzby.threadpool.fruit;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;

public class FruitRejectedExecutionHanlder extends DiscardPolicy{

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
		System.out.println("队列满，且达到最大线程数，任务被拒绝，可以在拒绝前进行一些处理");
		super.rejectedExecution(r, e);
	}

}
