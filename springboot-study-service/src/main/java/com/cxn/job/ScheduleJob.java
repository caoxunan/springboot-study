package com.cxn.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class ScheduleJob implements SimpleJob{

	@Override
	public void execute(ShardingContext shardingContext) {
		
		// 1.当分片数为1时,在同一个zookepper和jobname情况下,多台机器部署了Elastic job时,只有拿到shardingContext.getShardingItem()为0的机器得以执行,其他的机器不执行  
		// 2.当分片数大于1时,假如有3台服务器，分成10片，则分片项分配结果为服务器A=0,1,2;服务器B=3,4,5;服务器C=6,7,8,9。此时每台服务器可根据拿到的shardingItem值进行相应的处理，  
		// 举例场景:  
		// 假如job处理数据库中的数据业务,方法为:A服务器处理数据库中Id以0,1,2结尾的数据,B处理数据库中Id以3,4,5结尾的数据,C处理器处理6,7,8,9结尾的数据,合计处理0-9为全部数据  
		// 如果服务器C崩溃，Elastic Job自动进行进行失效转移，将C服务器的分片转移到A和B服务器上，则分片项分配结果为服务器A=0,1,2,3,4;服务器B=5,6,7,8,9  
		// 此时,A服务器处理数据库中Id以0,1,2,3,4结尾的数据,B处理数据库中Id以5,6,7,8,9结尾的数据,合计处理0-9为全部数据.
		System.out.println("开始执行定时任务  " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E").format(new Date()));	
	}
	
}
