package com.reto.pandero.cinema.dto;

import com.reto.pandero.cinema.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {


    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private AccountType accountType;
}
