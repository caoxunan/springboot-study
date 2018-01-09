package com.cxn.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * 德鲁伊配置类
 * @authorasus
 *
 */
@Configuration
public class DuridConfig {
	@Bean(name="statFilter")
	public Filter statFilter(){
		StatFilter stat=new StatFilter();
		return stat; 
	}
	@Bean(name = "dataSource")
	public DataSource druidDataSource(@Value("${spring.datasource.driver-class-name}") String driver,
			@Value("${spring.datasource.url}") String url,
			@Value("${spring.datasource.username}") String username,
			@Value("${spring.datasource.password}") String password ,
			Filter ... filters)  {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(driver);
		druidDataSource.setUrl(url);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);
		druidDataSource.setValidationQuery("select 1");
		druidDataSource.setTestWhileIdle(true);
		druidDataSource.setMinIdle(5);
		druidDataSource.setMinEvictableIdleTimeMillis(300000);
		druidDataSource.setTestOnBorrow(true);
		druidDataSource.setRemoveAbandoned(true);
		druidDataSource.setRemoveAbandonedTimeout(100000);
		druidDataSource.setLogAbandoned(true);
		if(filters!=null&&filters.length>0){
			druidDataSource.setProxyFilters(Arrays.asList(filters));
		}
		return druidDataSource;
	}

	@Bean
	public ServletRegistrationBean druidServlet() {
		ServletRegistrationBean reg = new ServletRegistrationBean();
		reg.setServlet(new StatViewServlet());
		reg.addUrlMappings("/druid/*");
		//reg.addInitParameter("allow", "127.0.0.1");
		//reg.addInitParameter("deny","");
		reg.addInitParameter("loginUsername", "midai");
		reg.addInitParameter("loginPassword", "midai");
		return reg;
	}
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}
}
