package com.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Turnstile {
    private final TicketRegistry registry;
    private final double farePrice;

    private int totalPasses = 0;
    private int totalDenials = 0;

    private final Map<String, Integer> passesByType = new HashMap<>();
    private final Map<String, Integer> denialsByType = new HashMap<>();

    public Turnstile(TicketRegistry registry, double farePrice) {
        this.registry = registry;
        this.farePrice = farePrice;
    }

    public boolean processCard(String cardId) {
        Optional<Ticket> ticketOpt = registry.getTicket(cardId);

        if (ticketOpt.isEmpty()) {
            totalDenials++;
            denialsByType.put("Невідома картка", denialsByType.getOrDefault("Невідома картка", 0) + 1);
            return false;
        }

        Ticket ticket = ticketOpt.get();
        String typeDesc = ticket.getTypeDescription();

        if (ticket.validateAndPass(farePrice)) {
            totalPasses++;
            passesByType.put(typeDesc, passesByType.getOrDefault(typeDesc, 0) + 1);
            return true;
        } else {
            totalDenials++;
            denialsByType.put(typeDesc, denialsByType.getOrDefault(typeDesc, 0) + 1);
            return false;
        }
    }

    public int getTotalPasses() { return totalPasses; }
    public int getTotalDenials() { return totalDenials; }
    public Map<String, Integer> getPassesByType() { return Collections.unmodifiableMap(passesByType); }
    public Map<String, Integer> getDenialsByType() { return Collections.unmodifiableMap(denialsByType); }
}
