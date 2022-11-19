package edu.lambton.util;

import java.util.Random;

public class AccountNumberGenerator {

    public long generatorAccountNumber() {
        Random randomAccountNumber = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int accIndex = 0; accIndex < 14; accIndex++) {
            int number = randomAccountNumber.nextInt(10);
            accountNumber.append(number);
        }
        return Long.parseLong(accountNumber.toString());
    }
}
