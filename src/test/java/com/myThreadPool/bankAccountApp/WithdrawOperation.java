package com.myThreadPool.bankAccountApp;

public class WithdrawOperation extends BankOperation {

    public WithdrawOperation(CustomBankAccount account) {
        super(account);
    }

    /**
     * The run method has a 1 second sleep to simulate longer processing.
     * After that it removes some money from the bank account.
     */
    @Override
    public void run() {
        try {
            synchronized(account) {
                Thread.sleep(1000); //processing
                account.withdraw(amount);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
