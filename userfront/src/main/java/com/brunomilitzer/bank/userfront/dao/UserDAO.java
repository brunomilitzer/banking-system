package com.brunomilitzer.bank.userfront.dao;

import com.brunomilitzer.bank.userfront.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);
}
