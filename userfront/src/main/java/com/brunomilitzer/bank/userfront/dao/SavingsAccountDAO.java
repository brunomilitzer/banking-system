package com.brunomilitzer.bank.userfront.dao;

import com.brunomilitzer.bank.userfront.entities.SavingsAccount;
import org.springframework.data.repository.CrudRepository;

public interface SavingsAccountDAO extends CrudRepository<SavingsAccount, Long> {
    
    SavingsAccount findByAccountNumber(Integer accountNumber);
}
