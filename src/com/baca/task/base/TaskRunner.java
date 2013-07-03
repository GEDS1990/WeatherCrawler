package com.baca.task.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 执行任务的类，使用Java Executor来运行。
 * @author Yang Tao <hsllany@163.com>
 * @version 1.0
 * @see com.baca.task.base.MonitorTask
 */
public class TaskRunner {
	/**
	 * 线程池
	 */
	private ExecutorService m_executor;
	
	/**
	 * 监控任务，用来监控任务的变化，并根据任务的变化相应的增加或删除任务。
	 */
	private MonitorTask m_monitorTask;
	
	/**
	 * 构造一个TaskRunner,使用CachedThreadPool
	 */
	public TaskRunner(){
		this.m_executor = Executors.newCachedThreadPool();
		this.m_monitorTask = new MonitorTask(this.m_executor);
	}
	
	/**
	 * 构造一个TaskRunner,使用FixedThreadPool
	 * @param size，FixedThreadPool的大小
	 */
	public TaskRunner(int size){
		this.m_executor = Executors.newFixedThreadPool(size);
		this.m_monitorTask = new MonitorTask(this.m_executor);
	}
	
	/**
	 * 启动TaskRunner
	 */
	public void start(){
		this.m_executor.execute(this.m_monitorTask);
	}
	
	/**
	 * 执行任务
	 * @param task
	 */
	public void execute(Runnable task){
		this.m_executor.execute(task);
	}
	
	public void awaitTermination(){
		if(!this.m_executor.isTerminated()){
			try {
				this.m_executor.awaitTermination(100000,TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
