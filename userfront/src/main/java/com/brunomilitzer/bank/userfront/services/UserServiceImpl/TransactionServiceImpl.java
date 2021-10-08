package com.brunomilitzer.bank.userfront.services.UserServiceImpl;

import com.brunomilitzer.bank.userfront.dao.PrimaryAccountDAO;
import com.brunomilitzer.bank.userfront.dao.PrimaryTransactionDAO;
import com.brunomilitzer.bank.userfront.dao.RecipientDAO;
import com.brunomilitzer.bank.userfront.dao.SavingsAccountDAO;
import com.brunomilitzer.bank.userfront.dao.SavingsTransactionDAO;
import com.brunomilitzer.bank.userfront.entities.PrimaryAccount;
import com.brunomilitzer.bank.userfront.entities.PrimaryTransaction;
import com.brunomilitzer.bank.userfront.entities.Recipient;
import com.brunomilitzer.bank.userfront.entities.SavingsAccount;
import com.brunomilitzer.bank.userfront.entities.SavingsTransaction;
import com.brunomilitzer.bank.userfront.entities.User;
import com.brunomilitzer.bank.userfront.services.TransactionService;
import com.brunomilitzer.bank.userfront.services.UserService;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private PrimaryTransactionDAO primaryTransactionDAO;

    @Autowired
    private SavingsTransactionDAO savingsTransactionDAO;

    @Autowired
    private PrimaryAccountDAO primaryAccountDAO;

    @Autowired
    private SavingsAccountDAO savingsAccountDAO;

    @Autowired
    private RecipientDAO recipientDAO;

    @Override
    public List<PrimaryTransaction> findPrimaryTransactionList(String username) {
        User user = userService.findByUsername(username);
        return user.getPrimaryAccount().getPrimaryTransactions();
    }

    @Override
    public List<SavingsTransaction> findSavingsTransactionList(String username) {
        User user = userService.findByUsername(username);
        return user.getSavingsAccount().getSavingsTransactions();
    }

    @Override
    public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction) {
        primaryTransactionDAO.save(primaryTransaction);
    }

    @Override
    public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
        savingsTransactionDAO.save(savingsTransaction);
    }

    @Override
    public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction) {
        primaryTransactionDAO.save(primaryTransaction);
    }

    @Override
    public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
        savingsTransactionDAO.save(savingsTransaction);
    }

    @Override
    public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount,
                                        PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception {
        Date date = new Date();

        if (transferFrom.equalsIgnoreCase("Primary") && transferTo.equalsIgnoreCase("Savings")) {
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));

            primaryAccountDAO.save(primaryAccount);
            savingsAccountDAO.save(savingsAccount);

            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Between account transfer from "
                + transferFrom + " to " + transferTo, "Account", "Finished", Double.parseDouble(amount),
                primaryAccount.getAccountBalance(), primaryAccount);

            primaryTransactionDAO.save(primaryTransaction);
        } else if (transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Primary")) {
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));

            primaryAccountDAO.save(primaryAccount);
            savingsAccountDAO.save(savingsAccount);

            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Between account transfer from "
                + transferFrom + " to " + transferTo, "Account", "Finished", Double.parseDouble(amount),
                savingsAccount.getAccountBalance(), savingsAccount);

            savingsTransactionDAO.save(savingsTransaction);

        } else {
            throw new Exception("Invalid transfer");
        }
    }

    @Override
    public List<Recipient> findRecipientList(Principal principal) {
        String username = principal.getName();

        return recipientDAO.findAll().stream().filter(recipient -> username.equals(recipient.getUser().getUsername()))
                .collect(Collectors.toList());
    }

    public Recipient saveRecipient(Recipient recipient) {
        return recipientDAO.save(recipient);
    }

    @Override
    public Recipient findRecipientByName(String recipientName) {
        return recipientDAO.findByName(recipientName);
    }

    @Override
    public void deleteRecipientByName(String recipientName) {
        recipientDAO.deleteByName(recipientName);
    }

    @Override
    public void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount,
                                      PrimaryAccount primaryAccount, SavingsAccount savingsAccount) {

        Date date = new Date();

        if (accountType.equalsIgnoreCase("Primary")) {
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountDAO.save(primaryAccount);

            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Transfer to recipient "
                + recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount),
                primaryAccount.getAccountBalance(), primaryAccount);

            primaryTransactionDAO.save(primaryTransaction);
        } else if (accountType.equalsIgnoreCase("Savings")) {
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountDAO.save(savingsAccount);

            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Transfer to recipient "
                + recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount),
                savingsAccount.getAccountBalance(), savingsAccount);

            savingsTransactionDAO.save(savingsTransaction);
        }
    }
}
