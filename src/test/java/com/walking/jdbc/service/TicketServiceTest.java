package com.walking.jdbc.service;

import com.walking.jdbc.db.TransactionTemplate;
import com.walking.jdbc.model.Passenger;
import com.walking.jdbc.model.Ticket;
import com.walking.jdbc.repository.PassengerRepository;
import com.walking.jdbc.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    private static final long PASSENGER_ID = 2L;

    @Mock
    private TransactionTemplate transactionTemplate;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void create_newPassenger_success() {
//        given
        var passenger = new Passenger();

        var ticket = new Ticket();
        ticket.setPassenger(passenger);

        configureTransactionTemplate(ticket);

        doAnswer(invocation -> {
            passenger.setId(PASSENGER_ID);
            return passenger;
        })
                .when(passengerRepository)
                .create(same(passenger), any());

        doReturn(ticket).when(ticketRepository).create(same(ticket), any());

//        when
        var actual = ticketService.create(ticket);

//        then
        assertSame(passenger, actual.getPassenger());
        assertNotNull(actual.getPassengerId());
        assertNotNull(actual.getPurchaseDate());
        assertNotNull(actual.getPassenger().getLastPurchase());
        assertEquals(actual.getPurchaseDate(), actual.getPassenger().getLastPurchase());

        verify(passengerRepository).create(same(passenger), any());
        verify(passengerRepository, never()).findById(any(), any());
        verify(ticketRepository).create(same(ticket), any());
        verify(passengerRepository).update(same(passenger), any());
    }

    @Test
    void create_existingPassenger_success() {
//        given
        var passenger = new Passenger();
        passenger.setId(PASSENGER_ID);

        var ticket = new Ticket();
        ticket.setPassenger(passenger);

        configureTransactionTemplate(ticket);

        doReturn(Optional.of(passenger)).when(passengerRepository).findById(eq(PASSENGER_ID), any());

        doReturn(ticket).when(ticketRepository).create(same(ticket), any());

//        when
        var actual = ticketService.create(ticket);

//        then
        assertSame(passenger, actual.getPassenger());
        assertNotNull(actual.getPassengerId());
        assertNotNull(actual.getPurchaseDate());
        assertNotNull(actual.getPassenger().getLastPurchase());
        assertEquals(actual.getPurchaseDate(), actual.getPassenger().getLastPurchase());

        verify(passengerRepository, never()).create(any(), any());
        verify(passengerRepository).findById(eq(PASSENGER_ID), any());
        verify(ticketRepository).create(same(ticket), any());
        verify(passengerRepository).update(same(passenger), any());
    }

    @Test
    void create_transactionTemplateError_failed() {
        doThrow(RuntimeException.class).when(transactionTemplate).runTransactional(any());

        Executable executable = () -> ticketService.create(new Ticket());

        assertThrows(RuntimeException.class, executable);
        verify(passengerRepository, never()).create(any(), any());
        verify(passengerRepository, never()).findById(any(), any());
        verify(ticketRepository, never()).create(any(), any());
        verify(passengerRepository, never()).update(any(), any());
    }

    @Test
    void create_invalidPassengerId_failed() {
//        given
        var passenger = new Passenger();
        passenger.setId(PASSENGER_ID);

        var ticket = new Ticket();
        ticket.setPassenger(passenger);

        configureTransactionTemplate(ticket);

        doReturn(Optional.empty()).when(passengerRepository).findById(eq(PASSENGER_ID), any());

//        when
        Executable executable = () -> ticketService.create(ticket);

//        then
        assertThrows(RuntimeException.class, executable);

        verify(passengerRepository, never()).create(any(), any());
        verify(passengerRepository).findById(eq(PASSENGER_ID), any());
        verify(ticketRepository, never()).create(any(), any());
        verify(passengerRepository, never()).update(any(), any());
    }

    @Test
    void create_repositoryError_failed() {
//        given
        var passenger = new Passenger();
        passenger.setId(PASSENGER_ID);

        var ticket = new Ticket();
        ticket.setPassenger(passenger);

        configureTransactionTemplate(ticket);

        doReturn(Optional.of(passenger)).when(passengerRepository).findById(eq(PASSENGER_ID), any());

        doThrow(RuntimeException.class).when(ticketRepository).create(same(ticket), any());

//        when
        Executable executable = () -> ticketService.create(ticket);

//        then
        assertThrows(RuntimeException.class, executable);

        verify(passengerRepository, never()).create(any(), any());
        verify(passengerRepository).findById(eq(PASSENGER_ID), any());
        verify(ticketRepository).create(same(ticket), any());
        verify(passengerRepository, never()).update(any(), any());
    }

    @SuppressWarnings("unchecked")
    private void configureTransactionTemplate(Ticket ticket) {
        doAnswer(invocation -> {
            ((Function<Connection, Ticket>) invocation.getArguments()[0]).apply(mock(Connection.class));
            return ticket;
        })
                .when(transactionTemplate)
                .runTransactional(any());
    }
}