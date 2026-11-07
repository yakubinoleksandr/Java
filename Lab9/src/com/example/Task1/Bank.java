package com.example.Task1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Bank {

    public void transfer(Account from, Account to, int amount) {
        if (from == to) return;

        Account firstLock = from.getId() < to.getId() ? from : to;
        Account secondLock = from.getId() < to.getId() ? to : from;

        firstLock.getLock().lock();
        try {
            secondLock.getLock().lock();
            try {
                if (from.getBalance() >= amount) {
                    from.withdraw(amount);
                    to.deposit(amount);
                }
            } finally {
                secondLock.getLock().unlock();
            }
        } finally {
            firstLock.getLock().unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        int numAccounts = 100;
        int numTransfers = 5000;
        List<Account> accounts = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numAccounts; i++) {
            accounts.add(new Account(100 + random.nextInt(901)));
        }

        long initialTotalSum = accounts.stream().mapToLong(Account::getBalance).sum();
        System.out.println("Початкова загальна сума в банку: " + initialTotalSum);

        ExecutorService executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < numTransfers; i++) {
            executor.submit(() -> {
                Account from = accounts.get(random.nextInt(numAccounts));
                Account to = accounts.get(random.nextInt(numAccounts));
                int amount = random.nextInt(50) + 1;

                bank.transfer(from, to, amount);
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        long finalTotalSum = accounts.stream().mapToLong(Account::getBalance).sum();
        System.out.println("Фінальна загальна сума в банку:   " + finalTotalSum);
        System.out.println("Суми збігаються: " + (initialTotalSum == finalTotalSum));
    }
}