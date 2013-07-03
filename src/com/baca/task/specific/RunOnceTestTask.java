package com.baca.task.specific;

import java.util.Map;

import com.baca.task.base.BaseRunnable;

public class RunOnceTestTask extends BaseRunnable {

	public RunOnceTestTask(Map<String, String> task) {
		super(task);
	}

	@Override
	protected String getRandomName() {
		return "run once";
	}

	@Override
	protected void excute() {
		System.out.println("run once");
		
	}

}
