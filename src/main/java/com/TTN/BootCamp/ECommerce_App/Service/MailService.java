package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.EmailDetails;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Service
public interface MailService {

////    public void sendActivationEmail(User user);
//
////    public void sendEmailFromTemplate(User user, String templateName, String titleKey);
//
////    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml);
//
//    @Async
//    public void sendEmail(EmailDetails emailDetails);
////    @Async
////    public void sendActivationEmail(User user)
//
//    public void sendEmailVerification(CustomerDTO customerDTO, String siteURL) throws MessagingException, UnsupportedEncodingException;
//
//    public void register(User user) throws UnsupportedEncodingException, MessagingException;

    public void sendEmail(SimpleMailMessage email);
}
