package com.reto.pandero.cinema.service.implement;

import com.reto.pandero.cinema.dto.AccountDTO;
import com.reto.pandero.cinema.dto.ApiResponse;
import com.reto.pandero.cinema.entity.Account;
import com.reto.pandero.cinema.enums.AccountType;
import com.reto.pandero.cinema.repository.AccountRepository;
import com.reto.pandero.cinema.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class AccountServiceImplement implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MicroserviceCaller microserviceCaller;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<AccountDTO> getAllAcount() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDTO> accountDTOs = accounts.stream()
                .map(account -> AccountDTO.builder()
                        .id(account.getId())
                        .username(account.getUsername())
                        .password(account.getPassword())
                        .email(account.getEmail())
                        .phoneNumber(account.getPhoneNumber())
                        .accountType(account.getAccountType())
                        .build())
                .collect(Collectors.toList());
        return accountDTOs;
    }

    @Override
    public AccountDTO getByIdAcount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return AccountDTO.builder()
                .id(account.getId())
                .username(account.getUsername())
                .password(account.getPassword())
                .email(account.getEmail())
                .phoneNumber(account.getPhoneNumber())
                .accountType(account.getAccountType())
                .build();

    }

    @Override
    public ResponseEntity<ApiResponse> createAccount(@Valid AccountDTO accountDTO) {

        if (accountRepository.existsByUsername(accountDTO.getUsername())) {
            ApiResponse errorResponse = new ApiResponse();
            errorResponse.setMessage("Username already exists");
            errorResponse.setSuccess(400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }
        if (accountRepository.existsByEmail(accountDTO.getEmail())) {
            ApiResponse errorResponse = new ApiResponse();
            errorResponse.setMessage("Email already exists");
            errorResponse.setSuccess(400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }

        Account account = Account.builder()
                .username(accountDTO.getUsername())
                .password(passwordEncoder.encode(accountDTO.getPassword()))
                .email(accountDTO.getEmail())
                .phoneNumber(accountDTO.getPhoneNumber())
                .accountType(accountDTO.getAccountType())
                .build();


        try {
            Account savedAccount = accountRepository.save(account);
            // Enviar correo electrónico de bienvenida
            microserviceCaller.sendWelcomeEmail(accountDTO);

            // Llamar al microservicio Prize (simulado con el Stub)
            if (accountDTO.getAccountType() != AccountType.BASIC) {
                microserviceCaller.sendToPrizeMicroservice(accountDTO);
            }

            // Llamar al microservicio Benefit (simulado con el Stub)
            if (accountDTO.getAccountType() == AccountType.PREMIUM) {
                microserviceCaller.sendToBenefitMicroservice(accountDTO);
            }
            ApiResponse successResponse = new ApiResponse();
            successResponse.setMessage("Account created successfully");
            successResponse.setSuccess(200);
            logger.info("Account created: {}", accountDTO.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(successResponse);
        } catch (DataIntegrityViolationException ex) {
            ApiResponse errorResponse = new ApiResponse();
            errorResponse.setMessage("Error while creating account");
            errorResponse.setSuccess(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }

    }

    @Override
    public boolean updateAccount(Long id, AccountDTO accountDTO) {
        Account existingAccount = accountRepository.findById(id).orElse(null);
        if (existingAccount == null) {
            return false;
        }

        existingAccount.setUsername(accountDTO.getUsername());
        existingAccount.setPassword(accountDTO.getPassword());
        existingAccount.setEmail(accountDTO.getEmail());
        existingAccount.setPhoneNumber(accountDTO.getPhoneNumber());
        existingAccount.setAccountType(accountDTO.getAccountType());
        accountRepository.save(existingAccount);

        return true;
    }

    @Override
    public boolean deleteAccount(Long id) {
        Account existingAccount = accountRepository.findById(id).orElse(null);
        if (existingAccount == null) {
            return false;
        }

        accountRepository.delete(existingAccount);

        return true;
    }
}
