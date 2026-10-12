package com.example;

import java.time.LocalDateTime;

public class TermTicket extends Ticket{

    private final LocalDateTime expirationDate;

    public TermTicket(String id, PassengerCategory category, int daysValid) {
        super(id, category);
        this.expirationDate = LocalDateTime.now().plusDays(daysValid);
    }

    @Override
    public boolean validateAndPass(double farePrice) {
        return LocalDateTime.now().isBefore(expirationDate);
    }

    @Override
    public String getTypeDescription() {
        return "Терміновий (" + getCategory().getDescription() + ")";
    }
}
