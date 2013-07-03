package com.baca.task.base;

import java.util.Map;

import com.baca.task.specific.RunOnceTestTask;
import com.baca.task.specific.WeatherPollutionGetTask;

public class TaskFactory {

	public static BaseRunnable getInstance(Map<String, String> taskToRun) {
		int taskType1 = Integer.valueOf(taskToRun.get("tasktype_1"));
		int taskType2 = Integer.valueOf(taskToRun.get("tasktype_2"));
		if (taskType1 == TaskConfig.TASK_INTERVAL) {
			switch (taskType2) {
			case TaskConfig.INTERVAL_WEATHER:
				return new WeatherPollutionGetTask(taskToRun);
			}
		} else if (taskType1 == TaskConfig.TASK_RUN_ONCE) {
			switch (taskType2) {
			case TaskConfig.RUNONCE_TEST:
				return new RunOnceTestTask(taskToRun);
			}

		}

		return null;
	}
}
