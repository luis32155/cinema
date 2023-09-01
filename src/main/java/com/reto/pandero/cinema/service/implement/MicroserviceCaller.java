package com.reto.pandero.cinema.service.implement;

import com.reto.pandero.cinema.dto.AccountDTO;
import com.reto.pandero.cinema.service.stubs.BenefitMicroserviceStub;
import com.reto.pandero.cinema.service.stubs.PrizeMicroserviceStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MicroserviceCaller {
    private static final Logger logger = LoggerFactory.getLogger(MicroserviceCaller.class);
    @Autowired
    private PrizeMicroserviceStub prizeMicroserviceStub;

    @Autowired
    private BenefitMicroserviceStub benefitMicroserviceStub;

    @Autowired
    private EmailService emailServiceStub;

    public void sendToPrizeMicroservice(AccountDTO accountDTO) {
        ResponseEntity<String> response = prizeMicroserviceStub.sendPrize(accountDTO);
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            logger.info("Stub - Prize Microservice Response: " + responseBody);
        } else {
            logger.info("Stub - Prize Microservice Request failed with status: " + response.getStatusCodeValue());
        }
    }

    public void sendToBenefitMicroservice(AccountDTO accountDTO) {
        ResponseEntity<String> response = benefitMicroserviceStub.sendBenefit(accountDTO);
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            logger.info("Stub - Benefit Microservice Response: " + responseBody);
        } else {
            logger.info("Stub - Benefit Microservice Request failed with status: " + response.getStatusCodeValue());
        }
    }

    public void sendWelcomeEmail(AccountDTO accountDTO) {
        emailServiceStub.sendWelcomeEmail(accountDTO);
        logger.info("Sent welcome email to: {}", accountDTO.getEmail());
    }
}
