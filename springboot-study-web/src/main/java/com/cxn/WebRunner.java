package com.cxn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ImportResource("classpath*:META-INF/spring/*.xml")
@ComponentScan("com.cxn")
public class WebRunner {

	// springboot项目的启动入口
	public static void main(String[] args) {
		
		// SpringApplication.run(MyApplication.class, args);
		SpringApplication app = new SpringApplication(WebRunner.class);
		app.run(args);
		
	}
	
}
