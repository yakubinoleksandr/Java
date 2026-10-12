package com.example;

public class Main {
    public static void main(String[] args) {
        TurnstileController controller = new TurnstileController();

        String studentTermCard = controller.createTermTicket(PassengerCategory.STUDENT, 30);
        String pupilCountCard = controller.createCountTicket(PassengerCategory.PUPIL, 5);
        String regularCumulCard = controller.createCumulativeTicket(15.0);
        String expiredCard = controller.createTermTicket(PassengerCategory.REGULAR, -1);

        controller.swipeCard(studentTermCard);

        for (int i = 0; i < 6; i++) {
            controller.swipeCard(pupilCountCard);
        }

        controller.swipeCard(regularCumulCard);
        controller.swipeCard(regularCumulCard);

        controller.swipeCard(expiredCard);

        controller.swipeCard("UNKNOWN_9999");

        controller.showSummaryStats();
        controller.showDetailedStats();
    }
}
