package com.reto.pandero.cinema.service.implement;

import com.reto.pandero.cinema.dto.AccountDTO;
import com.reto.pandero.cinema.service.stubs.EmailServiceStub;
import com.reto.pandero.cinema.utils.Constanst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private EmailServiceStub emailServiceStub;

    public void sendWelcomeEmail(AccountDTO accountDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(accountDTO.getEmail());
        message.setSubject(Constanst.MENSAJESUBJET);
        message.setText(Constanst.MENSAJE);

        emailServiceStub.sendEmail(message);


    }

}
