package com.broded.jwtrtsecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService{

    public JavaMailSender emailSender;

    public void sendAlert(String ip) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("smth@gmail.com");
        simpleMailMessage.setSubject("Совершен вход с нового IP");
        simpleMailMessage.setText("Совершен вход с IP %s".formatted(ip));
        emailSender.send(simpleMailMessage);
    }
}
