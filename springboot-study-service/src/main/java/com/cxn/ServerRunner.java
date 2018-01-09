package com.cxn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath*:META-INF/spring/*.xml")
public class ServerRunner {
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ServerRunner.class);
		app.run(args);
	}
	
}
