package com.cxn.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cxn.model.StudyTableModel;

@Mapper
public interface StudyTableMapper {

	@Select(" select id, text, flag, date_format(create_time,'%Y-%m-%d %H:%s:%i') create_time,update_time from tbl_study_table1 where id = #{id}")
	@ResultMap(value="com.cxn.mapper.StudyTableMapper.StudyTableModelMap")
	public StudyTableModel getById(long id);

	@Delete(" delete from tbl_study_table1 where id = #{id}")
	public long deleteById(long id);
	
}
