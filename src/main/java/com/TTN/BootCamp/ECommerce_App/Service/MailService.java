package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.ProductResponseDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public interface MailService {

    public void sendEmail(String toEmail, String body, String subject);

    public void sendActivationMail(User user, Locale locale);

    public void sendIsActivatedMail(User user, Locale locale);

    public void sendForgotPasswordMail(User user, Locale locale);

    public void sendSuccessfulChangeMail(User user, Locale locale);

    public void sendDeActivatedMail(User user, Locale locale);

    public void sendProductActivationMail(ProductResponseDTO productResponseDTO,User productOwner);

    public void sendProductDeactivationMail(ProductResponseDTO productResponseDTO,User productOwner);
}
