package com.cxn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxn.common.pojo.ServiceException;

@Controller
@RequestMapping(path = "/exception")
public class ExceptionController {

	@PostMapping(path="/demo1")
	@ResponseBody
	public String exceptionDemo1(){

		try {
			int y = 1/0;
			System.out.println(y);
		} catch (Exception e) {
			// errorCode = 1代表业务处理错误
			throw new ServiceException(1, e.getMessage());
		}

		return "success";
	}

	@PostMapping(path="/demo2")
	@ResponseBody
	public String exceptionDemo2(){

		int y = 1/0;
		System.out.println(y);
		return "success";

	}

}
