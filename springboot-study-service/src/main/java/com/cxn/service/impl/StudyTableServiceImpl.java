package com.cxn.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cxn.mapper.StudyTableMapper;
import com.cxn.model.StudyTableModel;
import com.cxn.service.StudyTableService;

@Service(version="1.0.0",protocol="dubbo")
public class StudyTableServiceImpl implements StudyTableService {

	@Autowired
	private StudyTableMapper studyTableMapper;
	
	@Override
	public StudyTableModel getById(long modelId) {
		try {
			// 1.注册驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 2.获得连接
			String url = "jdbc:mysql://localhost:3306/studytest_db";
			String user = "root";
			String password = "123";
			Connection conn = DriverManager.getConnection(url, user, password);
			// 3.sql的承载对象
			String sql = "  select * from tbl_study_table1 where id = " + modelId;
			PreparedStatement preState = conn.prepareStatement(sql);
			// 4.执行sql获得结果
			ResultSet rs = preState.executeQuery();
			// 5.处理结果
			StudyTableModel model = new StudyTableModel();
			while (rs.next()) {
				long id = rs.getInt("id");
				String text = rs.getString("text");
				int flag = rs.getInt("flag");
				String createTime = rs.getString("create_time");
				String updateTime = rs.getString("update_time");
				System.out.println(id + ":::" + text + ":::" + flag +":::" + createTime + ":::" + updateTime);
				model.setId(id);
				model.setFlag(flag);
				model.setText(text);
				model.setCreateTime(createTime);
				model.setUpdateTime(updateTime);
			}
			// 6.释放资源
			rs.close();
			preState.close();
			conn.close();
			
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public StudyTableModel getByIdUseMapper(long id) {
		return studyTableMapper.getById(id);
	}

	@Override
	public long deleteById(long id) {
		
		System.out.println("Before执行删除。。");
		
		long result = studyTableMapper.deleteById(id);
		int i = 10/0;
		System.out.println(i);
		System.out.println("After执行删除。。");
		
		return result;
	}

	
	
}
