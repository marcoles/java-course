package com.myThreadPool.temporaryPackage;

public class WithdrawOperation extends BankOperation {

    public WithdrawOperation(CustomBankAccount account) {
        super(account);
    }

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
