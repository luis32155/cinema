package com.reto.pandero.cinema.service.stubs;

import com.reto.pandero.cinema.dto.AccountDTO;
import com.reto.pandero.cinema.utils.Constanst;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BenefitMicroserviceStub {

    public ResponseEntity<String> sendBenefit(AccountDTO accountDTO) {
        // Simulate success response
        String responseBody = Constanst.PREMIUMRESPONSE;
        return ResponseEntity.ok(responseBody);
    }
}
