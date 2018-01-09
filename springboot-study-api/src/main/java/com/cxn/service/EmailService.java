package com.cxn.service;
import java.io.File;
import java.util.Map;

public interface EmailService {  
  
    /** 
     * 发送简单邮件 
     * @param sendTo 收件人地址 
     * @param titel  邮件标题 
     * @param content 邮件内容 
     */  
    public void sendSimpleMail(String sendTo, String titel, String content);  
      
    /** 
     * 发送带附件邮件 
     * @param sendTo 收件人地址 
     * @param titel  邮件标题 
     * @param content 邮件内容 
     * @param attachments<文件名，附件> 附件列表 
     */  
    public void sendAttachmentsMail(String sendTo, String titel, String content, Map<String, File> attachments);  
      
    /** 
     * 发送模板邮件 使用velocity
     * @param sendTo 收件人地址 
     * @param titel  邮件标题 
     * @param content<key, 内容> 邮件内容 
     * @param attachments<文件名，附件> 附件列表 
     */  
    public void sendTemplateMail(String sendTo, String titel, Map<String, Object> content, Map<String, File> attachments);

    /** 
     * 发送html样式邮件 
     * @param sendTo 收件人地址 
     * @param titel  邮件标题 
     * @param html   html格式邮件内容 
     */  
	public void sendHtmlMail(String sendTo, String titel, String html);

	/**
	 * 发送带静态资源的邮件
     * @param sendTo 收件人地址 
     * @param titel  邮件标题 
	 */
	public void sendInlineMail(String sendTo, String titel);

	/**
     * 发送模板邮件 使用 freemarker
     * @param sendTo 收件人地址 
     * @param titel  邮件标题 
     * @param content<key, 内容> 邮件内容 
     * @param attachments<文件名，附件> 附件列表 
	 */
	void sendTemplateMailFreemarker(String sendTo, String titel, Map<String, Object> content, Map<String, File> attachments);  
      
}  