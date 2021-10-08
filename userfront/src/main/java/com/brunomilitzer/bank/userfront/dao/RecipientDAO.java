package com.brunomilitzer.bank.userfront.dao;

import com.brunomilitzer.bank.userfront.entities.Recipient;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface RecipientDAO extends CrudRepository<Recipient, Long> {

    List<Recipient> findAll();

    Recipient findByName(String name);

    void deleteByName(String name);
}
