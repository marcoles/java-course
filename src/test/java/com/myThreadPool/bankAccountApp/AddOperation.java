package com.myThreadPool.bankAccountApp;

public class AddOperation extends BankOperation {
    protected boolean isBonusActive = true;
    protected final double multiplier = 0.01;

    public AddOperation(CustomBankAccount account) {
        super(account);
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

    public double getMultiplier() {
        return multiplier;
    }

    /**
     * The run method has a 1 second sleep to simulate longer processing.
     * After that it adds some money to the bank account.
     * If the bonus is active, it also creates a bonus adder and runs it.
     */
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
