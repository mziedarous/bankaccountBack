package com.sg.bank.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "First name cannot be empty")
    @Pattern(regexp = "[A-Za-z-']*", message = "First name contains illegal characters")
    private String firstName;
    private String compteReference;
    @NotBlank(message = "Last name cannot be empty")
    @Pattern(regexp = "[A-Za-z-']*", message = "Last name contains illegal characters")
    private String lastName;
    private String address;
    @NotBlank(message = "Amount superior to zero")
    @Pattern(regexp = "^[1-9][0-9]*$", message = "Amount superior to zero")
    private Double amount;
    @OneToMany(targetEntity = Operation.class,cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "useraccount",referencedColumnName = "id")
    private List<Operation> operations;


}
