package com.ylt.servicefunction.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class SendEmail {

    private Logger logger = LoggerFactory.getLogger(getClass());//提供日志类


    private JavaMailSenderImpl javaMailSenderImpl;
    @Autowired
    public void setJavaMailSenderImpl(JavaMailSenderImpl javaMailSenderImpl) {
        this.javaMailSenderImpl = javaMailSenderImpl;
    }

    public void sendSimple() {
//		创建一个简单邮件对象
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//		设置邮件基本信息
        simpleMailMessage.setSubject("邮件标题1");
        simpleMailMessage.setText("邮件文本1");
        simpleMailMessage.setTo("202526784@qq.com");
        simpleMailMessage.setFrom("1989414476@qq.com");
        javaMailSenderImpl.send(simpleMailMessage);
    }

    @Async
    public void sendComplex(String email,String emailHeader,String emailBody,boolean htmlStatus) throws MessagingException {
//		创建一个复杂邮件对象
        MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
        MimeMessageHelper MimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
//		设置邮件基本信息
//        MimeMessageHelper.setSubject("邮件标题1");
//        MimeMessageHelper.setText("<b style='color:red'>邮件文本1</b>",true);
//        MimeMessageHelper.setTo("202526784@qq.com");
        MimeMessageHelper.setSubject(emailHeader);
        MimeMessageHelper.setText(emailBody,htmlStatus);
        MimeMessageHelper.setTo(email);
        MimeMessageHelper.setFrom("1989414476@qq.com");
//		设置邮件附件（上传文件）
//        MimeMessageHelper.addAttachment("5.jpg",new File("D:\\5.jpeg"));
        javaMailSenderImpl.send(mimeMessage);
    }
}
