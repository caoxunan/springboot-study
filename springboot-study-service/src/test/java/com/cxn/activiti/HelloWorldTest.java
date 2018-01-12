package com.cxn.activiti;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;

public class HelloWorldTest {


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
		deploymentBuilder.addClasspathResource("META-INF/diagram/HelloWorld.bpmn");
		deploymentBuilder.addClasspathResource("META-INF/diagram/HelloWorld.png");
		// 设置部署的流程的名字
		deploymentBuilder.name("HelloWorld请假流程");
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
		String processDefinitionKey = "helloworld";
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
		String assignee = "范冰冰";
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

	// 4.完成办理人的个人任务(流程未结束继续办理)
	@Test
	public void completeTask(){

		// 找到要办理的任务id
		String taskId = "10002";
		// 获得任务服务类
		TaskService taskService = processEngine.getTaskService();
		// 完成任务：通过任务id
		taskService.complete(taskId);
		//输出办理的任务ID
		System.out.println("完成的任务的ID："+taskId);

	}

	// 5.测试部署对象信息的查询
	@Test
	public void deploymentQuery(){

		// 流程如果还没有运行，那么只有一个对象可以操作，就是仓库service
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// 获得部署查询对象
		DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
		// where条件
		List<Deployment> list = deploymentQuery
				// .deploymentId(deploymentId)// 通过部署id查询
				// .deploymentName(name)//通过部署名称查询。
				//按照部署时间倒序排序
				// .orderByDeploymenTime().desc()
				// .count()//统计
				//查询结果
				.list()//列表显示
				// .singleResult();//查询出单条记录
				;

		if(list!=null && list.size()>0){
			for (Deployment dm : list) {
				System.out.println("部署id："+dm.getId());
				System.out.println("部署名称："+dm.getName());
				System.out.println("部署时间："+dm.getDeploymentTime());
				System.out.println("******************************");
			}
		}
	}

	// 6.查询流程定义
	@Test
	public void queryProcessDefinition(){

		// 获得repositoryService对象
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// 创建流程定义查询对象
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		// 流程定义集合
		List<ProcessDefinition> list = processDefinitionQuery
				//添加where条件
				// .processDefinitionId(processDefinitionId)//根据流程定义的ID查询
				// .processDefinitionKey(processDefinitionKey)//根据流程定义的key查询
				// .processDefinitionName(processDefinitionName)//根据流程定义的名称查询
				// .processDefinitionVersion(processDefinitionVersion)//根据流程定义的版本查询
				// .deploymentId(deploymentId)//使用部署ID查询---关联了部署对象的表了
				// 排序
				// .orderByProcessDefinitionVersion().asc()//按照流程定义的版本升序排列
				// .orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

				// 返回结果集相关
				// .count()//返回结果集的数量
				// .listPage(firstResult, maxResults)//分页查询
				.list()
				// .singleResult()//返回唯一结果集
				;
		// ProcessDefinition对象对应的流程定义表
		if(list!=null && list.size()>0){
			for (ProcessDefinition processDefinition : list) {
				System.out.println("流程定义的ID："+processDefinition.getId());
				System.out.println("流程定义的名称："+processDefinition.getName());
				System.out.println("流程定义的key："+processDefinition.getKey());
				System.out.println("流程定义的版本："+processDefinition.getVersion());
				System.out.println("流程的部署ID："+processDefinition.getDeploymentId());
				System.out.println("资源文件（BPMN）："+processDefinition.getResourceName());
				System.out.println("图片资源文件（PNG）："+processDefinition.getDiagramResourceName());
				System.out.println("***********************************");
			}
		}

	}

	// 7.流程定义的删除
	@Test
	public void deleteProcessDefinition(){

		/**
		 * 因为删除的是流程定义，而流程定义的部署是属于仓库服务的，
		 * 所以应该先得到RepositoryService（你要删除流程定义，得通过删除部署来操作）
		 * 如果该流程定义下没有正在运行的流程，则可以用普通删除。如果是有关联的信息（流程一旦启动了），用级联删除。
		 * 项目开发中使用级联删除的情况比较多，删除操作一般只开放给超级管理员使用。
		 */
		// 部署对象Id
		String deploymentId = "2501";
		// 流程部署与流程定义相关
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// 不带级联的删除：只能删除没有启动或者已经结束的流程
		// repositoryService.deleteDeployment(deploymentId);
		// 带级联的删除，会将正在流程中的表一起删除
		repositoryService.deleteDeployment(deploymentId, true);
		System.out.println("删除流程定义成功，删除的部署id：："+deploymentId);
		// act_re_deployment、act_re_procdef、act_ge_bytearray都没有对应的数据了

	}

	// 8.获得流程定义文档的资源（查看流程图附件）
	@Test
	public void getProcessDefinitionResource() throws IOException{

		/**
		 * 1.deploymentId为流程部署Id
		 * 2.resourceName为act_ge_bytearray表中NAME_列的值
		 * 3.使用repositoryService的getDeploymentResourceNames方法可以获得指定部署下的所有文件的名称
		 * 4.使用repositoryService的getResourceAsStream方法，传入流程部署Id和资源图片名称可以获取部署下指定名称文件的输入流
		 * 5.使用IO操作（也可以使用工具类）
		 */

		// 根据部署对象的id来查
		String deploymentId = "1";
		List<String> list = processEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);

		// 首先要得到图片的资源名称
		String imageResourcesName = "";

		if (null != list && list.size() > 0) {
			for (String str : list) {
				System.out.println("str:" + str);
				// 查找png图片资源
				if (str.indexOf(".png") > -1) {
					imageResourcesName = str;
				}
			}
		}

		System.out.println("图片资源名称:" + imageResourcesName);

		if (imageResourcesName != null) {
			// 根据部署id和图片资源名称获取图片的输入流
			// 创建输入流关联源文件
			InputStream in = processEngine.getRepositoryService().getResourceAsStream(deploymentId, imageResourcesName);

			// 创建输出流关联目标文件
			// 将输入流中的数据写入磁盘中
			String[] split = imageResourcesName.split("/");
			String imageName = split[split.length - 1];
			System.out.println(imageName);
			File file = new File("E:/" + imageName);
			FileOutputStream out = new FileOutputStream(file);
			// 读取数据到输出流
			for (int b = 0; (b=in.read()) != -1;) {
				out.write(b);
			}
			// 释放流
			in.close();
			out.close();
			System.out.println("输出图片资源成功！");

		}

	}

	// 9.查询最新版本的流程定义
	@Test
	public void queryProcessDefinitionForLastVersion(){

		// 得到repositoryService
		RepositoryService repositoryService = processEngine.getRepositoryService();

		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()// 创建流程定义查询对象
				.orderByProcessDefinitionVersion()	// 根据版本升序排序
				.asc()
				.list();

		//过滤出最新的版本：
		//使用map过滤：
		//这里：key：流程定义的key；value：流程定义的对象
		//保证顺序：LinkedHashMap 是HashMap的一个子类，保存了记录的插入顺序，在遍历LinkedHashMap时，先得到的记录肯定是先插入的
		Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();

		if (null != list && list.size() > 0) {
			for (ProcessDefinition pd : list) {
				map.put(pd.getKey(), pd);
			}
		}
		//重新组织最新版本的流程定义的集合
		List<ProcessDefinition> pdList= new ArrayList<ProcessDefinition>(map.values());

		//遍历显示
		if(pdList!=null && pdList.size()>0){
			for (ProcessDefinition processDefinition : pdList) {
				System.out.println("流程定义的ID："+processDefinition.getId());
				System.out.println("流程定义的名称："+processDefinition.getName());
				System.out.println("流程定义的key："+processDefinition.getKey());
				System.out.println("流程定义的版本："+processDefinition.getVersion());
				System.out.println("流程的部署ID："+processDefinition.getDeploymentId());
				System.out.println("资源文件（BPMN）："+processDefinition.getResourceName());
				System.out.println("图片资源文件（PNG）："+processDefinition.getDiagramResourceName());
				System.out.println("***********************************");
			}
		}

	}

	// 10.删除流程定义（删除key相同的所有不同版本的流程定义）
	@Test
	public void deleteProcessDefinitionCascadeByKey(){
		
		// 删除流程定义--通过部署id删除--级联删除
		//流程定义的KEY
		String processDefinitionKey ="helloworld";
		//通过流程定义的key，查询其所有版本的流程定义
		List<ProcessDefinition> list = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey)
				.list();

		//遍历删除
		if(list!=null && list.size()>0){
			
			for (ProcessDefinition pd : list) {
				//与查询部署对象和流程定义表的数据数据相关
				processEngine.getRepositoryService().deleteDeployment(pd.getDeploymentId(), true);
						
				System.out.println("删除流程成功，删除的部署id：："+pd.getDeploymentId());
			}
		}
		
	}



	@Test
	public void getAllKindsOfService(){

		// 1.仓库服务类
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// 1.1 用来定义流程部署的相关参数
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		// 1.2 删除流程定义
		String deploymentId = "1";
		repositoryService.deleteDeployment(deploymentId);

		// 2.流程执行服务类，可以从其中获得关于流程执行相关的信息
		RuntimeService runtimeService = processEngine.getRuntimeService();

		// 3.任务服务类，可以从其中获得任何任务的信息
		TaskService taskService = processEngine.getTaskService();

		// 4.查询历史信息的类
		HistoryService historyService = processEngine.getHistoryService();



	}


}
