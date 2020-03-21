package com.hronosf.service.impl;

import com.hronosf.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Setter
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private String from;
    private String subject;

    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String to, String htmlTemplate) throws MessagingException {
        MimeMessage email = mailSender.createMimeMessage();
        email.setSubject(subject);
        MimeMessageHelper helper = new MimeMessageHelper(email, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setText(htmlTemplate, true);
        mailSender.send(email);
    }
}
