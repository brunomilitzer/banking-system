package com.brunomilitzer.bank.userfront.dao;

import com.brunomilitzer.bank.userfront.entities.SavingsTransaction;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface SavingsTransactionDAO extends CrudRepository<SavingsTransaction, Long> {

    List<SavingsTransaction> findAll();
}
