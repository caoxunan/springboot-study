package com.cxn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cxn.service.DubboService;

@Controller
@RequestMapping(path="/dubbo")
public class DubboController {
	
	@Reference(version = "1.0.0")
	private DubboService dubboService;
	
	@ResponseBody
	@RequestMapping(path="/test",method={RequestMethod.GET})
	public String testDubbo(){
		
		System.out.println(">>>>>>>>>>>>>"+dubboService);
		
		return dubboService.getResult();
		
	}
	
}
