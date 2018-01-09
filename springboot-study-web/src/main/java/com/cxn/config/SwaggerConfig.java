package com.cxn.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootConfiguration
public class SwaggerConfig {

	@Bean
	public Docket controllerApi(){
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket.groupName("group-one")
		.apiInfo(apiInfo())
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.cxn.controller"))
		.paths(PathSelectors.any())// 可以使用正则regex("/user/.*")
		.build();
		
		return docket;
	}
	
	// api题头信息
	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfoBuilder().title("SpringBoot-study")
				.description("这里是描述。。。")
				.license("MIT")
				.licenseUrl("http://opensource.org/licenses/MIT")
				.contact(new Contact("电台台长", "192.168.99.99", "caoxunan121@163.com"))
				.version("1.0")
				.build();
		
		return apiInfo;
	}

}

