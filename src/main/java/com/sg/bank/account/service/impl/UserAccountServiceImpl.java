package com.sg.bank.account.service.impl;

import com.sg.bank.account.dto.AmountDTO;
import com.sg.bank.account.dto.OperationDTO;
import com.sg.bank.account.dto.UserAccountDTO;
import com.sg.bank.account.entity.Operation;
import com.sg.bank.account.entity.UserAccount;
import com.sg.bank.account.exception.BankAccountException;
import com.sg.bank.account.repository.UserAccountRepository;
import com.sg.bank.account.service.UserAccountService;
import com.sg.bank.account.util.OperationActEnum;

import com.sg.bank.account.util.TechnicialConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(UserAccountServiceImpl.class);


    private EntityManager entityManager;
    private UserAccountRepository userAccountRepository;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, EntityManager entityManager) {
        this.userAccountRepository = userAccountRepository;
        this.entityManager = entityManager;
    }


    @Override
    public UserAccount create(UserAccountDTO userAccountDTO) throws BankAccountException {
        try {
            return userAccountRepository.save(intUserAccount(userAccountDTO));
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new BankAccountException(TechnicialConstant.CREATE_ACCOUNT_ERROR);
        }
    }

    @Override
    public List<UserAccount> findAllUserAccount() throws BankAccountException {
        try {
        return StreamSupport.stream(userAccountRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new BankAccountException(TechnicialConstant.FIND_ALL_ACCOUNTS_ERROR);
        }
    }

    @Override
    public UserAccount findUserAccount(Integer id) throws BankAccountException {
        try {
        return userAccountRepository.findById(id).get();
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new BankAccountException(TechnicialConstant.FIND_ACCOUNT_ERROR);
        }
    }


    @Override
    public List<OperationDTO> getBankStatement(Integer id) throws BankAccountException {
        try {
        return userAccountRepository.getBankStatement(id);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new BankAccountException(TechnicialConstant.BANK_STATEMENT_ERROR);
        }
    }

    @Override
    public List<OperationDTO> getBankStatementByReference(String reference) throws BankAccountException {
        try {
            return userAccountRepository.getBankStatementByCompteReference(reference);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new BankAccountException(TechnicialConstant.BANK_STATEMENT_ERROR);
        }
    }

    @Override
    @Transactional
    public UserAccount debitAccount(String reference, AmountDTO amountDTO) throws BankAccountException {
        try {
        return entityManager.merge(getUserAccount(reference, amountDTO, OperationActEnum.DEPOSIT));
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new BankAccountException(TechnicialConstant.DEBIT_ACCOUNT_ERROR);
        }
    }

    @Override
    @Transactional
        public UserAccount creditAccount(String reference, AmountDTO amountDTO) throws BankAccountException {
        try {
            return entityManager.merge(getUserAccount(reference, amountDTO, OperationActEnum.WITHDRAWAL));
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new BankAccountException(TechnicialConstant.CREDIT_ACCOUNR_ERROR);
        }
    }

    @Override
    public UserAccount findUserAccountByCompteReference(String compteReference) throws BankAccountException {
        try {
            return userAccountRepository.findUserAccountByCompteReference(compteReference);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new BankAccountException(TechnicialConstant.FIND_ACCOUNT_ERROR);
        }
    }

    private UserAccount getUserAccount(String reference, AmountDTO amountDTO, OperationActEnum operationEnum) {
        UserAccount userAccount = new UserAccount();
        Operation operation = new Operation();
        operation.setOperationAct(operationEnum.name());
        operation.setDate(new Date());
        userAccount = userAccountRepository.findUserAccountByCompteReference(reference);
        if (OperationActEnum.DEPOSIT.equals(operationEnum)) {
            userAccount.getOperations().add(operation);
            operation.setBalance(amountDTO.getAmount());
            userAccount.setAmount(userAccount.getAmount() + amountDTO.getAmount());
        } else if (OperationActEnum.WITHDRAWAL.equals(operationEnum)) {
            userAccount.getOperations().add(operation);
            operation.setBalance(amountDTO.getAmount()*-1);
            userAccount.setAmount(userAccount.getAmount() - amountDTO.getAmount());
        }
        return userAccount;
    }

    private UserAccount intUserAccount(UserAccountDTO userAccountDTO) {
        UserAccount userAccount = new UserAccount();
        userAccount.setAddress(userAccountDTO.getAddress());
        userAccount.setFirstName(userAccountDTO.getFirstName());
        userAccount.setLastName(userAccountDTO.getLastName());
        userAccount.setAmount(userAccountDTO.getAmount());
        userAccount.setCompteReference((int)Math.floor(Math.abs(Math.random()*(10000001)+100000000))+"");
        List<Operation> operations = new ArrayList<>();
        Operation operation = new Operation();
        operation.setBalance(userAccountDTO.getAmount());
        operation.setOperationAct(OperationActEnum.OPENING.name());
        operation.setDate(new Date());
        operations.add(operation);
        userAccount.setOperations(operations);
        return userAccount;
    }


}
