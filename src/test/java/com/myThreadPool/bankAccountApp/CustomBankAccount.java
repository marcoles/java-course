package com.myThreadPool.bankAccountApp;

/**
 * Simple bank account class
 */
public class CustomBankAccount {
    private double balance = 0;

    public double getBalance() {
        return balance;
    }

    /**
     * Used to increase current account balance
     *
     * @param amount
     * Amount to be added to current balance
     */
    public void add(double amount) {
        this.balance += amount;
        // System.out.printf("Current account balance: %f\n", getBalance());
    }

    /**
     * Used to decrease current account balance
     *
     * @param amount
     * Amount to be withdrawn from the account
     */
    public void withdraw(double amount) {
        this.balance -= amount;
        // System.out.printf("Current account balance: %f\n", getBalance());
    }

}
