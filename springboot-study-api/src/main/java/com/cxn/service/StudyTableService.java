package com.cxn.service;

import com.cxn.model.StudyTableModel;

public interface StudyTableService {

	public StudyTableModel getById(long id);
	
	public StudyTableModel getByIdUseMapper(long id);
	
	public long deleteById(long id);
	
}
