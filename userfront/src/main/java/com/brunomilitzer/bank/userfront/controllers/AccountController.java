package com.brunomilitzer.bank.userfront.controllers;

import com.brunomilitzer.bank.userfront.entities.PrimaryAccount;
import com.brunomilitzer.bank.userfront.entities.PrimaryTransaction;
import com.brunomilitzer.bank.userfront.entities.SavingsAccount;
import com.brunomilitzer.bank.userfront.entities.SavingsTransaction;
import com.brunomilitzer.bank.userfront.entities.User;
import com.brunomilitzer.bank.userfront.services.AccountService;
import com.brunomilitzer.bank.userfront.services.TransactionService;
import com.brunomilitzer.bank.userfront.services.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/primaryAccount")
    public String primaryAccount(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();

        List<PrimaryTransaction> primaryTransactionList =
            transactionService.findPrimaryTransactionList(user.getUsername());

        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("primaryTransactionList", primaryTransactionList);

        return "primaryAccount";
    }

    @RequestMapping("/savingsAccount")
    public String savingsAccount(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        SavingsAccount savingsAccount = user.getSavingsAccount();

        List<SavingsTransaction> savingsTransactionList =
            transactionService.findSavingsTransactionList(user.getUsername());

        model.addAttribute("savingsAccount", savingsAccount);
        model.addAttribute("savingsTransactionList", savingsTransactionList);

        return "savingsAccount";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.GET)
    public String deposit(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "deposit";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public String depositPost(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
        accountService.deposit(accountType, Double.parseDouble(amount), principal);

        return "redirect:/userFront";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public String withdraw(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "withdraw";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdrawPost(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
        accountService.withdraw(accountType, Double.parseDouble(amount), principal);

        return "redirect:/userFront";
    }
}
