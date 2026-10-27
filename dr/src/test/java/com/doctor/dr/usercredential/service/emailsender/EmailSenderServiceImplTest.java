package com.doctor.dr.usercredential.service.emailsender;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmailSenderServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    private EmailSenderServiceImpl emailSenderService;

    @Test
    void sendSimpleEmail_shouldSendEmailOnRegistration_whenValidInformationIsGiven() {
        EmailInformation emailInformation = new EmailInformation("fsd1@gmail.com", "asddw", "asdwqdqggsdaw", true);
        this.emailSenderService.sendSimpleEmail(emailInformation);
    }

    @Test
    void sendSimpleEmail_shouldSendEmailOnUpdateScenario_whenValidInformationIsGiven() {
        EmailInformation emailInformation = new EmailInformation("fsd1@gmail.com", "asddw", "asdwqdqggsdaw", false);
        this.emailSenderService.sendSimpleEmail(emailInformation);
    }
}