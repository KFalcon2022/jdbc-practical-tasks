package com.walking.jdbc.mapper;

import com.walking.jdbc.model.Passenger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PassengerMapper {

    public Optional<Passenger> map(ResultSet rs) throws SQLException {
        return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
    }

    public List<Passenger> mapMany(ResultSet rs) throws SQLException {
        var passengers = new ArrayList<Passenger>();

        while (rs.next()) {
            passengers.add(mapRow(rs));
        }

        return passengers;
    }

    private Passenger mapRow(ResultSet rs) throws SQLException {
        var passenger = new Passenger();

        passenger.setId(rs.getLong("id"));
        passenger.setFirstName(rs.getString("first_name"));
        passenger.setLastName(rs.getString("last_name"));
        passenger.setMale(rs.getBoolean("male"));
        passenger.setBirthDate(rs.getDate("birth_date").toLocalDate());

        var lastPurchase = rs.getTimestamp("last_purchase");
        passenger.setLastPurchase(lastPurchase == null ? null : lastPurchase.toLocalDateTime());

        return passenger;
    }
}
