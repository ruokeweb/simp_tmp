package com.mpri.aio.untils.activiti.utils;

import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * activiti 工具类
 * 
 * @author syp
 *
 */
@Component
public class ActivitiUtils {
	@Autowired
	RuntimeService runtimeService;

	@Autowired
	private ProcessEngine processEngine;

	/**
	 * 开始流程
	 * 
	 * @param processName
	 * @return
	 */
	public String StartProcess(String processDefinitionkey,Map<String, Object> map) {
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey(processDefinitionkey,map);
		System.out.println("流程部署的ID：" + processInstance.getDeploymentId());
		System.out.println("流程定义的ID：" + processInstance.getProcessDefinitionId());
		System.out.println("流程实例的ID：" + processInstance.getProcessInstanceId());
		return processInstance.getProcessInstanceId();
	}

	/**
	 * 设置变量并执行完改流程节点
	 * 
	 * @param map
	 * @param processInstanceId
	 * @param assignee
	 */
	public Boolean setVarible(Map<String, Object> map, String processInstanceId, String assignee) {
		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().taskAssignee(assignee).processInstanceId(processInstanceId)
				.singleResult();
		if(task != null) {
			for (Map.Entry<String, Object> m : map.entrySet()) {
				taskService.setVariable(task.getId(), m.getKey(), m.getValue());
			}
			endProcess(task.getId(),map);	
			return true;
		}
		return false;
	}

	/**
	 * 执行完当前流程
	 * 
	 * @param taskId
	 */
	public void endProcess(String taskId,Map<String, Object> map) {
		TaskService taskService = processEngine.getTaskService();
		taskService.complete(taskId,map);
	}

	/**
	 * 获取某个流程里当前某个人的待办流程
	 * 
	 * @param processDefinitionKey
	 * @param assignee
	 */
	public List<Task> findWillsByAssignee(String processDefinitionKey, String assignee) {
		// 2.得到TaskService对象
		TaskService taskService = processEngine.getTaskService();
		// 3.根据流程定义的key,负责人assignee来实现当前用户的任务列表查询
		List<Task> list = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey)
				.taskAssignee(assignee).list();
		return list;
	}

	/**
	 * 查询历史流程实例，就是 查找按照某个流程定义的规则一共执行了多少次流程
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	public List<HistoricProcessInstance> findDonesByAssignee(String processDefinitionKey) {
		List<HistoricProcessInstance> list = processEngine.getHistoryService().createHistoricProcessInstanceQuery()
				.processDefinitionKey(processDefinitionKey)// 流程定义ID
				.list();
		return list;
	}

	/**
	 * 查询历史任务，就是查询摸一次流程的执行一共经历了多少个任务
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List<HistoricVariableInstance> findHisTaskList(String processInstanceId) {
		List<HistoricVariableInstance> list = processEngine.getHistoryService().createHistoricVariableInstanceQuery()
				.processInstanceId(processInstanceId).list();
		return list;
	}
	
	/**
	 * 获取某个人的历史流程
	 * @param taskAssignee
	 * @return
	 */
    public List<HistoricTaskInstance> findHistoryTask(String taskAssignee){
        List<HistoricTaskInstance> list = processEngine.getHistoryService()//与历史数据（历史表）相关的Service
                        .createHistoricTaskInstanceQuery()//创建历史任务实例查询
                        .taskAssignee(taskAssignee)//指定历史任务的办理人
                        .list();
        return list;
    }
    
}
