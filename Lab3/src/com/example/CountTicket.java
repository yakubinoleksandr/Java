package com.example;

public class CountTicket extends Ticket{

    private int remainingTrips;

    public CountTicket(String id, PassengerCategory category, int trips) {
        super(id, category);
        this.remainingTrips = trips;
    }

    @Override
    public boolean validateAndPass(double farePrice) {
        if (remainingTrips > 0) {
            remainingTrips--;
            return true;
        }
        return false;
    }

    public int getRemainingTrips() {
        return remainingTrips;
    }

    @Override
    public String getTypeDescription() {
        return "Кількісний (" + getCategory().getDescription() + ")";
    }
}
