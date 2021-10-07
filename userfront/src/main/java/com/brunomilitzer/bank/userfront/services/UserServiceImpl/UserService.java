package com.brunomilitzer.bank.userfront.services.UserServiceImpl;

import com.brunomilitzer.bank.userfront.entities.User;
import com.brunomilitzer.bank.userfront.entities.security.UserRole;
import java.util.Set;

public interface UserService {

    User save(User user);

    User createUser(User user, Set<UserRole> userRoles);

    User findByUsername(String username);

    User findByEmail(String email);

    boolean checkUserExists(String username, String email);

    boolean checkEmailExists(String email);

    boolean checkUsernameExists(String username);
}
