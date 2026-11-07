package com.example.Task1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private static final AtomicInteger ID_COUNTER = new AtomicInteger(0);

    private final int id;
    private int balance;
    private final Lock lock = new ReentrantLock();

    public Account(int initialBalance) {
        this.id = ID_COUNTER.getAndIncrement();
        this.balance = initialBalance;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public Lock getLock() {
        return lock;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }
}
