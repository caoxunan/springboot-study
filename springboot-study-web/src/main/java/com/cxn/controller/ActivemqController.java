package com.cxn.controller;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cxn.activemq.MyMessageProducer;

@RestController
@RequestMapping(path="/activemq")
public class ActivemqController {
	
	@Autowired
	private MyMessageProducer messageProducer;
	
	@RequestMapping(path="/send/{message}",method={RequestMethod.GET})
	public String testSendMessage(@PathVariable String message){
		
		Destination destination = new ActiveMQQueue("test.queue"); 
		// 发送消息
		messageProducer.sendMessage(destination , message);
		
		return"success";
	}

}
