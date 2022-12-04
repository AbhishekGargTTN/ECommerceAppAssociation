package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.ProductResponseDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Product;
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
    private JavaMailSender javaMailSender;
    private SecureTokenRepo secureTokenRepo;
    private MessageSource messageSource;
    public MailServiceImpl(JavaMailSender javaMailSender, SecureTokenRepo secureTokenRepo, MessageSource messageSource) {
        this.javaMailSender = javaMailSender;
        this.secureTokenRepo = secureTokenRepo;
        this.messageSource = messageSource;
    }
    @Value("${spring.mail.username}")
    private String sender;

    @Async
    public void sendEmail(String toEmail, String body, String subject) {
        logger.info("MailService: started execution");
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
        SecureToken secureToken = new SecureToken(user);
        secureTokenRepo.save(secureToken);

        String emailSubject= messageSource
                .getMessage("api.email.activationSubject",null, locale);
        String link = messageSource
                .getMessage("api.email.siteUrlActivate"
                        ,new String[]{secureToken.getSecureToken()}, locale);

        String emailBody = messageSource
                .getMessage("api.email.activationMailBody"
                        ,new String[]{user.getFirstName(),link}, locale);

        sendEmail(user.getEmail(), emailBody, emailSubject);
        logger.info("MailService: sendActivationMail ended execution");

    }

    public void sendIsActivatedMail(User user, Locale locale){
        logger.info("MailService: sendIsActivatedMail started execution");
        String emailSubject= messageSource
                .getMessage("api.email.activationSubject",null, locale);

        String emailBody = messageSource
                .getMessage("api.email.isActivatedMailBody"
                        ,new String[]{user.getFirstName()}, locale);

        sendEmail(user.getEmail(), emailBody, emailSubject);
        logger.info("MailService: sendIsActivatedMail ended execution");
    }

    public void sendForgotPasswordMail(User user, Locale locale){
        logger.info("MailService: sendForgotPasswordMail started execution");

        SecureToken secureToken = new SecureToken(user);
        secureTokenRepo.save(secureToken);

        String emailSubject= messageSource
                .getMessage("api.email.resetSubject",null, locale);

        String link = messageSource.getMessage("api.email.siteUrlForgot"
                ,new String[]{secureToken.getSecureToken()}, locale);
        String emailBody = messageSource
                .getMessage("api.email.forgotPasswordMailBody"
                        ,new String[]{user.getFirstName(),link}, locale);

        sendEmail(user.getEmail(), emailBody, emailSubject);
        logger.info("MailService: sendForgotPasswordMail ended execution");
    }

    public void sendSuccessfulChangeMail(User user, Locale locale) {
        logger.info("MailService: sendSuccessfulChangeMail started execution");

        String emailSubject= messageSource
                .getMessage("api.email.resetSubject",null, locale);

        String emailBody = messageSource
                .getMessage("api.email.successfulChangeMailBody"
                        ,new String[]{user.getFirstName()}, locale);

        sendEmail(user.getEmail(), emailBody, emailSubject);
        logger.info("MailService: sendSuccessfulChangeMail ended execution");
    }

    public void sendDeActivatedMail(User user, Locale locale) {
        logger.info("MailService: sendDeActivatedMail started execution");

        String emailSubject= messageSource
                .getMessage("api.email.deactivationSubject",null, locale);

        String emailBody = messageSource
                .getMessage("api.email.accountDeactivatedMailBody"
                        ,new String[]{user.getFirstName()}, locale);

        sendEmail(user.getEmail(), emailBody, emailSubject);
        logger.info("MailService: sendDeActivatedMail ended execution");
    }

    public void sendProductActivationMail(ProductResponseDTO productResponseDTO, User productOwner){
        logger.info("EmailService::sendProductActivationMail execution started.");

        String subject = messageSource
                .getMessage("api.email.productSubject",null,Locale.ENGLISH);

        String body = messageSource
                .getMessage("api.email.productActivationMail"
                        ,new String[]{productOwner.getFirstName(),productResponseDTO.toString()}, Locale.ENGLISH);

        sendEmail(productOwner.getEmail(), body, subject);
        logger.info("EmailService::sendProductActivationMail execution ended.");
    }

    public void sendProductDeactivationMail(ProductResponseDTO productResponseDTO,User productOwner){
        logger.info("EmailService::sendProductActivationMail execution started.");

        String subject = messageSource.getMessage("api.email.productSubject",null,Locale.ENGLISH);

        String body = messageSource
                .getMessage("api.email.productDeactivationMail"
                        ,new String[]{productOwner.getFirstName(),productResponseDTO.toString()}, Locale.ENGLISH);

        sendEmail(productOwner.getEmail(), body, subject);
        logger.info("EmailService::sendProductActivationMail execution ended.");
    }

    public void sendAccountLockedMail(User user){
        logger.info("EmailService::sendAccountLockedMail execution started.");

        logger.debug("EmailService::sendAccountLockedMail composing email to send");

        String subject = messageSource.getMessage("api.email.accountLockedSubject",null,Locale.ENGLISH);
        String body = messageSource
                .getMessage("api.email.accountLockedMailBody"
                        ,new String[]{user.getFirstName()}, Locale.ENGLISH);

        sendEmail(user.getEmail(), body, subject);
        logger.info("EmailService::sendAccountLockedMail execution ended.");

    }

    public void sendNewProductMail(Product product) {
        logger.info("EmailService::sendNewProductMail execution started.");

        String subject = messageSource.getMessage("api.email.productSubject",null,Locale.ENGLISH);
        String body = messageSource
                .getMessage("api.email.newProductAddedMailBody"
                        ,new String[]{"Admin", product.toString()}, Locale.ENGLISH);

        sendEmail("garga5492216@gmail.com", body, subject);
        logger.info("EmailService::sendSuccessfulChangeMail execution ended.");
    }

    public void sendAwaitingApprovalMail(User user){
        logger.info("EmailService::sendAwaitingApprovalMail execution started.");

        String subject = messageSource.getMessage("api.email.activationSubject",null,Locale.ENGLISH);
        String body = messageSource
                .getMessage("api.email.awaitingApprovalMailBody"
                        ,new String[]{user.getFirstName()}, Locale.ENGLISH);

        sendEmail(user.getEmail(), body, subject);
        logger.info("EmailService::sendAwaitingApprovalMail execution ended.");

    }

    public void sendUnlockedMail(User user, Locale locale){
        logger.info("MailService: sendUnlockedMail started execution");
        String emailSubject= messageSource
                .getMessage("api.email.unlockSubject",null, locale);

        String emailBody = messageSource
                .getMessage("api.email.unlockedMailBody"
                        ,new String[]{user.getFirstName()}, locale);

        sendEmail(user.getEmail(), emailBody, emailSubject);
        logger.info("MailService: sendUnlockedMail ended execution");
    }
}