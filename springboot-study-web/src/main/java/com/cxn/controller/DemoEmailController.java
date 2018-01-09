package com.cxn.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cxn.service.EmailService;


@RestController  
@RequestMapping(value="/email")   
public class DemoEmailController {  
  
    @Reference(version="1.0.0")
    private EmailService emailService;  
      
    @ApiOperation(value="测试普通邮件发送", notes="普通邮件发送")  
    @RequestMapping(value = "/sendSimpleMail", method = RequestMethod.GET)  
    public  String sendSimpleMail() throws Exception {  
    	
        String sendTo = "caoxunan121@163.com";  
        String titel = "测试邮件标题";  
        String content = "测试邮件内容";  
      
        emailService.sendSimpleMail(sendTo, titel, content);  
        
        return "success";  
    }  
    
    @ApiOperation(value="html样式邮件发送", notes="html样式邮件发送")  
    @RequestMapping(value = "/sendHtmlEmail", method = RequestMethod.GET)  
    public String sendHtmlEmail(){
    	
    	String sendTo = "caoxunan121@163.com";  
    	String titel = "标题：发送Html内容";  
    	// 拼接html代码
    	StringBuffer sb = new StringBuffer();
        sb.append("<h1>大标题-h1</h1>")
                .append("<p style='color:#F00'>红色字</p>")
                .append("<p style='text-align:right'>右对齐</p>");
        String content = sb.toString();
        
    	emailService.sendHtmlMail(sendTo, titel, content);  
    	
    	return "success";
    }
    
    @ApiOperation(value="发送带附件的邮件", notes="发送带附件的邮件")  
    @RequestMapping(value = "/sendAttachmentsMail", method = RequestMethod.GET)  
    public String sendAttachmentsMail(){
    	
    	String sendTo = "caoxunan121@163.com";  
    	String titel = "标题：发送带附件的邮件";  
    	String content = "邮件内容。。。。。";
    	
		Map<String, File> attachments = new TreeMap<>();
		emailService.sendAttachmentsMail(sendTo, titel, content, attachments);
    	
    	return "success";
    }
    
    @ApiOperation(value="发送带静态资源的邮件", notes="发送带静态资源的邮件")  
    @RequestMapping(value = "/sendInlineMail", method = RequestMethod.GET)  
    public String sendInlineMail(){
    	
    	String sendTo = "caoxunan121@163.com";  
    	String titel = "标题：发送带附件的邮件";  
    	
    	emailService.sendInlineMail(sendTo, titel);
    	
    	return "success";
    }
    
    @ApiOperation(value="发送模板邮件", notes="发送模板邮件")  
    @RequestMapping(value = "/sendTemplateMailVelocity", method = RequestMethod.GET)  
    public String sendTemplateMailVelocity(){
    	
    	String sendTo = "caoxunan121@163.com";  
    	String titel = "标题：发送模板邮件";  
    	
    	Map<String, Object> content = new HashMap<>();
    	content.put("orderId", "JJ-CD-180104-001");
    	content.put("runState", "Velocity");
    	
		emailService.sendTemplateMail(sendTo, titel, content , null);
    	
    	return "success";
    }
    
    @ApiOperation(value="发送模板邮件", notes="发送模板邮件")  
    @RequestMapping(value = "/sendTemplateMailFreemarker", method = RequestMethod.GET)  
    public String sendTemplateMailFreemarker(){
    	
    	String sendTo = "caoxunan121@163.com";  
    	String titel = "标题：发送模板邮件";  
    	
    	Map<String, Object> content = new HashMap<>();
    	content.put("orderId", "JJ-CD-180104-001");
    	content.put("runState", "Freemarker");
    	
    	emailService.sendTemplateMailFreemarker(sendTo, titel, content , null);
    	
    	return "success";
    } 
}