package com.myThreadPool.app;

public class Adder extends BankOperation {

    public Adder(CustomBankAccount account) {
        super(account);
    }

    @Override
    public void run() {
        try {
            synchronized(account) {
                Thread.sleep(1000); //processing
                account.add(amount);
                if (isBonusActive()) {
                    BonusAdder bonusAdder = new BonusAdder(account);
                    bonusAdder.run();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
