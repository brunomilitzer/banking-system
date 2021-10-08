package com.brunomilitzer.bank.userfront.services.UserServiceImpl;

import com.brunomilitzer.bank.userfront.dao.PrimaryAccountDAO;
import com.brunomilitzer.bank.userfront.dao.SavingsAccountDAO;
import com.brunomilitzer.bank.userfront.entities.PrimaryAccount;
import com.brunomilitzer.bank.userfront.entities.PrimaryTransaction;
import com.brunomilitzer.bank.userfront.entities.SavingsAccount;
import com.brunomilitzer.bank.userfront.entities.SavingsTransaction;
import com.brunomilitzer.bank.userfront.entities.User;
import com.brunomilitzer.bank.userfront.services.AccountService;
import com.brunomilitzer.bank.userfront.services.TransactionService;
import com.brunomilitzer.bank.userfront.services.UserService;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
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

    @Autowired
    private TransactionService transactionService;

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

    @Override
    public void deposit(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Date date = new Date();

        if (accountType.equalsIgnoreCase("Primary")) {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
            primaryAccountDAO.save(primaryAccount);

            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Deposit to Primary Account",
                "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);

            transactionService.savePrimaryDepositTransaction(primaryTransaction);

        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            savingsAccountDAO.save(savingsAccount);

            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to Savings Account",
                "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);

            transactionService.saveSavingsDepositTransaction(savingsTransaction);
        }
    }

    @Override
    public void withdraw(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Date date = new Date();

        if (accountType.equalsIgnoreCase("Primary")) {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountDAO.save(primaryAccount);

            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Withdraw from Primary Account",
                "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);

            transactionService.savePrimaryWithdrawTransaction(primaryTransaction);

        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountDAO.save(savingsAccount);

            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from Savings Account",
                "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);

            transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
        }
    }

    private Integer accountGen() {
        return ++nextAccountNumber;
    }
}
