package com.ylt.servicefunction.bean;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class EmailBean {
    private String id;//邮件id
    private String from;//邮件发送人
    private String to;//邮件接收人（多个邮箱则用逗号","隔开）
    private String subject;//邮件主题
    private String text;//邮件内容
    private LocalDate sentDate;//发送时间
    private String cc;//抄送（多个邮箱则用逗号","隔开）
    private String bcc;//密送（多个邮箱则用逗号","隔开）
    private String status;//状态
    private String error;//报错信息
    private MultipartFile[] multipartFiles;//邮件附件
}
