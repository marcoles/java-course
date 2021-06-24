package com.myThreadPool.temporaryPackage;

public class AddOperation extends BankOperation {

    public AddOperation(CustomBankAccount account) {
        super(account);
    }

    @Override
    public void run() {
        try {
            synchronized(account) {
                Thread.sleep(1000); //processing
                account.add(amount);
                if (isBonusActive()) {
                    AddBonusOperation bonusAdder = new AddBonusOperation(account);
                    bonusAdder.run();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
