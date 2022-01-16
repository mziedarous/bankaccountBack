package com.sg.bank.account;

import com.sg.bank.account.api.UserAccountApi;
import com.sg.bank.account.dto.AmountDTO;
import com.sg.bank.account.dto.OperationDTO;
import com.sg.bank.account.dto.UserAccountDTO;
import com.sg.bank.account.entity.UserAccount;
import com.sg.bank.account.exception.BankAccountException;
import com.sg.bank.account.service.UserAccountService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BankaccountApplicationTests {

    private static final Integer TEST_ID = 1;
    private static final Integer TEST_EXPECTED_RETURN_SIZE_ID = 2;
    private static final String TEST_REFERENCE = "536636975";
    private static final String TEST_VALUE = "TEST";
    private static final Double TEST_AMOUNT_VALUE = 5000.0;

    @Mock
    UserAccountService userAccountService;

    @InjectMocks
    UserAccountApi userAccountApi;


    @Test
    public void testCreateUserAccount() throws BankAccountException {
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        UserAccount userAccount = new UserAccount();
        userAccount.setAmount(TEST_AMOUNT_VALUE);
        when(userAccountService.create(userAccountDTO)).thenReturn(userAccount);
        ResponseEntity<UserAccount> userAccountReturn = userAccountApi.create(userAccountDTO);
        verify(userAccountService).create(userAccountDTO);
        assertNotNull(userAccountReturn);
        assertEquals(TEST_AMOUNT_VALUE, userAccountReturn.getBody().getAmount());

    }

    @Test
    public void testfindUserAccount() throws BankAccountException {
        UserAccount userAccount = new UserAccount();
        userAccount.setFirstName(TEST_VALUE);
        userAccount.setAmount(TEST_AMOUNT_VALUE);
        when(userAccountService.findUserAccount(TEST_ID)).thenReturn(userAccount);
        ResponseEntity<UserAccount> userAccountResponse = userAccountApi.findById(TEST_ID);
        verify(userAccountService).findUserAccount(TEST_ID);
        assertNotNull(userAccountResponse);
        assertEquals(TEST_AMOUNT_VALUE, userAccountResponse.getBody().getAmount());
    }

    @Test
    public void testUserAccountByCompteReference() throws BankAccountException {
        UserAccount userAccount = new UserAccount();
        userAccount.setFirstName(TEST_VALUE);
        userAccount.setAmount(TEST_AMOUNT_VALUE);
        when(userAccountService.findUserAccountByCompteReference(TEST_REFERENCE)).thenReturn(userAccount);
        ResponseEntity<UserAccount> userAccountResponse = userAccountApi.findByReference(TEST_REFERENCE);
        verify(userAccountService).findUserAccountByCompteReference(TEST_REFERENCE);
        assertNotNull(userAccountResponse);
        assertEquals(TEST_AMOUNT_VALUE, userAccountResponse.getBody().getAmount());
    }

    @Test
    public void testFindAllUserAccount() throws BankAccountException {
        List<UserAccount> userAccounts =  new ArrayList<>();
        userAccounts.add(new UserAccount());
        userAccounts.add(new UserAccount());
        when(userAccountService.findAllUserAccount()).thenReturn(userAccounts);
        ResponseEntity<List<UserAccount>> userAccountListResponse = userAccountApi.findAll();
        assertNotNull(userAccountListResponse);
        assertEquals(TEST_EXPECTED_RETURN_SIZE_ID,userAccountListResponse.getBody().size());
        verify(userAccountService).findAllUserAccount();
    }

    @Test
    public void testGetBankStatement() throws BankAccountException {
        List<OperationDTO> operationDTOS = new ArrayList<>();
        operationDTOS.add(new OperationDTO());
        operationDTOS.add(new OperationDTO());
        when(userAccountService.getBankStatement(TEST_ID)).thenReturn(operationDTOS);
        ResponseEntity<List<OperationDTO>> listOperationDTO = userAccountApi.getBankStatementById(TEST_ID);
        assertNotNull(listOperationDTO);
        assertEquals(TEST_EXPECTED_RETURN_SIZE_ID,listOperationDTO.getBody().size());
        verify(userAccountService).getBankStatement(TEST_ID);
    }

    @Test
    public void testGetBankStatementByReference() throws BankAccountException {
        List<OperationDTO> operationDTOS = new ArrayList<>();
        operationDTOS.add(new OperationDTO());
        operationDTOS.add(new OperationDTO());
        when(userAccountService.getBankStatementByReference(TEST_REFERENCE)).thenReturn(operationDTOS);
        ResponseEntity<List<OperationDTO>> listOperationDTO = userAccountApi.getBankStatementByReference(TEST_REFERENCE);
        assertNotNull(listOperationDTO);
        assertEquals(TEST_EXPECTED_RETURN_SIZE_ID,listOperationDTO.getBody().size());
        verify(userAccountService).getBankStatementByReference(TEST_REFERENCE);
    }
    @Test
    public void testDebitAccount() throws BankAccountException {
        AmountDTO amountDTO = new AmountDTO();
        amountDTO.setAmount(TEST_AMOUNT_VALUE);
        UserAccount userAccount = new UserAccount();
        userAccount.setAmount(TEST_AMOUNT_VALUE);
        when(userAccountService.debitAccount(TEST_REFERENCE,amountDTO)).thenReturn(userAccount);
        ResponseEntity<UserAccount>  userAccountResponseEntity = userAccountApi.debit(TEST_REFERENCE,amountDTO);
        assertNotNull(userAccountResponseEntity);
        assertEquals(TEST_AMOUNT_VALUE,userAccountResponseEntity.getBody().getAmount());
        verify(userAccountService).debitAccount(TEST_REFERENCE,amountDTO);
    }
    @Test
    public void testCreditAccount() throws BankAccountException {
        AmountDTO amountDTO = new AmountDTO();
        amountDTO.setAmount(TEST_AMOUNT_VALUE);
        UserAccount userAccount = new UserAccount();
        userAccount.setAmount(TEST_AMOUNT_VALUE);
        when(userAccountService.creditAccount(TEST_REFERENCE,amountDTO)).thenReturn(userAccount);
        ResponseEntity<UserAccount>  userAccountResponseEntity = userAccountApi.credit(TEST_REFERENCE,amountDTO);
        assertNotNull(userAccountResponseEntity);
        assertEquals(TEST_AMOUNT_VALUE,userAccountResponseEntity.getBody().getAmount());
        verify(userAccountService).creditAccount(TEST_REFERENCE,amountDTO);
    }

}
