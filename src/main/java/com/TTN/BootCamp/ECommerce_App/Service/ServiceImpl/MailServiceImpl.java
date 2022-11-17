package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.Entity.EmailDetails;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;
//    @Async
//    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
//
//        // Prepare message using a Spring helper
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        try {
//            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
//            message.setTo(to);
////            message.setFrom(jHipsterProperties.getMail().getFrom());
//            message.setSubject(subject);
//            message.setText(content, isHtml);
//            javaMailSender.send(mimeMessage);
//            log.debug("Sent email to User '{}'", to);
//        }  catch (MailException | MessagingException e) {
//            log.warn("Email could not be sent to user '{}'", to, e);
//        }
//    }
//    @Async
//    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
//
////        context.setVariable(USER, user);
////        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
//        String content = templateEngine.process(templateName, context);
//        String subject = messageSource.getMessage(titleKey, null, locale);
//        sendEmail(user.getEmail(), subject, content, false, true);
//    }
//
//    @Async
//    public void sendActivationEmail(User user) {
//        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
//    }


    @Override
    public void sendEmail(EmailDetails emailDetails) {

        SimpleMailMessage mailMessage
                = new SimpleMailMessage();

        // Setting up necessary details
        mailMessage.setFrom(sender);
        mailMessage.setTo(emailDetails.getRecipient());
        mailMessage.setText(emailDetails.getMsgBody());
        mailMessage.setSubject(emailDetails.getSubject());

        // Sending the mail
        javaMailSender.send(mailMessage);
    }
}
