package com.brunomilitzer.bank.userfront.resource;

import com.brunomilitzer.bank.userfront.entities.PrimaryTransaction;
import com.brunomilitzer.bank.userfront.entities.SavingsTransaction;
import com.brunomilitzer.bank.userfront.entities.User;
import com.brunomilitzer.bank.userfront.services.TransactionService;
import com.brunomilitzer.bank.userfront.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<User> userList() {
        return userService.findUserList();
    }

    @RequestMapping(value = "/user/primary/transaction", method = RequestMethod.GET)
    public List<PrimaryTransaction> getPrimaryTransactionList(@RequestParam("username") String username) {
        return transactionService.findPrimaryTransactionList(username);
    }

    @RequestMapping(value = "/user/savings/transaction", method = RequestMethod.GET)
    public List<SavingsTransaction> getSavingsTransactionList(@RequestParam("username") String username) {
        return transactionService.findSavingsTransactionList(username);
    }

    @RequestMapping(value = "/user/{username}/enable")
    public void enableUser(@RequestParam("username") String username) {
        userService.enableUser(username);
    }

    @RequestMapping(value = "/user/{username}/disable")
    public void disableUser(@RequestParam("username") String username) {
        userService.disableUser(username);
    }
}
