package com.myThreadPool.bankAccountApp;

/**
 * Abstract class used as a base to create Runnables that run operations on the bank account.
 */
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

    /**
     * Used to check if the bonus on deposits is currently active
     *
     * @return
     * Returns true if bonus is active and false if it is inactive
     */
    public boolean isBonusActive() {
        return isBonusActive;
    }

}