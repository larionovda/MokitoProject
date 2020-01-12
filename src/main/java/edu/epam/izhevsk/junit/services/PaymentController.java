package edu.epam.izhevsk.junit.services;

import edu.epam.izhevsk.junit.InsufficientFundsException;
import edu.epam.izhevsk.junit.dao.AccountService;
import edu.epam.izhevsk.junit.dao.DepositService;

public class PaymentController {
    private AccountService accountService;
    private DepositService depositService;


    public PaymentController(AccountService accountService, DepositService depositService) {
        this.accountService = accountService;
        this.depositService = depositService;
    }

    public void deposit(Long amount, Long userId) throws InsufficientFundsException {
        if (accountService.isUserAuthenticated(userId)) {
            depositService.deposit(amount, userId);
        } else {
            throw new SecurityException("User not authenticated: " + userId);
        }
    }
}
