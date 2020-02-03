package com.mpri.aio.untils.activiti.service;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.untils.activiti.vo.ActivitiVo;

@Service
public class ActivitiService {

	@Autowired
	private ProcessEngine processEngine;
	
	
	
	/**
	 * 获取某个人的待办事项
	 * @param processDefinitionKey
	 * @param taskAssignee
	 * @return
	 */
	public List<ActivitiVo> getWilDoList(String processDefinitionKey,String taskAssignee){        
		TaskService taskService = processEngine.getTaskService();		
		List<Task> list = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).
				taskAssignee(taskAssignee).list();
		List<ActivitiVo> activitiVos = new ArrayList<ActivitiVo>();
		for (Task task : list) {
			setActivitiVo(activitiVos, taskService,task);
		}
		return activitiVos;
	}
	
	
	public PageIo<ActivitiVo> getWilDoByPage(String processDefinitionKey,String taskAssignee,int pageNo,int pageSize){        
		TaskService taskService = processEngine.getTaskService();		
		int start = (pageNo -1) * pageSize;
		List<Task> list = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).
				taskAssignee(taskAssignee).listPage(start,pageSize);
		System.out.println("getWilDoByPage===list==================="+list.size() );
		long count = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).
				taskAssignee(taskAssignee).count();
		System.out.println("getWilDoByPage===count=================="+count );
		List<ActivitiVo> activitiVos = new ArrayList<ActivitiVo>();
		for (Task task : list) {
			setActivitiVo(activitiVos, taskService,task);
		}
		PageIo<ActivitiVo> pageIos = new PageIo<ActivitiVo>(activitiVos);
		pageIos.setCount(count);
		System.out.println("getWilDoByPage===pageIos==================" );
		return pageIos;
	}

	private void setActivitiVo(List<ActivitiVo> activitiVos,TaskService taskService , Task task){
		ActivitiVo activitiVo = new ActivitiVo();
		activitiVo.setId(task.getId());
		activitiVo.setProcessInstanceId(task.getProcessInstanceId());
		activitiVo.setName(task.getName());
		activitiVo.setUserId((String) taskService.getVariable(task.getId(), GlobalStr.USER_ID));
		activitiVo.setUserName((String) taskService.getVariable(task.getId(), GlobalStr.USER_NAME));
		activitiVos.add(activitiVo);
	}
	
}
