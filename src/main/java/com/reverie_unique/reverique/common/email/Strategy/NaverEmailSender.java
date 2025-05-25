package com.reverie_unique.reverique.common.email.Strategy;

import com.reverie_unique.reverique.config.MailConfig;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Component;
import java.util.Properties;

@Component("naverEmailSender")
public class NaverEmailSender implements EmailSenderStrategy {

    private final MailConfig mailConfig;

    public NaverEmailSender(MailConfig mailConfig) {
        this.mailConfig = mailConfig;
    }

    @Override
    public void send(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", mailConfig.getHost());
        props.put("mail.smtp.port", mailConfig.getPort());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailConfig.getUsername(), mailConfig.getPassword());
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailConfig.getUsername()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent to " + to);
        } catch (MessagingException e) {
            e.printStackTrace();  // 전체 예외 스택 트레이스 출력
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }
}