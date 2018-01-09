package com.cxn.test;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.cxn.mapper.StudyTableMapper;
import com.cxn.model.StudyTableModel;

/**
 * ClassName: <B>TestMapperTest</B> <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: <B>mybatis持久层mapper的测试类</B>. <br/>
 * date: 2017年9月13日 上午11:03:57 <br/>
 *
 * @author 曹旭楠  
 * @version 1.0
 * @since JDK 1.7
 */
public class MybatisMapperJunitTest {

	/**
	 * sqlSessionFactory:<B>mybatis的sqlSession工厂</B>.
	 */
	private static SqlSessionFactory sqlSessionFactory;

	@Before
	public void setUp() throws Exception {
		
		// 设置资源路径(注意：若涉及复杂映射，需在配置文件中添加mapper.xml的位置)
		String resource = "test-mybatis-conf.xml";
		// 获取输入流，关联配置文件
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 读取配置，构建session工厂
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		/** 需要注意的是：若出现"is not known to the MapperRegistry"错误则使用下行代码注册接口类
		 *	或者在配置文件中添加<mapper class="类名全路径"/>
		 */
		// sqlSessionFactory.getConfiguration().addMapper(TestMapper.class);
	}
	
	@Test
	public void testMapperMethod() {
		
		// 获取session,参数true打开事务的自动提交，每一次操作都是个独立事务
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		
		/** 使用sqlSession.getMapper(Class class)方法传入接口类，构建DAO对象*/
		// 样例代码： TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
		StudyTableMapper studyTableMapper = sqlSession.getMapper(StudyTableMapper.class);
		
		/** 调用方法，获得结果*/
		// 样例代码：testMapper.method();
		StudyTableModel model = studyTableMapper.getById(9);
		System.out.println(model);
		
	}
}

