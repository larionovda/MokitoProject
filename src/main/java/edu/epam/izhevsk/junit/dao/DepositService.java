package edu.epam.izhevsk.junit.dao;

import edu.epam.izhevsk.junit.InsufficientFundsException;

public interface DepositService {
    String deposit(Long amount, Long userId) throws InsufficientFundsException;
}
