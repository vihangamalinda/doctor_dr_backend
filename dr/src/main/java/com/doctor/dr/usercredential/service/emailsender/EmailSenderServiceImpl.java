package com.doctor.dr.usercredential.service.emailsender;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender javaEmailSender;

    public EmailSenderServiceImpl(JavaMailSender javaEmailSender) {
        this.javaEmailSender = javaEmailSender;
    }

    public void sendSimpleEmail(EmailInformation emailInformation) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("doctordrapplication@gmail.com");
        mailMessage.setTo(emailInformation.getToEmail());
        mailMessage.setText(emailInformation.getEmailBody());
        mailMessage.setSubject(emailInformation.getSubject());

        javaEmailSender.send(mailMessage);
        System.out.println("email sent successfully");

    }
}
