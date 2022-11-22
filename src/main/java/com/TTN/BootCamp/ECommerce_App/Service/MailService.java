package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.Entity.User;
import org.springframework.stereotype.Service;

@Service
public interface MailService {

    public void sendEmail(String toEmail, String body, String subject);

    public void sendActivationMail(User user);

    public void sendIsActivatedMail(User user);

    public void sendForgotPasswordMail(User user);

    public void sendSuccessfulChangeMail(User user);
}
