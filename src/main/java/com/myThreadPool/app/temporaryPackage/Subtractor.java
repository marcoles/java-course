package com.myThreadPool.app.temporaryPackage;

import com.myThreadPool.app.temporaryPackage.BankOperation;
import com.myThreadPool.app.temporaryPackage.CustomBankAccount;

public class Subtractor extends BankOperation {

    public Subtractor(CustomBankAccount account) {
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
