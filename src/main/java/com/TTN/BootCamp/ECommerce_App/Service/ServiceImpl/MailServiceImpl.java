package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.DTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.EmailDetails;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
@Transactional
public class MailServiceImpl implements MailService {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Value("${spring.mail.username}")
//    private String sender;
//    @Value("${spring.mail.username}")
//    private String fromEmail;
////    @Async
////    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
////
////        // Prepare message using a Spring helper
////        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
////        try {
////            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
////            message.setTo(to);
//////            message.setFrom(jHipsterProperties.getMail().getFrom());
////            message.setSubject(subject);
////            message.setText(content, isHtml);
////            javaMailSender.send(mimeMessage);
////            log.debug("Sent email to User '{}'", to);
////        }  catch (MailException | MessagingException e) {
////            log.warn("Email could not be sent to user '{}'", to, e);
////        }
////    }
////    @Async
////    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
////
//////        context.setVariable(USER, user);
//////        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
////        String content = templateEngine.process(templateName, context);
////        String subject = messageSource.getMessage(titleKey, null, locale);
////        sendEmail(user.getEmail(), subject, content, false, true);
////    }
////
////    @Async
////    public void sendActivationEmail(User user) {
////        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
////    }
//
//
//    @Override
//    public void sendEmail(EmailDetails emailDetails) {
//
//        SimpleMailMessage mailMessage
//                = new SimpleMailMessage();
//
//        // Setting up necessary details
//        mailMessage.setFrom(sender);
//        mailMessage.setTo(emailDetails.getRecipient());
//        mailMessage.setText(emailDetails.getMsgBody());
//        mailMessage.setSubject(emailDetails.getSubject());
//
//        // Sending the mail
//        javaMailSender.send(mailMessage);
//    }
//
//
////    public void sendActivationEmail(User user) {
//////        log.debug("Sending activation email to '{}'", user.getEmail());
//////        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
////
////    }
//
//    public void sendEmailVerification(CustomerDTO customerDTO, String siteURL) throws MessagingException, UnsupportedEncodingException {
//        String toAddress = customerDTO.getEmail();
//        String fromAddress = fromEmail;
//        String senderName = "Ecommerce Application";
//        String subject = "Please verify your registration";
//        String content = "Dear [[name]], <br>"
//                +"Please click the link below to verify your registration:<br>"
//                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
//                + "Thank you,<br>"
//                + "Ecommerce Application.";
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        helper.setFrom(fromAddress, senderName);
//        helper.setTo(toAddress);
//        helper.setSubject(subject);
//
//        content = content.replace("[[name]]", customerDTO.getFirstName());
//        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
//
//
//        content = content.replace("[[URL]]", verifyURL);
//
//        helper.setText(content, true);
//
//        javaMailSender.send(message);
//    }
//
//    public void register(User user)throws UnsupportedEncodingException, MessagingException {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
//
//        String randomCode = RandomString.make(64);
//        user.setVerificationCode(randomCode);
//        user.setCreated_at(getCurrentTimeUsingCalendar());
//        user.setActive(false);
//
//        //  userRepo.save(user);
//
//    }

    private JavaMailSender javaMailSender;




    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
}
