package com.brunomilitzer.bank.userfront.services;

import com.brunomilitzer.bank.userfront.entities.PrimaryAccount;
import com.brunomilitzer.bank.userfront.entities.SavingsAccount;

public interface AccountService {

    PrimaryAccount createPrimaryAccount();

    SavingsAccount createSavingsAccount();
}
