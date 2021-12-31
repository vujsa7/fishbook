package com.fishbook.email.service;

import com.fishbook.email.model.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmailService {
    private final JavaMailSender emailSender;
    private final Environment env;

    @Autowired
    public EmailService(JavaMailSender emailSender, Environment env){
        this.emailSender = emailSender;
        this.env = env;
    }

    @Async
    public void sendEmail(Email email) throws MailException, InterruptedException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        message.setTo(email.getRecipient());
        message.setSubject(email.getSubject());
        message.setText(email.getContent());

        emailSender.send(message);
    }
}
