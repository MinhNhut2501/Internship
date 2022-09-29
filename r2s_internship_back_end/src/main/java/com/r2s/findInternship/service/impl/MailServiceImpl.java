package com.r2s.findInternship.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.r2s.findInternship.common.MailResponse;
import com.r2s.findInternship.exception.InternalServerErrorException;
import com.r2s.findInternship.service.MailService;
import com.r2s.findInternship.service.UserService;

@Component
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserService userService;
    @Autowired
    private ServletContext application;
    private List<MimeMessage> queue = new ArrayList<MimeMessage>();

    @Override
    public void send(MailResponse response) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(response.getTo());
            switch (response.getTypeMail()) {
                case ApplyJob:// SEND MAIL HR
//				response.createTemplate();//Paramater: Name candidate, Name job
                    response.setSubject("Thông Báo Ứng Viên Ứng Tuyển");
                    if (response.getCv() != null) {
                        File file = new File(application.getRealPath("/") + response.getCv());
                        helper.addAttachment("File CV", file);
                    }
                    response.createTemplate();
                    break;
                case ForgotPassword:
                    response.setSubject("CẤP LẠI MẬT KHẨU");
                    String newPass = userService.resetPassword(response.getTo());//Change Password
                    response.createMailForgotPassword(newPass);
                    break;
                case HRApply:
                    if (response.getCv() != null) {
                        File file = new File(application.getRealPath("/") + response.getCv());
                        helper.addAttachment("File", file);
                    }
                    response.createTemplateHRApply();
                    break;
                case ActiveUniversity:
                    //todo send mail active university
                    response.setSubject("KÍCH HOẠT TÀI KHOẢN THÀNH CÔNG");
                    response.createTemplateActive();
                    break;
                case ActiveCompany:
                    //todo send mail active Company
                    response.setSubject("KÍCH HOẠT TÀI KHOẢN THÀNH CÔNG");
                    break;
                default:
                    throw new InternalServerErrorException("Type mail is incorect!");
            }
            helper.setSubject(response.getSubject());
            helper.setText(response.getMailTemplate(), true);
            File logo = new File(application.getRealPath("/") + "images/logoR2S.png");
            helper.addInline("R2S", logo);
            queue.add(message);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }

    }

    @Scheduled(fixedDelay = 10000)//DELAY 10s
    public void run() {
        boolean flag = false;
        while (!queue.isEmpty()) {
            MimeMessage message = queue.remove(0);
            try {
                javaMailSender.send(message);
                flag = true;
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        if (flag) System.out.println("Send mail successfull");
    }


}
