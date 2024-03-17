package com.walking.jdbc.mapper;

import com.walking.jdbc.model.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketMapper {

    public Optional<Ticket> map(ResultSet rs) throws SQLException {
        return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
    }

    public List<Ticket> mapMany(ResultSet rs) throws SQLException {
        var tickets = new ArrayList<Ticket>();

        while (rs.next()) {
            tickets.add(mapRow(rs));
        }

        return tickets;
    }

    private Ticket mapRow(ResultSet rs) throws SQLException {
        var ticket = new Ticket();

        ticket.setId(rs.getLong("id"));
        ticket.setDepartureAirport(rs.getString("departure_airport"));
        ticket.setArrivalAirport(rs.getString("arrival_airport"));
        ticket.setPassengerId(rs.getLong("passenger_id"));

        ticket.setDepartureDate(rs.getTimestamp("departure_date").toLocalDateTime());
        ticket.setArrivalDate(rs.getTimestamp("arrival_date").toLocalDateTime());
        ticket.setPurchaseDate(rs.getTimestamp("purchase_date").toLocalDateTime());

        return ticket;
    }
}
