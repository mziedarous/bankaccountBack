package com.sg.bank.account.repository;

import com.sg.bank.account.dto.OperationDTO;
import com.sg.bank.account.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAccountRepository extends JpaRepository<UserAccount,Integer> {

    @Query("SELECT new com.sg.bank.account.dto.OperationDTO(a.operationAct,a.date,a.balance) FROM UserAccount u JOIN u.operations a where u.id=?1")
    List<OperationDTO> getBankStatement(Integer id);

    @Query("SELECT new com.sg.bank.account.dto.OperationDTO(a.operationAct,a.date,a.balance) FROM UserAccount u JOIN u.operations a where u.compteReference=?1")
    List<OperationDTO> getBankStatementByCompteReference(String reference);

    UserAccount findUserAccountByCompteReference(String compteReference);
}
