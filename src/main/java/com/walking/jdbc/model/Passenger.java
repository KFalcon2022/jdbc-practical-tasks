package com.walking.jdbc.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Passenger {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private boolean male;
    private LocalDateTime lastPurchase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public LocalDateTime getLastPurchase() {
        return lastPurchase;
    }

    public void setLastPurchase(LocalDateTime lastPurchase) {
        this.lastPurchase = lastPurchase;
    }
}
