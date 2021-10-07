package com.brunomilitzer.bank.userfront.services;

import com.brunomilitzer.bank.userfront.dao.PrimaryAccountDAO;
import com.brunomilitzer.bank.userfront.dao.SavingsAccountDAO;
import com.brunomilitzer.bank.userfront.entities.PrimaryAccount;
import com.brunomilitzer.bank.userfront.entities.SavingsAccount;
import com.brunomilitzer.bank.userfront.services.UserServiceImpl.UserService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private static int nextAccountNumber = 11223145;

    @Autowired
    private PrimaryAccountDAO primaryAccountDAO;

    @Autowired
    private SavingsAccountDAO savingsAccountDAO;

    @Autowired
    private UserService userService;

    @Override
    public PrimaryAccount createPrimaryAccount() {
        PrimaryAccount primaryAccount = new PrimaryAccount();
        primaryAccount.setAccountBalance(new BigDecimal(0.0));
        primaryAccount.setAccountNumber(accountGen());

        primaryAccountDAO.save(primaryAccount);

        return primaryAccountDAO.findByAccountNumber(primaryAccount.getAccountNumber());
    }

    @Override
    public SavingsAccount createSavingsAccount() {

        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountBalance(new BigDecimal(0.0));
        savingsAccount.setAccountNumber(accountGen());

        savingsAccountDAO.save(savingsAccount);

        return savingsAccountDAO.findByAccountNumber(savingsAccount.getAccountNumber());
    }

    private Integer accountGen() {
        return ++nextAccountNumber;
    }
}
