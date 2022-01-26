package com.sg.bank.account.api;

import com.sg.bank.account.dto.OperationDTO;
import com.sg.bank.account.dto.AmountDTO;
import com.sg.bank.account.dto.UserAccountDTO;
import com.sg.bank.account.entity.UserAccount;
import com.sg.bank.account.exception.BankAccountException;
import com.sg.bank.account.service.UserAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/account")
public class UserAccountApi {

    private UserAccountService userAccountService;

    public UserAccountApi(UserAccountService accountUserService) {
        this.userAccountService = accountUserService;
    }

    @PostMapping("/useraccount")
    public ResponseEntity<UserAccount> create(@RequestBody @Valid UserAccountDTO userAccountDTO) throws BankAccountException {
        return ResponseEntity.ok(userAccountService.create(userAccountDTO));
    }

    @GetMapping("/userid/{id}")
    public ResponseEntity<UserAccount> findById(@PathVariable("id") Integer id) throws BankAccountException {
        return ResponseEntity.ok(userAccountService.findUserAccount(id));
    }
    @GetMapping("/reference/{ref}")
    public ResponseEntity<UserAccount> findByReference(@PathVariable("ref") String ref) throws BankAccountException {
        return ResponseEntity.ok(userAccountService.findUserAccountByCompteReference(ref));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserAccount>> findAll() throws BankAccountException {
        return ResponseEntity.ok(userAccountService.findAllUserAccount());
    }

    @GetMapping("/bankstatement/userid/{id}")
    public ResponseEntity<List<OperationDTO>> getBankStatementById(@PathVariable("id") Integer id) throws BankAccountException {
        return ResponseEntity.ok(userAccountService.getBankStatement(id));
    }

    @GetMapping("/bankstatement/reference/{ref}")
    public ResponseEntity<List<OperationDTO>> getBankStatementByReference(@PathVariable("ref") String ref) throws BankAccountException {
        return ResponseEntity.ok(userAccountService.getBankStatementByReference(ref));
    }

    @PostMapping("/debit/reference/{reference}")
    public ResponseEntity<UserAccount> debit(@PathVariable("reference") String reference, @RequestBody @Valid AmountDTO amountDTO) throws BankAccountException {
        return ResponseEntity.ok(userAccountService.debitAccount(reference, amountDTO));
    }

    @PostMapping("/credit/reference/{reference}")
    public ResponseEntity<UserAccount> credit(@PathVariable("reference") String reference, @RequestBody @Valid AmountDTO amountDTO) throws BankAccountException {
        return ResponseEntity.ok(userAccountService.creditAccount(reference, amountDTO));
    }

}
