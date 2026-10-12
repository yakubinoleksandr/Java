package com.example;

public class TurnstileController {
    private final TicketRegistry registry;
    private final Turnstile turnstile;
    private final ConsoleView view;
    private int cardCounter = 1000;

    public TurnstileController() {
        this.registry = new TicketRegistry();
        this.turnstile = new Turnstile(registry, 8.0);
        this.view = new ConsoleView();
    }

    public String createTermTicket(PassengerCategory category, int days) {
        String id = "TERM_" + (++cardCounter);
        Ticket ticket = new TermTicket(id, category, days);
        registry.registerTicket(ticket);
        return id;
    }

    public String createCountTicket(PassengerCategory category, int trips) {
        String id = "COUNT_" + (++cardCounter);
        Ticket ticket = new CountTicket(id, category, trips);
        registry.registerTicket(ticket);
        return id;
    }

    public String createCumulativeTicket(double balance) {
        String id = "CUMUL_" + (++cardCounter);
        Ticket ticket = new CumulativeTicket(id, balance);
        registry.registerTicket(ticket);
        return id;
    }

    public void swipeCard(String cardId) {
        view.printMessage("\n[Зчитування картки ID: " + cardId + "...]");
        boolean allowed = turnstile.processCard(cardId);
        view.printPassResult(allowed);
    }

    public void showSummaryStats() {
        view.printSummaryStats(turnstile.getTotalPasses(), turnstile.getTotalDenials());
    }

    public void showDetailedStats() {
        view.printDetailedStats(turnstile.getPassesByType(), turnstile.getDenialsByType());
    }
}
