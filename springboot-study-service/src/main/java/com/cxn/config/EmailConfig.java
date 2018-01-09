package com.cxn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;  
  
@Component  
public class EmailConfig {  
  
    /** 
     * 发件邮箱，通过set方法将只注入bean中 
     */  
    @Value("${spring.mail.username}")  
    private String emailFrom;  
  
    public String getEmailFrom() {  
        return emailFrom;  
    }  
  
    public void setEmailFrom(String emailFrom) {  
        this.emailFrom = emailFrom;  
    }  
      
} 