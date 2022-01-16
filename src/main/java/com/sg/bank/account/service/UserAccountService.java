package com.sg.bank.account.service;

import com.sg.bank.account.dto.OperationDTO;
import com.sg.bank.account.dto.AmountDTO;
import com.sg.bank.account.dto.UserAccountDTO;
import com.sg.bank.account.entity.UserAccount;
import com.sg.bank.account.exception.BankAccountException;

import java.util.List;

public interface UserAccountService {
    /**
     * This method will create a new acount
     * @param userAccountDTO
     * @return
     */
    UserAccount create(UserAccountDTO userAccountDTO) throws BankAccountException;

    /**
     * This method will get all account
     * @return
     */
    List<UserAccount> findAllUserAccount() throws BankAccountException;

    /**
     *
     * @param id
     * @return
     */
    UserAccount findUserAccount(Integer id) throws BankAccountException;

    /**
     *
     * @param id
     * @return
     */
    List<OperationDTO> getBankStatement(Integer id) throws BankAccountException;


    /**
     *
     * @param reference
     * @return
     * @throws BankAccountException
     */
     List<OperationDTO> getBankStatementByReference(String reference) throws BankAccountException;

        /**
         *
         * @param reference
         * @param amountDTO
         * @return
         */
    UserAccount debitAccount(String reference , AmountDTO amountDTO) throws BankAccountException;

    /**
     *
     * @param reference
     * @param amountDTO
     * @return
     */
    UserAccount creditAccount(String reference , AmountDTO amountDTO) throws BankAccountException;

    /**
     *
     * @param compteReference
     * @return
     */
    UserAccount findUserAccountByCompteReference(String compteReference) throws BankAccountException;

}
