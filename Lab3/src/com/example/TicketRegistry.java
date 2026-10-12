package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TicketRegistry {
    private final Map<String, Ticket> tickets = new HashMap<>();

    public void registerTicket(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
    }

    public Optional<Ticket> getTicket(String id) {
        return Optional.ofNullable(tickets.get(id));
    }
}
