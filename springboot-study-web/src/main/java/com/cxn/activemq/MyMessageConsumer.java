package com.cxn.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class MyMessageConsumer {

	// 使用JmsListener配置消费者监听的队列，其中text是接收到的消息  
	@JmsListener(destination = "test.queue")
	@SendTo("out.queue")// 实现双向队列
	public String receiveQueue(String text) {
		// TODO do something
		System.out.println("Consumer--A-->>收到的报文为:"+text);  
		return"out>>" + text;
	}  

	@JmsListener(destination = "out.queue")
	public void receiveQueue1(String text) {
		// TODO do something
		System.out.println("Consumer--B-->>收到的报文为:"+text);  
	}  

}
