package com.walking.jdbc.service;

import com.walking.jdbc.db.TransactionTemplate;
import com.walking.jdbc.model.Ticket;
import com.walking.jdbc.repository.PassengerRepository;
import com.walking.jdbc.repository.TicketRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class TicketService {
    private final static Logger log = LogManager.getLogger(TicketService.class);

    private final TransactionTemplate transactionTemplate;
    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;

    public TicketService(TransactionTemplate transactionTemplate, TicketRepository ticketRepository,
                         PassengerRepository passengerRepository) {
        this.transactionTemplate = transactionTemplate;
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
    }

    public Ticket create(Ticket ticket) {
        return transactionTemplate.runTransactional(connection -> {
            var passengerId = ticket.getPassengerId();
            var passenger = passengerId == null
                    ? passengerRepository.create(ticket.getPassenger(), connection)
                    : passengerRepository.findById(passengerId, connection)
                    .orElseThrow(() -> new RuntimeException("Пассажир с id %d не существует".formatted(passengerId)));

            ticket.setPassenger(passenger);

            ticket.setPurchaseDate(LocalDateTime.now());
            var createdTicket = ticketRepository.create(ticket, connection);

            passenger.setLastPurchase(ticket.getPurchaseDate());
            passengerRepository.update(passenger, connection);

            log.debug("Был добавлен билет с id {}", ticket.getId());

            return createdTicket;
        });
    }
}
