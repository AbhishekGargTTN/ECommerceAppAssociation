package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.Entity.SecureToken;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Repository.SecureTokenRepo;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Component
@Transactional
public class MailServiceImpl implements MailService {

    Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    SecureTokenRepo secureTokenRepo;

    @Autowired
    MessageSource messageSource;

    @Value("${spring.mail.username}")
    private String sender;

//    public MailServiceImpl(String sender) {
//        this.sender = sender;
//    }

    @Async
    public void sendEmail(String toEmail, String body, String subject) {
        logger.info("MailService: started execution");
        logger.debug("MailService: configuring email details");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        javaMailSender.send(mailMessage);
        logger.info("MailService: ended execution");

    }


    public void sendActivationMail(User user, Locale locale){
        logger.info("MailService: sendActivationMail started execution");
        logger.debug("MailService: sendActivationMail generating token and composing activation mail");

        SecureToken secureToken = new SecureToken(user);
        secureTokenRepo.save(secureToken);

        String emailSubject= messageSource
                .getMessage("api.email.activationMailBody",null, locale);

        String emailBody = messageSource
                .getMessage("api.email.activationSubject",null, locale);

        String link = messageSource.getMessage("api.email.siteUrl",null, locale);

        emailBody = emailBody.replace("[[name]]", user.getFirstName());
        emailBody = emailBody.replace("[[URL]]",link +"/activate_account?token="+ secureToken.getSecureToken());

        sendEmail(user.getEmail(), emailBody, emailSubject);
        logger.info("MailService: sendActivationMail ended execution");

    }

    public void sendIsActivatedMail(User user, Locale locale){
        logger.info("MailService: sendIsActivatedMail started execution");
        logger.debug("MailService: sendIsActivatedMail composing activation confirmation mail");

        String emailSubject= messageSource
                .getMessage("api.email.activationSubject",null, locale);

        String emailBody = messageSource.getMessage("api.email.isActivatedMailBody",null, locale);
        emailBody = emailBody.replace("[[name]]", user.getFirstName());

        sendEmail(user.getEmail(), emailBody, emailSubject);
        logger.info("MailService: sendIsActivatedMail ended execution");
    }

    public void sendForgotPasswordMail(User user, Locale locale){
        logger.info("MailService: sendForgotPasswordMail started execution");
        logger.debug("MailService: sendForgotPasswordMail generating token and composing password reset mail");

        SecureToken secureToken = new SecureToken(user);
        secureTokenRepo.save(secureToken);

        String emailSubject= messageSource
                .getMessage("api.email.resetSubject",null, locale);

        String emailBody = messageSource
                .getMessage("api.email.forgotPasswordMailBody",null, locale);

        String link = messageSource.getMessage("api.email.siteUrl",null, Locale.ENGLISH);

        emailBody = emailSubject.replace("[[name]]", user.getFirstName());
        emailBody = emailSubject.replace("[[URL]]",link +"/reset_password?token="+ secureToken.getSecureToken());

        sendEmail(user.getEmail(), emailBody, emailSubject);
        logger.info("MailService: sendForgotPasswordMail ended execution");
    }

    public void sendSuccessfulChangeMail(User user, Locale locale) {
        logger.info("MailService: sendSuccessfulChangeMail started execution");
        logger.debug("MailService: sendSuccessfulChangeMail composing password reset confirmation mail");

        String emailSubject= messageSource
                .getMessage("api.email.resetSubject",null, locale);

        String emailBody = messageSource.getMessage("api.email.successfulChangeMailBody",null, locale);
        emailBody = emailBody.replace("[[name]]", user.getFirstName());

        sendEmail(user.getEmail(), emailBody, emailSubject);
        logger.info("MailService: sendSuccessfulChangeMail ended execution");
    }

    public void sendDeActivatedMail(User user, Locale locale) {
        logger.info("MailService: sendDeActivatedMail started execution");

        logger.debug("MailService: sendDeActivatedMail composing user " +
                "account deactivation confirmation mail");

        String emailSubject= messageSource
                .getMessage("api.email.deactivationSubject",null, locale);

        String emailBody = messageSource.getMessage("api.email.accountDeactivatedMailBody",null, locale);
        emailBody = emailBody.replace("[[name]]", user.getFirstName());

        sendEmail(user.getEmail(), emailBody, emailSubject);
        logger.info("MailService: sendDeActivatedMail ended execution");
    }
}
