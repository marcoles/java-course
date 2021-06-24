package com.myThreadPool.temporaryPackage;

public class CustomBankAccount {
    private double balance = 0;

    public double getBalance() {
        return balance;
    }

    public void add(double amount) {
        this.balance += amount;
        System.out.printf("Current account balance: %f\n", getBalance());
    }

    public void withdraw(double amount) {
        this.balance -= amount;
        System.out.printf("Current account balance: %f\n", getBalance());
    }

}
