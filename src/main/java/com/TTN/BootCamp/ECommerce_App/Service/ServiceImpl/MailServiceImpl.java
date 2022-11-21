package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.Entity.SecureToken;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Repository.SecureTokenRepo;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
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
//        logger.info("EmailService::sendMail execution started.");
//
//        logger.debug("EmailService::sendMail configuring email details");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("abhishekgarg919@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        javaMailSender.send(mailMessage);
//        logger.info("EmailService::sendMail execution ended.");

    }


    public void sendActivationMail(User user){
//        logger.info("EmailService::sendActivationMail execution started.");

//        logger.debug("EmailService::sendActivationMail generating token, composing email to send");
        SecureToken secureToken = new SecureToken(user);
        secureTokenRepo.save(secureToken);

        String emailBody = "Hello " + user.getFirstName() +", " + "\n\n" +
                "We're excited to have you get started. First, you need to activate your account " +
                "Just go to the link mentioned below to activate your account. The link is valid for 3 hours." +
                "\n\n"+"http://localhost:8080/api/activate_account?token="+ secureToken.getSecureToken() + "\n\n" +
                "- Team 'Dummy Ecommerce Application' ";

        sendEmail(user.getEmail(), emailBody, ACTIVATION_SUBJECT);
//        logger.info("EmailService::sendActivationMail execution ended.");

    }

    // method to trigger a mail to notify that account is activated
    public void sendIsActivatedMail(User user){
//        logger.info("EmailService::sendIsActivatedMail execution started.");

//        logger.debug("EmailService::sendIsActivatedMail composing email to send");
        String emailBody = "Hi " + user.getFirstName() +", " + "\n" +
                "Welcome to 'Dummy Ecommerce Application', your account has been activated. " +
                "\n" +
                "- Team 'Dummy Ecommerce Application' ";

        sendEmail(user.getEmail(), emailBody, ACTIVATION_SUBJECT);
//        logger.info("EmailService::sendIsActivatedMail execution ended.");
    }


    // method to trigger a mail to reset the password
    public void sendForgotPasswordMail(User user){
//        logger.info("EmailService::sendForgotPasswordMail execution started.");

//        logger.debug("EmailService::sendForgotPasswordMail generating token, composing email to send");

        SecureToken secureToken = new SecureToken(user);
        secureTokenRepo.save(secureToken);

        String emailBody = "Hi " + user.getFirstName() +", " + "\n" +
                "A request has been received to change the password for your account. " +
                "Please go the link mentioned below to change your password. The link will expire in 15min." +
                "\n" + "http://localhost:8080/api/reset_password?token="+ secureToken.getSecureToken() + "\n\n" +
                "- Team 'Dummy Ecommerce Application' ";

        sendEmail(user.getEmail(), emailBody, RESET_SUBJECT);
//        logger.info("EmailService::sendForgotPasswordMail execution ended.");
    }

    // method to trigger a mail to notify successful password change
    public void sendSuccessfulChangeMail(User user) {
//        logger.info("EmailService::sendSuccessfulChangeMail execution started.");

//        logger.debug("EmailService::sendSuccessfulChangeMail composing email to send");
        String emailBody = "Hi " + user.getFirstName() +", " + "\n" +
                "Your password has been changed successfully. Please use your new password to access your account." +
                "\n" +
                "- Team 'Dummy Ecommerce Application' ";

        sendEmail(user.getEmail(), emailBody, RESET_SUBJECT);
//        logger.info("EmailService::sendSuccessfulChangeMail execution ended.");
    }
}
