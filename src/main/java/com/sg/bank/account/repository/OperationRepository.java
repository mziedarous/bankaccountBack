package com.sg.bank.account.repository;

import com.sg.bank.account.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Integer> {


}
