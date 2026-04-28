package com.example.demo.model;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String dni;
    private String name;
    private String lastName;
    private Date startedDate;
    private Date endedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    public Date getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(Date endedDate) {
        this.endedDate = endedDate;
    }

    @PrePersist
    public void assignDates() {
        this.startedDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.startedDate);
        calendar.add(Calendar.MONTH, 1);
        this.endedDate = calendar.getTime();
    }
}
