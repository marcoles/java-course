package com.myThreadPool.bankAccountApp;

public class AddBonusOperation extends AddOperation {


    public AddBonusOperation(CustomBankAccount account) {
        super(account);
    }

    /**
     * Calculates the bonus amount to be added to the account.
     * The amount is based on the current account balance
     *
     * @return
     * Returns the calculated amount
     */
    private double calculateBonusAmount(){
        return account.getBalance() * super.multiplier;
    }

    /**
     * The run method adds the calculated bonus amount to the bank account
     */
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
