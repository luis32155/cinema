package com.reto.pandero.cinema.service.implement;

import com.reto.pandero.cinema.dto.AccountDTO;
import com.reto.pandero.cinema.entity.Account;
import com.reto.pandero.cinema.enums.AccountType;
import com.reto.pandero.cinema.repository.AccountRepository;
import com.reto.pandero.cinema.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
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
        return accountDTOs ;
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
    public void  createAccount(AccountDTO accountDTO) {
        Account account = Account.builder()
                .username(accountDTO.getUsername())
                .password(passwordEncoder.encode(accountDTO.getPassword()))
                .email(accountDTO.getEmail())
                .phoneNumber(accountDTO.getPhoneNumber())
                .accountType(accountDTO.getAccountType())
                .build();


        accountRepository.save(account);

        // Enviar correo electrónico de bienvenida
        microserviceCaller.sendWelcomeEmail(accountDTO);


        // Llamar al microservicio Prize (simulado con el Stub)
        if(accountDTO.getAccountType() != AccountType.BASIC){
            microserviceCaller.sendToPrizeMicroservice(accountDTO);
        }



        // Llamar al microservicio Benefit (simulado con el Stub)
        if (accountDTO.getAccountType() == AccountType.PREMIUM) {
            microserviceCaller.sendToBenefitMicroservice(accountDTO);
        }
        logger.info("Account created: {}", accountDTO.getUsername());

    }

    @Override
    public boolean  updateAccount(Long id, AccountDTO accountDTO) {
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
    public boolean  deleteAccount(Long id) {
        Account existingAccount = accountRepository.findById(id).orElse(null);
        if (existingAccount == null) {
            return false;
        }

        accountRepository.delete(existingAccount);

        return true;
    }
}
