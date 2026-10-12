package com.example;

public class CumulativeTicket extends Ticket{
    private double balance;

    public CumulativeTicket(String id, double initialBalance) {
        super(id, PassengerCategory.REGULAR);
        this.balance = initialBalance;
    }

    public void topUp(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    @Override
    public boolean validateAndPass(double farePrice) {
        if (balance >= farePrice) {
            balance -= farePrice;
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String getTypeDescription() {
        return "Накопичувальний (Звичайний)";
    }
}
