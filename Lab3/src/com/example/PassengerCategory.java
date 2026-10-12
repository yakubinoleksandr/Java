package com.example;

public enum PassengerCategory {
    PUPIL("Учнівський"),
    STUDENT("Студентський"),
    REGULAR("Звичайний");

    private final String description;

    PassengerCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}