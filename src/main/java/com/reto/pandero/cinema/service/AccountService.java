package com.reto.pandero.cinema.service;

import com.reto.pandero.cinema.dto.AccountDTO;
import com.reto.pandero.cinema.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface AccountService {

   List<AccountDTO> getAllAcount();
   AccountDTO getByIdAcount(Long id);
   void  createAccount(AccountDTO accountDTO);
   boolean  updateAccount(Long id, AccountDTO accountDTO);

   boolean  deleteAccount(Long id);


}
