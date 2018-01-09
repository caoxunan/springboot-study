package com.cxn.service.impl;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.alibaba.dubbo.config.annotation.Service;
import com.cxn.config.EmailConfig;
import com.cxn.service.EmailService;

import freemarker.template.Template;

@SuppressWarnings("deprecation")
@Service(version="1.0.0",protocol="dubbo")
public class EmailServiceImpl implements EmailService {  

	@Autowired  
	private EmailConfig emailConfig;  
	@Autowired  
	private JavaMailSender mailSender;  
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	private static VelocityEngine velocityEngine;

	static {
		velocityEngine = new VelocityEngine();
		velocityEngine.setProperty("input.encoding", "UTF-8");
		velocityEngine.setProperty("output.encoding", "UTF-8");
		velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		velocityEngine.init();
	}

	@Override
	public void sendSimpleMail(String sendTo, String titel, String content) {  
		SimpleMailMessage message = new SimpleMailMessage();  
		message.setFrom(emailConfig.getEmailFrom());  
		message.setTo(sendTo);  
		message.setSubject(titel);  
		message.setText(content);  
		mailSender.send(message);  
	}  

	@Override
	public void sendHtmlMail(String sendTo, String titel, String html) {
		MimeMessage message = null;

		try {
			message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(emailConfig.getEmailFrom());
			helper.setTo(sendTo);
			helper.setSubject(titel);
			helper.setText(html, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mailSender.send(message);
	}

	@Override
	public void sendAttachmentsMail(String sendTo, String titel, String content, Map<String, File> attachments) {

		MimeMessage mimeMessage = mailSender.createMimeMessage();  

		try {  
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);  
			helper.setFrom(emailConfig.getEmailFrom());  
			helper.setTo(sendTo);  
			helper.setSubject(titel);  
			helper.setText(content);  
			
			if (null != attachments) {

				File file1 = new File("src/main/resources/META-INF/template/李小龙.gif");
				File file2 = new File("src/main/resources/META-INF/template/github.jpg");
				attachments.put("图片1.jpg", file1);
				attachments.put("图片2.jpg", file2);
				
				for (Entry<String, File> entry : attachments.entrySet()) {  
					helper.addAttachment(entry.getKey(), new FileSystemResource(entry.getValue()));  
				} 
			}
		} catch (Exception e) {  
			throw new RuntimeException(e);  
		}  

		mailSender.send(mimeMessage);  

	}

	@Override
	public void sendInlineMail(String sendTo, String titel) {  

		MimeMessage mimeMessage = mailSender.createMimeMessage();  

		try {  
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);  
			helper.setFrom(emailConfig.getEmailFrom());  
			helper.setTo(sendTo); 
			helper.setSubject("主题：带静态资源的邮件");

			//第二个参数指定发送的是HTML格式,同时cid:是固定的写法
			helper.setText("<html><body>带静态资源的邮件内容 图片:<img src='cid:picture' /></body></html>", true);

			FileSystemResource file = new FileSystemResource(new File("src/main/resources/META-INF/template/github.jpg"));
			helper.addInline("picture",file);

		} catch (Exception e) {  
			throw new RuntimeException(e);  
		}  

		mailSender.send(mimeMessage);  
	}  

	@Override
	public void sendTemplateMail(String sendTo, String titel, Map<String, Object> content, Map<String, File> attachments) {

		MimeMessage mimeMessage = mailSender.createMimeMessage();  

		try {  
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);  
			helper.setFrom(emailConfig.getEmailFrom());  
			helper.setTo(sendTo);  
			helper.setSubject(titel);  
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "META-INF/template/email.vm", "UTF-8", content);  

			helper.setText(text, true);  
			if (null != attachments) {
				for (Entry<String, File> entry : attachments.entrySet()) {  
					helper.addAttachment(entry.getKey(), new FileSystemResource(entry.getValue()));  
				}
			}
		} catch (Exception e) {  
			throw new RuntimeException(e);  
		}  

		mailSender.send(mimeMessage);  
	}	

	@Override
	public void sendTemplateMailFreemarker(String sendTo, String titel, Map<String, Object> content, Map<String, File> attachments) {

		MimeMessage mimeMessage = mailSender.createMimeMessage();  

		try {  
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);  
			helper.setFrom(emailConfig.getEmailFrom());  
			helper.setTo(sendTo);  
			helper.setSubject(titel);  
			//修改 application.properties 文件中的读取路径
			// FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
			// configurer.setTemplateLoaderPath("classpath:templates");
			//读取 html 模板
			Template template = freeMarkerConfigurer.getConfiguration().getTemplate("email.vm");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, content);
			helper.setText(html, true);

			if (null != attachments) {
				for (Entry<String, File> entry : attachments.entrySet()) {  
					helper.addAttachment(entry.getKey(), new FileSystemResource(entry.getValue()));  
				}
			}
		} catch (Exception e) {  
			throw new RuntimeException(e);  
		}  

		mailSender.send(mimeMessage);  
	}	

}  