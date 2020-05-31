package com.matheuscordeiro.libaryapi.service.impl;

import com.matheuscordeiro.libaryapi.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Value("${application.mail.default-remetent}")
    private String remetent;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(String message, List<String> mailsList) {
        String[] mails = mailsList.toArray(new String[mailsList.size()]);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(remetent);
        mailMessage.setSubject("Book with late loan");
        mailMessage.setText(message);
        mailMessage.setTo(mails);
        javaMailSender.send(mailMessage);
    }
}
