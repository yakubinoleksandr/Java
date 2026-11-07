package com.example.Task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    @DisplayName("Базовий переказ коштів між двома рахунками")
    void testSingleTransfer() {
        Account acc1 = new Account(100);
        Account acc2 = new Account(50);

        bank.transfer(acc1, acc2, 30);

        assertEquals(70, acc1.getBalance());
        assertEquals(80, acc2.getBalance());
    }

    @Test
    @DisplayName("Спроба переказу більше коштів, ніж є на рахунку")
    void testTransferInsufficientFunds() {
        Account acc1 = new Account(20);
        Account acc2 = new Account(50);

        bank.transfer(acc1, acc2, 100);

        assertEquals(20, acc1.getBalance(), "Баланс не повинен змінитися");
        assertEquals(50, acc2.getBalance(), "Баланс не повинен змінитися");
    }

    @Test
    @DisplayName("Паралельні перекази та відсутність Deadlock (збереження балансу банку)")
    void testConcurrentTransfersAndTotalBalance() throws InterruptedException {
        int numAccounts = 50;
        int numThreads = 10;
        int transfersPerThread = 1000;

        List<Account> accounts = new ArrayList<>();
        Random random = new Random(42);

        for (int i = 0; i < numAccounts; i++) {
            accounts.add(new Account(100 + random.nextInt(400)));
        }

        long initialTotalSum = accounts.stream().mapToLong(Account::getBalance).sum();

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < numThreads * transfersPerThread; i++) {
            executor.submit(() -> {
                Account from = accounts.get(random.nextInt(numAccounts));
                Account to = accounts.get(random.nextInt(numAccounts));
                int amount = random.nextInt(20) + 1;
                bank.transfer(from, to, amount);
            });
        }

        executor.shutdown();
        boolean finished = executor.awaitTermination(10, TimeUnit.SECONDS);

        assertTrue(finished, "Усі потоки повинні завершитися без deadlock");

        long finalTotalSum = accounts.stream().mapToLong(Account::getBalance).sum();
        assertEquals(initialTotalSum, finalTotalSum, "Загальна сума коштів у банку до та після переказів має збігатися");
    }
}
