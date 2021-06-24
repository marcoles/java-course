package com.myThreadPool.temporaryPackage;

public class AddBonusOperation extends BankOperation {

    public AddBonusOperation(CustomBankAccount account) {
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
