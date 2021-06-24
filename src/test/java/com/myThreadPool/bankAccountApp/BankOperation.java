package com.myThreadPool.temporaryPackage;

public abstract class BankOperation implements Runnable {
    protected final CustomBankAccount account;
    protected double amount = 100;
    protected boolean isBonusActive = true;

    public BankOperation(CustomBankAccount account) {
        this.account = account;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setBonusActive(boolean bonusActive) {
        isBonusActive = bonusActive;
    }

    public boolean isBonusActive() {
        return isBonusActive;
    }

}