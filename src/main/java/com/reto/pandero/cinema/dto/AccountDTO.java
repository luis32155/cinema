package com.reto.pandero.cinema.dto;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email format")
    @Email(message = "Invalid email")
    private String email;
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number format")
    private String phoneNumber;
    private AccountType accountType;
}
