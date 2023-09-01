package com.reto.pandero.cinema.service.stubs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailServiceStub {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceStub.class);
    private List<SimpleMailMessage> sentEmails = new ArrayList<>();

    public void sendEmail(SimpleMailMessage message) {
        sentEmails.add(message);
        logger.info("Email sent: " + message.getSubject());
    }

    public List<SimpleMailMessage> getSentEmails() {
        return sentEmails;
    }
}
