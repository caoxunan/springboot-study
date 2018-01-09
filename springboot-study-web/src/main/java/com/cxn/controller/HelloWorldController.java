package com.cxn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cxn.model.StudyTableModel;
import com.cxn.service.StudyTableService;

@Controller
public class HelloWorldController {

	@Reference(version = "1.0.0")
	private StudyTableService studyTableService;

	@RequestMapping(path = "/hello",method={RequestMethod.GET})
	@ResponseBody
	public String helloWorld(){
		System.out.println("helloWorld!");
		return "helloWorld!";
	}

	@RequestMapping(path="/getById/{id}",method={RequestMethod.GET})
	@ResponseBody
	public StudyTableModel getById(@PathVariable("id") long id){
		System.out.println("进入getById方法:" + id);
		return studyTableService.getById(id);
	}

	@RequestMapping(path="/getByIdUseMapper/{id}",method={RequestMethod.GET})
	@ResponseBody
	public StudyTableModel getByIdUseMapper(@PathVariable("id") long id){
		System.out.println("进入getById方法:" + id);
		return studyTableService.getByIdUseMapper(id);
	}

	@RequestMapping(path="/deleteById/{id}",method={RequestMethod.GET})
	@ResponseBody
	public String deleteById(@PathVariable("id") long id){
		System.out.println("进入deleteById方法:" + id);
		long resultNum = studyTableService.deleteById(id);
		System.out.println("受影响条数:" + resultNum);
		return "success";
	}
	
	
	
}
