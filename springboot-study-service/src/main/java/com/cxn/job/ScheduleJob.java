package com.cxn.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class ScheduleJob implements SimpleJob{

	@Override
	public void execute(ShardingContext shardingContext) {
		System.out.println("开始执行定时任务  " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E").format(new Date()));	
	}
	
}
