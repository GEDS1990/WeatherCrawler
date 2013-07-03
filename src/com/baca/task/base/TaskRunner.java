package com.baca.task.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ִ��������࣬ʹ��Java Executor�����С�
 * @author Yang Tao <hsllany@163.com>
 * @version 1.0
 * @see com.baca.task.base.MonitorTask
 */
public class TaskRunner {
	/**
	 * �̳߳�
	 */
	private ExecutorService m_executor;
	
	/**
	 * ������������������ı仯������������ı仯��Ӧ�����ӻ�ɾ������
	 */
	private MonitorTask m_monitorTask;
	
	/**
	 * ����һ��TaskRunner,ʹ��CachedThreadPool
	 */
	public TaskRunner(){
		this.m_executor = Executors.newCachedThreadPool();
		this.m_monitorTask = new MonitorTask(this.m_executor);
	}
	
	/**
	 * ����һ��TaskRunner,ʹ��FixedThreadPool
	 * @param size��FixedThreadPool�Ĵ�С
	 */
	public TaskRunner(int size){
		this.m_executor = Executors.newFixedThreadPool(size);
		this.m_monitorTask = new MonitorTask(this.m_executor);
	}
	
	/**
	 * ����TaskRunner
	 */
	public void start(){
		this.m_executor.execute(this.m_monitorTask);
	}
	
	/**
	 * ִ������
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
