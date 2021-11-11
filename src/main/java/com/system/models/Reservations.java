package com.system.models;

import javax.persistence.*;

@Entity
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reserveId;
    private String reserveDate;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "users",referencedColumnName = "id")
    private User user;

    public Reservations() {
    }

    public Reservations(String reserveDate, User user) {
        this.reserveDate = reserveDate;
        this.user = user;
    }

    public int getReserveId() {
        return reserveId;
    }

    public void setReserveId(int reserveId) {
        this.reserveId = reserveId;
    }

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
