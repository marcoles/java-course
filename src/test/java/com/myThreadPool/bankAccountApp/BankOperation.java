package com.myThreadPool.bankAccountApp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Abstract class used as a base to create Runnables that run operations on the bank account.
 */
@RequiredArgsConstructor
public abstract class BankOperation implements Runnable {
    protected final CustomBankAccount account;
    @Getter
    @Setter
    protected double amount = 100;
}