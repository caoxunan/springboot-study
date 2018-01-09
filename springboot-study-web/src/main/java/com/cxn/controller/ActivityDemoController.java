package com.cxn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cxn.service.ActivityDemoService;

@Controller
@RequestMapping(path="activity")
public class ActivityDemoController {

	@Reference(version="1.0.0")
	private ActivityDemoService activityDemoService;
	
	
	
}
