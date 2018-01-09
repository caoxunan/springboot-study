package com.cxn.test;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cxn.ServerRunner;

/**
 * ClassName:ActivitiTest <br/>
 * @since    JDK 1.7
 * @see 	 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServerRunner.class)// 指定spring-boot的启动类 
public class ActivitiTest {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;

	Logger logger = LoggerFactory.getLogger(ActivitiTest.class);

	@Test
	@Deployment
	public void testProcess(){
		
		System.out.println("==== Do Something ====");
		System.out.println("runtimeService：" + runtimeService);
		System.out.println("taskService：" + taskService);
		System.out.println("historyService：" + historyService);
		System.out.println("repositoryService：" + repositoryService);
		
	}

}

