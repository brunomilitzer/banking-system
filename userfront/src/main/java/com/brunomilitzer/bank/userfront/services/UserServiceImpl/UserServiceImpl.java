package com.brunomilitzer.bank.userfront.services.UserServiceImpl;

import com.brunomilitzer.bank.userfront.dao.RoleDAO;
import com.brunomilitzer.bank.userfront.dao.UserDAO;
import com.brunomilitzer.bank.userfront.entities.User;
import com.brunomilitzer.bank.userfront.entities.security.UserRole;
import com.brunomilitzer.bank.userfront.services.AccountService;
import com.brunomilitzer.bank.userfront.services.UserService;
import java.util.Set;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        return userDAO.save(user);
    }

    @Override
    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userDAO.findByUsername(user.getUsername());

        if (localUser != null) {
            LOGGER.info("User with username {} already exists.  Nothing will be done. " + user.getUsername());

            return localUser;
        }

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        userRoles.forEach(userRole -> roleDAO.save(userRole.getRole()));

        user.getUserRoles().addAll(userRoles);

        user.setPrimaryAccount(accountService.createPrimaryAccount());
        user.setSavingsAccount(accountService.createSavingsAccount());

        return userDAO.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public boolean checkUserExists(String username, String email) {
        return checkUsernameExists(username) || checkEmailExists(email);
    }

    @Override
    public boolean checkEmailExists(String email) {
        return findByEmail(email) != null;
    }

    @Override
    public boolean checkUsernameExists(String username) {
        return findByUsername(username) != null;
    }

    @Override
    public User saveUser(User user) {
        return userDAO.save(user);
    }
}
