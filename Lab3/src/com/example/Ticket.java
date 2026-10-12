package com.example;

abstract class Ticket {
    private final String id;
    private final PassengerCategory category;

    public Ticket(String id, PassengerCategory category) {
        this.id = id;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public PassengerCategory getCategory() {
        return category;
    }

    public abstract boolean validateAndPass(double farePrice);
    public abstract String getTypeDescription();
}
