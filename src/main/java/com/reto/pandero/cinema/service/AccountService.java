package com.reto.pandero.cinema.service;

import com.reto.pandero.cinema.dto.AccountDTO;

import java.util.List;


public interface AccountService {

    List<AccountDTO> getAllAcount();

    AccountDTO getByIdAcount(Long id);

    void createAccount(AccountDTO accountDTO);

    boolean updateAccount(Long id, AccountDTO accountDTO);

    boolean deleteAccount(Long id);


}
