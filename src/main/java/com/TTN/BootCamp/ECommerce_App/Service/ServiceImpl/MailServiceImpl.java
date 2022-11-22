package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.Controller.AuthenticationController;
import com.TTN.BootCamp.ECommerce_App.Entity.SecureToken;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Repository.SecureTokenRepo;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MailServiceImpl implements MailService {

    Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    SecureTokenRepo secureTokenRepo;

    @Value("${spring.mail.username}")
    private String sender;

    private static final String ACTIVATION_SUBJECT = "Account Activation | Dummy Ecommerce Application";
    private static final String RESET_SUBJECT = "Password Reset Request | Dummy Ecommerce Application";


    @Async
    public void sendEmail(String toEmail, String body, String subject) {
        logger.info("MailService: started execution");
        logger.debug("MailService: configuring email details");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("abhishekgarg919@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        javaMailSender.send(mailMessage);
        logger.info("MailService: ended execution");

    }


    public void sendActivationMail(User user){
        logger.info("MailService: sendActivationMail started execution");
        logger.debug("MailService: sendActivationMail generating token and composing activation mail");

        SecureToken secureToken = new SecureToken(user);
        secureTokenRepo.save(secureToken);

        String emailBody = "Hello " + user.getFirstName() +", " + "\n\n" +
                "We're excited to have you get started. First, you need to activate your account " +
                "Just go to the link mentioned below to activate your account. The link is valid for 3 hours." +
                "\n\n"+"http://localhost:8080/api/activate_account?token="+ secureToken.getSecureToken() + "\n\n" +
                "- Team 'Dummy Ecommerce Application' ";

        sendEmail(user.getEmail(), emailBody, ACTIVATION_SUBJECT);
        logger.info("MailService: sendActivationMail ended execution");

    }

    public void sendIsActivatedMail(User user){
        logger.info("MailService: sendIsActivatedMail started execution");
        logger.debug("MailService: sendIsActivatedMail composing activation confirmation mail");

        String emailBody = "Hi " + user.getFirstName() +", " + "\n" +
                "Welcome to 'Dummy Ecommerce Application', your account has been activated. " +
                "\n" +
                "- Team 'Dummy Ecommerce Application' ";

        sendEmail(user.getEmail(), emailBody, ACTIVATION_SUBJECT);
        logger.info("MailService: sendIsActivatedMail ended execution");
    }


    // method to trigger a mail to reset the password
    public void sendForgotPasswordMail(User user){
        logger.info("MailService: sendForgotPasswordMail started execution");
        logger.debug("MailService: sendForgotPasswordMail generating token and composing password reset mail");

        SecureToken secureToken = new SecureToken(user);
        secureTokenRepo.save(secureToken);

        String emailBody = "Hi " + user.getFirstName() +", " + "\n" +
                "A request has been received to change the password for your account. " +
                "Please go the link mentioned below to change your password. The link will expire in 15min." +
                "\n" + "http://localhost:8080/api/reset_password?token="+ secureToken.getSecureToken() + "\n\n" +
                "- Team 'Dummy Ecommerce Application' ";

        sendEmail(user.getEmail(), emailBody, RESET_SUBJECT);
        logger.info("MailService: sendForgotPasswordMail ended execution");
    }

    public void sendSuccessfulChangeMail(User user) {
        logger.info("MailService: sendSuccessfulChangeMail started execution");
        logger.debug("MailService: sendSuccessfulChangeMail composing password reset confirmation mail");

        String emailBody = "Hi " + user.getFirstName() +", " + "\n" +
                "Your password has been changed successfully. " +
                "Please use your new password to access your account." +
                "\n" +
                "- Team 'Dummy Ecommerce Application' ";

        sendEmail(user.getEmail(), emailBody, RESET_SUBJECT);
        logger.info("MailService: sendSuccessfulChangeMail ended execution");
    }
}
