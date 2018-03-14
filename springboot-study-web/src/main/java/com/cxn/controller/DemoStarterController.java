package com.cxn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cxn.demo.service.DemoService;


@RestController
@RequestMapping(value="/demoStarter")
public class DemoStarterController {

	@Autowired
	private DemoService demoService;

	@RequestMapping(value="/test", method=RequestMethod.GET)
	@ResponseBody
	public String testDemoStarter(@RequestParam("value") String value){

		String jointString = demoService.jointString(value);
		System.out.println(jointString);
		return jointString;
	}

}
