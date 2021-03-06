package ch.com.mazad.service;

import ch.com.mazad.utils.MessageFactory;
import ch.com.mazad.web.dto.UserDTO;
import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * Created by Chemakh on 13/07/2017.
 */

@Service
public class MailService {

    private final static Logger log = LoggerFactory.getLogger(MailService.class);


    @Inject
    private JavaMailSenderImpl javaMailSender;

    @Inject
    private SpringTemplateEngine templateEngine;

    @Value("${mazad.mail.from}")
    private String fromEmail;


    @Async
    public void sendEmail(String toEmail, String subject, String content, boolean isMultipart, boolean isHtml) {
        if (toEmail != null) {
            log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                    isMultipart, isHtml, toEmail, subject, content);

            log.info("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}'",
                    isMultipart, isHtml, toEmail, subject);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper message;

            try {
                message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
                message.setTo(toEmail.trim());
                message.setFrom(fromEmail);
                message.setSubject(subject);
                message.setText(content, isHtml);

                message.addInline("lock.png", new ClassPathResource("static/images/lock.png"), "image/png");
                message.addInline("telephone.png", new ClassPathResource("static/images/telephone.png"), "image/png");
                
                
                javaMailSender.send(mimeMessage);
                log.debug("Sent e-mail to User '{}'", toEmail);
            } catch (MessagingException e) {
                log.warn("E-mail could not be sent to user '{}', exception is: {}", toEmail, e);
            }
        } else
            log.warn("Email Receiver is Null");
    }

    @Async
    public void sendActivationEmail(UserDTO user,String code) {
        log.info("Sending activation e-mail to '{}' with template activationEmail", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey() != null && !user.getLangKey().isEmpty() ? user.getLangKey() : "fr");
        Context context = new Context(locale);

        context.setVariable("user", user);
        context.setVariable("code", code);
        context.setVariable("greeting", MessageFactory.getMessage("email.activation.greeting", new String[]{user.getFirstname() + " " + user.getLastname()}, user.getLangKey()));
        context.setVariable("text1", MessageFactory.getMessage("email.activation.text", null, user.getLangKey()));


        String content = templateEngine.process("mail", context);
        String subject = MessageFactory.getMessage("email.activation.subject", null, user.getLangKey());
        sendEmail(user.getEmail(), subject, content, true, true);
    }

    @Async
    public void sendForgetPasswordEmail(UserDTO user, String code) {
        log.info("Sending Forget Password e-mail to '{}' with template Forget Email", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey() != null && !user.getLangKey().isEmpty() ? user.getLangKey() : "fr");
        Context context = new Context(locale);

        context.setVariable("user", user);
        context.setVariable("code", code);
        context.setVariable("greeting", MessageFactory.getMessage("email.forget.greeting", new String[]{user.getFirstname() + " " + user.getLastname()}, user.getLangKey()));
        context.setVariable("text1", MessageFactory.getMessage("email.forget.text1", null, user.getLangKey()));
        context.setVariable("text2", MessageFactory.getMessage("email.forget.text2", null, user.getLangKey()));


        String content = templateEngine.process("mail", context);
        String subject = MessageFactory.getMessage("email.activation.subject", null, user.getLangKey());
        sendEmail(user.getEmail(), subject, content, true, true);
    }
}
