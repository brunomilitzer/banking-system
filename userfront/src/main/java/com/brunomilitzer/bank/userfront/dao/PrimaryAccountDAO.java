package com.brunomilitzer.bank.userfront.dao;

import com.brunomilitzer.bank.userfront.entities.PrimaryAccount;
import org.springframework.data.repository.CrudRepository;

public interface PrimaryAccountDAO extends CrudRepository<PrimaryAccount, Long> {

    PrimaryAccount findByAccountNumber(Integer accountNumber);
}
