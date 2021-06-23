package com.myThreadPool.app.temporaryPackage;

import com.myThreadPool.app.temporaryPackage.BankOperation;
import com.myThreadPool.app.temporaryPackage.CustomBankAccount;

public class BonusAdder extends BankOperation {

    public BonusAdder(CustomBankAccount account) {
        super(account);
    }

    private double calculateBonusAmount(){
        return account.getBalance() * 0.01;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000); //processing
            account.add(calculateBonusAmount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
