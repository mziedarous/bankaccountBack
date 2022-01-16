package com.sg.bank.account.configuration;

import com.sg.bank.account.entity.Operation;
import com.sg.bank.account.entity.UserAccount;
import com.sg.bank.account.repository.UserAccountRepository;
import com.sg.bank.account.util.OperationActEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class DefaultConfiguration implements CommandLineRunner {

    private static final String DEFAULT_USER_ADDRESS = "6 Rue Lecourbe 75015 PARIS";
    private static final String DEFAULT_USER_FIRSTNAME = "Pascal";
    private static final String DEFAULT_USER_LASTNAME = "Simon";
    private static final String DEFAULT_ACCOUNT_REFERENCE = "536636975";
    private static final Double DEFAULT_AMOUNT = 5000.0;

    @Autowired
    private UserAccountRepository userAccountRepository;

    //Create a default USER
    @Override
    public void run(String... args) throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setAddress(DEFAULT_USER_ADDRESS);
        userAccount.setFirstName(DEFAULT_USER_FIRSTNAME);
        userAccount.setLastName(DEFAULT_USER_LASTNAME);
        userAccount.setCompteReference(DEFAULT_ACCOUNT_REFERENCE);
        userAccount.setAmount(DEFAULT_AMOUNT);
        List<Operation> operations = new ArrayList<>();
        Operation operation = new Operation();
        operation.setBalance(DEFAULT_AMOUNT);
        operation.setOperationAct(OperationActEnum.OPENING.name());
        operation.setDate(new Date());
        operations.add(operation);
        userAccount.setOperations(operations);
        userAccountRepository.save(userAccount);
    }
}
