package com.cxn.config;

import java.util.Properties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@Configuration
public class ActiveMQConfig {  
	@Value("${spring.activemq.broker-url}")
	private String brokerUrl;
	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory (){  
		Properties props = new Properties();  
		props.setProperty("prefetchPolicy.queuePrefetch", "1000");  
		props.setProperty("prefetchPolicy.queueBrowserPrefetch", "500");  
		props.setProperty("prefetchPolicy.durableTopicPrefetch", "100");  
		props.setProperty("prefetchPolicy.topicPrefetch", "32766");  
		ActiveMQConnectionFactory activeMQConnectionFactory =  
				new ActiveMQConnectionFactory(  
						ActiveMQConnectionFactory.DEFAULT_USER,  
						ActiveMQConnectionFactory.DEFAULT_PASSWORD,  
						ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
		activeMQConnectionFactory.setProperties(props);
		activeMQConnectionFactory.setBrokerURL(brokerUrl);
		return activeMQConnectionFactory;  
	}  
}
