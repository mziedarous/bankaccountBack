package com.sg.bank.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDTO {

    private String firstName;
    private String lastName;
    private String address;
    private Double amount;
    private List<OperationDTO> operation;

}
