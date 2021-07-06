package com.myThreadPool;

import com.myThreadPool.bankAccountApp.AddOperation;
import com.myThreadPool.bankAccountApp.BankOperation;

import java.util.List;

public class BankOperationHelper {

    public static double calculateExpectedBalance (List<AddOperation> operations) {

        double balance = 0;
        for (AddOperation operation: operations) {
            balance += operation.getAmount();
            balance += balance * operation.getMultiplier();
        }
        return balance;
    }
}
