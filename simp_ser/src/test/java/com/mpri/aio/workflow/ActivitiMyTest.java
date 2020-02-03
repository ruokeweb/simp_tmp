package com.mpri.aio.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.task.Task;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.untils.activiti.utils.ActivitiUtils;

public class ActivitiMyTest extends ApplicationTests {
//	@Autowired
//	RuntimeService runtimeService;  
//	@Autowired
//	private TaskService taskService;
//	@Autowired
//	private RepositoryService repositoryService;
//	@Autowired
//	private HistoryService historyService;
	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private ActivitiUtils activitiUtils;

//
////	@Test
//	public void start() {
//
//		String processDefinitionKey = "renzheng";
//		ProcessInstance processInstance = processEngine.getRuntimeService()
//				.startProcessInstanceByKey(processDefinitionKey);
//		System.out.println("流程部署的ID：" + processInstance.getDeploymentId());
//		System.out.println("流程定义的ID：" + processInstance.getProcessDefinitionId());
////		System.out.println("流程实例的ID：" + processInstance.getProcessInstanceId());
//
//	}
//
//	// 获取执行完的流程
////	@Test
//	public void getCompletePro() {
//		String taskId = "991fd876-77ce-11e9-bf14-309c2382234e";
//		processEngine.getTaskService().complete(taskId);
//		System.out.println("完成任务，任务ID：" + taskId);
//	}
//
////	@Test
//	public void findMyPersonalTask() {
//		String assignee = "苏宇鹏";
//
//		// 2.得到TaskService对象
//		TaskService taskService = processEngine.getTaskService();
//
//		// 3.根据流程定义的key,负责人assignee来实现当前用户的任务列表查询
//		List<Task> list = taskService.createTaskQuery().processDefinitionKey("renzheng").taskAssignee(assignee).list();
//
//		System.out.println(list.size());
//		// 4.任务列表的展示
//		for (Task task : list) {
//			System.err.println("流程实例ID:" + task.getProcessInstanceId());
//			System.err.println("任务ID:" + task.getId()); // 5002
//			System.err.println("任务负责人:" + task.getAssignee());
//			System.err.println("任务名称:" + task.getName());
//		}
//
//	}
//
//	// 获取taskId(流程开始)
//	public String startProcess() {
//		String processDefinitionKey = "renzheng";
//		ProcessInstance processInstance = processEngine.getRuntimeService()
//				.startProcessInstanceByKey(processDefinitionKey);
//		System.out.println("流程部署的ID：" + processInstance.getDeploymentId());
//		System.out.println("流程定义的ID：" + processInstance.getProcessDefinitionId());
//		System.out.println("流程实例的ID：" + processInstance.getProcessInstanceId());
//		return processInstance.getProcessInstanceId();
//	}
//
//	// 设置变量
//	public void setVariable(String processInstanceId) {
//		String assignee = "schoolmate";
//		TaskService taskService = processEngine.getTaskService();
//		Task task = taskService.createTaskQuery().taskAssignee(assignee).processInstanceId(processInstanceId)
//				.singleResult();
//		taskService.setVariable(task.getId(), "userId", "b071cb98849a4e2c92ba42e8dd5f45e0");
//		endProcess(task.getId());
//	}
//
//	// 智能审核
//	public void autoAudic(String processInstanceId) {
//		String assignee = "system";
//		TaskService taskService = processEngine.getTaskService();
//		Task task = taskService.createTaskQuery().taskAssignee(assignee).processInstanceId(processInstanceId)
//				.singleResult();
//		System.err.println("申请的用户为============" + (String) taskService.getVariable(task.getId(), "userId"));
//		taskService.setVariable(task.getId(), "res", 1);
//		taskService.setVariable(task.getId(), "admin", "17629261881");
//		endProcess(task.getId());
//	}
//
//	// 点击执行
//	public void excuteProcess(String processInstanceId,String username) {
////		String assignee = "ADMIN";
//
//		TaskService taskService = processEngine.getTaskService();
//		Task task = taskService.createTaskQuery().taskAssignee(username).processInstanceId(processInstanceId)
//				.singleResult();
//		System.err
//				.println("申请的用户为============" +(String) taskService.getVariable(task.getId(), "admin"));
//		taskService.setVariable(task.getId(), "res", 0);
//		endProcess(task.getId());
//	}
//
//	// 返回成功与否
//	public void endProcess(String taskId) {
//		TaskService taskService = processEngine.getTaskService();
//		taskService.complete(taskId);
//	}
//
////	@Test
//	public void startTest() {
////		String processInstanceId = startProcess();
//		// 提交审核
//		setVariable("7e96e289-7aa9-11e9-9b64-309c2382234e");
//		// 智能审核
//		autoAudic("7e96e289-7aa9-11e9-9b64-309c2382234e");
//		// 管理员审核
//		excuteProcess("7e96e289-7aa9-11e9-9b64-309c2382234e", "17629261881");
//	}
//
//	// 获取当前人的待办事件
//	@Test
	public void getWillEvents() {
		String assignee = "admin";
		// 2.得到TaskService对象
		TaskService taskService = processEngine.getTaskService();
		// 3.根据流程定义的key,负责人assignee来实现当前用户的任务列表查询
		List<Task> list = taskService.createTaskQuery().processDefinitionKey("authenticateFlow").taskAssignee(assignee)
				.list();
		System.out.println(list.size());
		// 4.任务列表的展示
		for (Task task : list) {
			System.err.println("流程实例ID:" + task.getProcessInstanceId());
			System.err.println("任务ID:" + task.getId()); // 5002
			System.err.println("任务负责人:" + task.getAssignee());
			System.err.println("任务名称:" + task.getName());
			System.err.println("申请的用户为============" + (String) taskService.getVariable(task.getId(), "userId"));
			System.err.println("申请的用户名为============" + (String) taskService.getVariable(task.getId(), "userName"));
		}
	}
//
//	// 获取已办事件
////	@Test
//	public void getFinishEvents() {
//		List<HistoricProcessInstance> list = processEngine.getHistoryService().createHistoricProcessInstanceQuery()
//				.processDefinitionKey("renzheng")// 流程定义ID
//				.list();
//
//		if (list != null && list.size() > 0) {
//			for (HistoricProcessInstance hi : list) {
//				System.err.println("历史============="+hi.getId() + "	  " + hi.getStartTime() + "   " + hi.getEndTime());
//			}
//		}
//	}

	/**
	 * 测试
	 */
//	@Test
	public void testProcess() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", "95d9b6359ca4488d833f51544176e212");
		// 开始一个新流程
		String processInstanceId = activitiUtils.StartProcess("authenticateFlow", map);
		// 设值(提交申请)
		Map<String, Object> firstMap = new HashMap<String, Object>();
		firstMap.put("userId", "95d9b6359ca4488d833f51544176e212");
		firstMap.put("userName", "13484478234");
		firstMap.put("res", 0);

//		String userId = "9547f60db68f4cd4877eec0b5e87f1e3";

//		activitiUtils.setVarible(firstMap, processInstanceId, userId);
		activitiUtils.setVarible(firstMap, processInstanceId, "95d9b6359ca4488d833f51544176e212");
		// 智能审核
		activitiUtils.setVarible(firstMap, processInstanceId, "system");
//		//管理员审核
//		if((int)firstMap.get("res") != 0) {
//			activitiUtils.setVarible(firstMap, processInstanceId, "17629261881");
//		}

	}

	@Test
	public void findHistoryTask() {
		String taskAssignee = "c719d7185fde4672a8f96a4f0a4e2295";
		List<HistoricTaskInstance> list = processEngine.getHistoryService()// 与历史数据（历史表）相关的Service
				.createHistoricTaskInstanceQuery()// 创建历史任务实例查询
				.taskAssignee(taskAssignee)// 指定历史任务的办理人
				.list();
		for (HistoricTaskInstance pi : list) {
			System.out.println("流程实例ID:" + pi.getId());// 流程实例ID
			System.out.println("流程定义ID:" + pi.getProcessDefinitionId());// 流程定义ID
			System.out.println("流程名称:" + pi.getName());
			System.out.println("代理人:" + pi.getAssignee());
		}
	}
}
