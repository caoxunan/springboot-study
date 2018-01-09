package com.cxn.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cxn.ServerRunner;
import com.cxn.mapper.StudyTableMapper;
import com.cxn.model.StudyTableModel;
import com.cxn.service.StudyTableService;

/**
 * ClassName: SpringBootMybatisJunitTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: <B>启动Spring容器,方法测试</B>. <br/>
 * date: 2017年9月13日 上午11:22:50 <br/>
 *
 * @author 曹旭楠  
 * @version 
 * @since JDK 1.7
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServerRunner.class)// 指定spring-boot的启动类 
public class SpringBootMybatisJunitTest {

	@Autowired
	private  StudyTableMapper mapper;
	
	@Autowired
	private  StudyTableService service;

	@Test
	// 测试方法：mapper.method1();
	public void testMethod1(){
		
		long id = 10;
		StudyTableModel mapperModel = mapper.getById(id);
		System.out.println(mapperModel);
		
		StudyTableModel model = service.getByIdUseMapper(id );
		System.out.println(model);

	}// end method

}// end class
