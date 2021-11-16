package com.brunomilitzer.bank.userfront.dao;

import com.brunomilitzer.bank.userfront.entities.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findAll();
}
