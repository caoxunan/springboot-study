package com.cxn.activiti;

import java.util.Date;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cxn.model.User;

public class ProcessVariable {


	private ProcessEngine processEngine;

	@Before
	public void initProcessEngine(){

		// 获取流程引擎,会默认取resources下读取activiti.cfg.xml文件
		// ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

		// 初始化流程引擎
		processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
				.buildProcessEngine();

		System.out.println("创建完成：ProcessEngine+"+processEngine);

	}

	// 1.部署流程
	@Test
	public void deployProcessDefinition(){

		// 获得仓库服务类repositoryService
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// 得到部署对象
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		// 加载流程定义文件只能一个一个的加载
		deploymentBuilder.addClasspathResource("META-INF/diagram/VariableProcess.bpmn");
		deploymentBuilder.addClasspathResource("META-INF/diagram/VariableProcess.png");
		// 设置部署的流程的名字
		deploymentBuilder.name("VariableProcess请假流程");
		// deployment对应数据库的表
		Deployment deploy = deploymentBuilder.deploy();

		// 输出相关信息：对应数据库的字段
		System.out.println("部署对象的id:" + deploy.getId());
		System.out.println("部署对象的名称:" + deploy.getName());
		System.out.println("部署的时间:" + deploy.getDeploymentTime());

	}


	// 2.启动流程实例
	@Test
	public void startProcessInstance(){

		// 流程定义的key , act_re_procdef表中的key字段(流程图的id)
		String processDefinitionKey = "VariableProcess";
		// 获得runtimeService
		RuntimeService runtimeService = processEngine.getRuntimeService();
		// 使用流程定义的key启动实例，可以按照流程最新的版本启动，得到流程实例对象
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);

		//输出相关信息：对应数据库的字段—对应页面
		System.out.println("流程执行的ID："+processInstance.getId());
		//现在值和执行对象ID一样，后面会讲不一样的地方
		System.out.println("流程实例ID："+processInstance.getProcessInstanceId());
		System.out.println("流程定义ID："+processInstance.getProcessDefinitionId());

	}

	// 3.查询办理人的个人任务
	@Test
	public void queryMyTask(){

		// 任务办理人
		String assignee = "张三";
		// 执行过程相关的service，用来操作任务节点(userTask节点)
		TaskService taskService = processEngine.getTaskService();
		// 得到任务查询对象:用来查询当前任务相关的信息--对应表
		TaskQuery taskQuery = taskService.createTaskQuery();
		// 指定任务办理人
		taskQuery.taskAssignee(assignee);

		// 列表查询
		List<Task> list = taskQuery.list();

		if (null != list && list.size() > 0) {
			//遍历打印
			for (Task task : list) {//任务对象
				System.out.println("任务ID:"+task.getId());
				System.out.println("任务名称:"+task.getName());
				System.out.println("任务办理人:"+task.getAssignee());
				System.out.println("任务创建时间:"+task.getId());
				System.out.println("任务办理经历的时间："+task.getDueDate());
				System.out.println("流程执行对象ID："+task.getExecutionId());
				System.out.println("流程实例ID："+task.getProcessInstanceId());
				System.out.println("流程定义ID："+task.getProcessDefinitionId());
			}

		}

	}

	// 3.在当前任务节点设置流程变量
	@Test
	public void setVariable(){

		// 使用任务的service
		TaskService taskService = processEngine.getTaskService();

		String taskId = "25002";// 20004
		// 设置流程变量（提示：如果流程变量key不存在，则工作流会创建一个新的流程变量）-- 和流程实例绑定的
		taskService.setVariable(taskId , "请假天数", 4);	// 数字
		taskService.setVariable(taskId , "请假原因", "约会");	// 字符串
		taskService.setVariable(taskId , "请假日期", new Date());	// 日期

		// 设置本地流程变量:和任务绑定的流程变量
		taskService.setVariableLocal(taskId, "审核批注", "望批准!");

		// 复杂数据类型的设置
		User user = new User();
		user.setAge(10);
		user.setHeight(1.78);
		user.setPassword("password");
		user.setUsername("username");
		taskService.setVariable(taskId, "user", user);
		System.out.println("流程变量设置成功!");

	}

	// 4.完成办理人的个人任务(流程未结束继续办理)
	@Test
	public void completeTask(){

		// 找到要办理的任务id
		String taskId = "20004";
		// 获得任务服务类
		TaskService taskService = processEngine.getTaskService();
		// 完成任务：通过任务id
		taskService.complete(taskId);
		//输出办理的任务ID
		System.out.println("完成的任务的ID："+taskId);

	}

	// 6.在下一个节点查询流程变量
	@Test
	public void queryProcessVariable(){

		TaskService taskService = processEngine.getTaskService();

		TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee("李四");

		List<Task> list = taskQuery.list();

		for (Task task : list) {

			String taskId = task.getId();
			System.out.println("taskId:" + taskId);
			Integer days = (Integer) taskService.getVariable(taskId, "请假天数");
			String reason = (String) taskService.getVariable(taskId, "请假原因");
			Date date = (Date) taskService.getVariable(taskId, "请假日期");
			String description = (String) taskService.getVariableLocal(taskId, "审核批注");
			User user = (User) taskService.getVariable(taskId, "user");

			System.out.println("请假天数:" + days);
			System.out.println("请假原因:" + reason);
			System.out.println("请假日期:" + date);
			System.out.println("请加批注:" + description);
			System.out.println("user:" + user);

		}

	}

	// 流程变量的主要api
	@Test
	public void testVariableAPI(){

		/*		// 1.使用任务的service
		TaskService taskService = processEngine.getTaskService();
		// 使用运行时service
		RuntimeService runtimeService = processEngine.getRuntimeService();

		// 设置
		// 设置一个流程变量
		taskService.setVariable(taskId, variableName, value);
		// 设置多个流程变量(map(key 流程变量名，value 流程变量值))
		taskService.setVariables(taskId, variables);

		// 2.完成任务的时候设置流程变量，只支持map
		taskService.complete(taskId, variables);

		// 3.流程正在执行的时候（runtimeService）
		runtimeService.setVariable(executionId, variableName, value);// 一次设置一个
		runtimeService.setVariables(executionId, variables);// 设置多个

		// 4.流程启动的时候（runtimeService）
		runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);// 设置多个

		// 获取
		// 使用taskService取流程变量
		taskService.getVariable(taskId, variableName);
		taskService.getVariables(taskId);// 当前任务节点下的所有任务节点

		// 使用runtimeService获取
		runtimeService.getVariables(executionId, variableNames);
		runtimeService.getVariable(executionId, variableName);*/

	}

	@Test
	public void queryHistoryInstance(){

		// 历史的表的查询，全部使用historyService
		HistoryService historyService = processEngine.getHistoryService();

		// 1.查询历史的流程实例		historyService.createHistoricProcessInstanceQuery();
		// 2.查询历史的活动			historyService.createHistoricActivityInstanceQuery();
		// 3.查询历史的任务			historyService.createHistoricTaskInstanceQuery();
		// 4.查询历史的流程变量		historyService.createHistoricVariableInstanceQuery();

		// 所有的API几乎一摸一样，只需要知道一种，其它的就知道了
		HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
		
		// 演示查询历史的任务
		List<HistoricTaskInstance> list = query
				.processInstanceId("20001")
				//		.processDefinitionId(processDefinitionId)	// 流程定义id
				//		.processDefinitionKey(processDefinitionKey)	// 流程定义key例如：helloworldProcess
				//		.listPage(firstResult, maxResults)			// 分页
				//		.singleResult()
				.list();
		for (HistoricTaskInstance historicTaskInstance : list) {
			// do something~
		}

	}

}
