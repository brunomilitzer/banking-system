package com.brunomilitzer.bank.userfront.dao;

import com.brunomilitzer.bank.userfront.entities.PrimaryTransaction;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface PrimaryTransactionDAO extends CrudRepository<PrimaryTransaction, Long> {

    List<PrimaryTransaction> findAll();
}
