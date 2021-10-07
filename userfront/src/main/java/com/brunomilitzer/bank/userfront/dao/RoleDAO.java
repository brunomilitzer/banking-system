package com.brunomilitzer.bank.userfront.dao;

import com.brunomilitzer.bank.userfront.entities.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleDAO extends CrudRepository<Role, Long> {

    Role findByName(String name);
}
