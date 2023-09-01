package com.reto.pandero.cinema.service.stubs;

import com.reto.pandero.cinema.dto.AccountDTO;
import com.reto.pandero.cinema.utils.Constanst;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class PrizeMicroserviceStub {
    public ResponseEntity<String> sendPrize(AccountDTO accountDTO) {
        // Simulate success response
        String responseBody = Constanst.PRIZERESPONSE;
        return ResponseEntity.ok(responseBody);
    }
}
