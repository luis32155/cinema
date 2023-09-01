package com.reto.pandero.cinema.controller;

import com.reto.pandero.cinema.dto.AccountDTO;
import com.reto.pandero.cinema.dto.ApiResponse;
import com.reto.pandero.cinema.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accountDTOs = accountService.getAllAcount();
        return ResponseEntity.ok(accountDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        AccountDTO accountDTO = accountService.getByIdAcount(id);
        if (accountDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createAccount(@Valid @RequestBody  AccountDTO accountDTO) {

        return   accountService.createAccount(accountDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountDTO accountDTO) {
        boolean updated = accountService.updateAccount(id, accountDTO);
        if (updated) {
            return ResponseEntity.ok("Account updated successfully ");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        boolean deleted = accountService.deleteAccount(id);
        if (deleted) {
            return ResponseEntity.ok("Account deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}