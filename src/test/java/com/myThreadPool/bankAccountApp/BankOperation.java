package com.myThreadPool.bankAccountApp;

/**
 * Abstract class used as a base to create Runnables that run operations on the bank account.
 */
public abstract class BankOperation implements Runnable {
    protected final CustomBankAccount account;
    protected double amount = 100;

    public BankOperation(CustomBankAccount account) {
        this.account = account;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

}