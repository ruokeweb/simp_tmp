package com.mpri.aio.app.reg.vo;

import org.activiti.engine.task.Task;

import com.mpri.aio.schoolmate.model.SmSchoolmate;

public class ActivitiSmVo {
	private Task task;
	private SmSchoolmate schoolmate;
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public SmSchoolmate getSchoolmate() {
		return schoolmate;
	}
	public void setSchoolmate(SmSchoolmate schoolmate) {
		this.schoolmate = schoolmate;
	}
	
	
}
