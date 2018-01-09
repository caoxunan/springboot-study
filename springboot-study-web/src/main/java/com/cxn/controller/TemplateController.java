package com.cxn.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Controller
@RequestMapping(path="/template")
public class TemplateController {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@RequestMapping(path="/velocity",method = {RequestMethod.GET})
	@ResponseBody
	public String generateStaticFileVelocity(){

		// 初始化模板引擎
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		// 获取模板文件
		Template t = ve.getTemplate("template/email.vm", "UTF-8");
		// 设置变量
		VelocityContext context = new VelocityContext();
		context.put("orderId", "JJ-CD-180104-001");
		context.put("runState", "Velocity");
		// 输出
		Writer writer;
		try {
			writer = new FileWriter(new File("email-velocity.html"));
			t.merge(context,writer);
			writer.flush();
			writer.close(); 
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		return "success";
	}

	@RequestMapping(path="/freemaker",method = {RequestMethod.GET})
	@ResponseBody
	public String generateStaticFileFreemarker(){

		try {
			// 加载模板文件
			freemarker.template.Template template = freeMarkerConfigurer.getConfiguration().getTemplate("email.vm");

			// 设置输出文件的位置
			FileWriter fileWriter = new FileWriter(new File("email-freemarker.html"));
			Map<String, Object> context = new HashMap<>();
			context.put("orderId", "JJ-CD-180104-001");
			context.put("runState", "Freemarker");
			
			// 开始输出
			template.process(context, fileWriter);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		return "success";
	}
}