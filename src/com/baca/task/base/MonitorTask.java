package com.baca.task.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import com.baca.config.Config;
import com.baca.db.TaskDB;

public class MonitorTask implements Runnable {
	private Executor m_executor;
	private TaskDB m_taskDB;
	private List<Map<String, String>> m_taskArray;
	private boolean m_isRunningFlag;
	private SimpleDateFormat m_dtFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public void run() {
		while (m_isRunningFlag) {
			List<Map<String, String>> taskFromDB = this.m_taskDB.getTasks();

			// check if there is new task in db
			for (int i = 0; i < taskFromDB.size(); i++) {
				Map<String, String> taskNew = taskFromDB.get(i);

				if (!containsTaskId(this.m_taskArray, taskNew.get("id"))) {
					int type1 = Integer.valueOf(taskNew.get("tasktype_1"));

					if (type1 == TaskConfig.TASK_INTERVAL) {
						// 如果是间隔执行的任务
						taskNew.put("lastRunTime",
								String.valueOf(System.currentTimeMillis()));
						this.m_taskArray.add(taskNew);
						System.out.println("adding new interval task: "
								+ taskNew.get("description") + " task");

					} else if (type1 == TaskConfig.TASK_RUN_ONCE) {
						// 如果是一次执行的任务
						this.m_taskArray.add(taskNew);
						System.out.println("adding new run_once task: "
								+ taskNew.get("description") + " task");
					}
				}
			}

			// delete old task from db
			for (int j = 0; j < this.m_taskArray.size(); j++) {
				Map<String, String> task_t = this.m_taskArray.get(j);
				if (!containsTaskId(taskFromDB, task_t.get("id"))) {
					System.out.println("deleting old task: " + task_t.get("id")
							+ " task");
					this.m_taskArray.remove(j);
				}
			}

			// start all task in the list
			for (int k = 0; k < this.m_taskArray.size(); k++) {
				Map<String, String> taskToRun = this.m_taskArray.get(k);
				int taskType = Integer.valueOf(taskToRun.get("tasktype_1"));

				if (taskType == TaskConfig.TASK_INTERVAL) {
					// 如果是间隔执行的任务
					long beforeTime = Long.parseLong(taskToRun
							.get("lastRunTime"));

					int interval = Integer.parseInt(taskToRun.get("interval"));
					// System.out.println("before: " + beforeTime + "; = " +
					// (System.currentTimeMillis() - beforeTime));
					if ((System.currentTimeMillis() - beforeTime) >= interval) {
						if (taskToRun.get("isFinished") == null || taskToRun.get("isFinished").equals("true")) {
							this.m_executor.execute(TaskFactory
									.getInstance(taskToRun));
							taskToRun.put("lastRunTime",
									String.valueOf(System.currentTimeMillis()));
						}
					}

				} else if (taskType == TaskConfig.TASK_RUN_ONCE) {
					// 如果是一次性任务
					Date now = new Date();
					Date runTime = null;
					try {
						runTime = m_dtFormat.parse(taskToRun.get("runtime"));
						if (now.after(runTime)) {
							this.m_executor.execute(TaskFactory
									.getInstance(taskToRun));
							m_taskDB.deleteTask(taskToRun.get("id"));
							m_taskArray.remove(taskToRun);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}

			taskFromDB.clear();
			taskFromDB = null;

			try {
				Thread.sleep(Integer.valueOf(Config
						.getValue("task_monitor_interval")));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public MonitorTask(Executor e) {
		this.m_executor = e;
		this.m_taskDB = new TaskDB();
		this.m_taskArray = new ArrayList<Map<String, String>>();
		this.m_isRunningFlag = true;
	}

	private boolean containsTaskId(List<Map<String, String>> taskList,
			String taskId) {
		if (taskList.size() == 0) {
			return false;
		} else {
			for (int j = 0; j < taskList.size(); j++) {
				if (taskList.get(j).containsValue(taskId)) {
					return true;
				}
			}
			return false;

		}
	}

	public void stopMonit() {
		this.m_isRunningFlag = false;
	}

}
