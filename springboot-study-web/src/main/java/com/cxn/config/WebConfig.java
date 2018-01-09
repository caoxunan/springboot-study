package com.cxn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cxn.interceptor.LoginInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
	
	 /**
     * 自定义静态资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
    
    /**
     * 拦截器(用户登录验证)
     * @param registry
     */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {	

		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
		LoginInterceptor loginInterceptor=new LoginInterceptor();
		registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
		super.addInterceptors(registry);

	}

}