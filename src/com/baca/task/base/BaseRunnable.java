package com.baca.task.base;

import java.util.Map;

public abstract class BaseRunnable implements Runnable {
	protected String m_taskName;
	protected boolean m_isRunningFlag;
	protected Map<String, String> m_task;

	public BaseRunnable(Map<String, String> task) {
		this.m_taskName = getRandomName();
		m_isRunningFlag = true;
		this.m_task = task;

		task.put("isFinish", "false");

	}

	public BaseRunnable(Map<String, String> task, String taskName) {
		this.m_taskName = taskName;
	}

	public final String getName() {
		return m_taskName;
	}

	protected abstract String getRandomName();

	protected abstract void excute();

	@Override
	public final void run() {
		excute();
		this.m_task.put("isFinish", "true");

	}

}
