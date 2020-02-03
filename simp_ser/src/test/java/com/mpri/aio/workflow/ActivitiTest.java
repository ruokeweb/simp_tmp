package com.mpri.aio.workflow;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpri.aio.ApplicationTests;

public class ActivitiTest extends ApplicationTests{
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ProcessEngine processEngine;
	
	@Test
	public void start() {
		
		String processDefinitionKey = "regProcess";
		ProcessInstance processInstance = processEngine.getRuntimeService()
											.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程部署的ID："+processInstance.getDeploymentId());
		System.out.println("流程定义的ID："+processInstance.getProcessDefinitionId());
		System.out.println("流程实例的ID："+processInstance.getProcessInstanceId());

	}
	
	//@Test
	public void runNext() {
		String instanceKey = "regProcess";
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(instanceKey);
		List<Task> taskQuery = processEngine.getTaskService().createTaskQuery().processInstanceId(instance.getId()).list();
		
		for(Task task:taskQuery) {
			
			System.out.println(task.getId());
		}
		
	}
	
	
}
