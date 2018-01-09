package com.cxn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxn.annotation.DemoActivity;
import com.cxn.annotation.DemoActivityParam;
import com.cxn.model.User;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/system")
public class SystemController {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemController.class);
	
	@ApiOperation(value = "用户登陆")
	@RequestMapping(path="/login",method={RequestMethod.GET})	
	public ResponseEntity<User> login(@RequestParam("username") String username, @RequestParam("password")  String password){
		
		logger.debug(">>>>>>>>>>coming in login method.<<<<<<<<<");
		
		// 1.经过springMVC的前置拦截器后进入方法
		// 2.取出用户名去数据库查询用户
		// 3.校验密码
		// 4.密码正确，利用username+System.currentTimeMillis(),在取md5作为token
		// 5.将token作为key，当前用户的信息CurrentUser作为value存入redis
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setAge(25);
		user.setHeight(170);
		
		return ResponseEntity.ok(user);
	}
	
	@GetMapping(path="/anno/test")
	@ResponseBody
	@DemoActivity
	public String test(@RequestParam("test") @DemoActivityParam String test){
		logger.info("进入。。。。test....方法//////");
		return "success";
	}
	
}
