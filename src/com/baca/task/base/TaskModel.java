package com.baca.task.base;

import java.util.Date;

public class TaskModel {
	private int m_id;
	private String m_des;
	private String m_content;
	public enum TaskTypeEnum{
		INTERVAL_TASK, RUNONCE_TASK;
	}
	
	private int m_interval;
	private Date m_runTime;
	
	private TaskTypeEnum m_taskType1;
	
	public TaskModel(int typeCode){
		if(typeCode == 21){
			this.setTaskType1(TaskTypeEnum.INTERVAL_TASK);
		}else if(typeCode == 22){
			this.setTaskType1(TaskTypeEnum.RUNONCE_TASK);
		}
	}

	public int getId() {
		return m_id;
	}

	public void setId(int m_id) {
		this.m_id = m_id;
	}

	public String getDes() {
		return m_des;
	}

	public void setDes(String m_des) {
		this.m_des = m_des;
	}

	public String getContent() {
		return m_content;
	}

	public void setContent(String m_content) {
		this.m_content = m_content;
	}

	public int getInterval() {
		return m_interval;
	}

	public void setInterval(int m_interval) {
		this.m_interval = m_interval;
	}

	public Date getRunTime() {
		return m_runTime;
	}

	public void setRunTime(Date m_runTime) {
		this.m_runTime = m_runTime;
	}

	public TaskTypeEnum getTaskType1() {
		return m_taskType1;
	}

	public void setTaskType1(TaskTypeEnum m_taskType1) {
		this.m_taskType1 = m_taskType1;
	}

}
