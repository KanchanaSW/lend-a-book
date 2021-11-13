package com.system.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;
    private Date issueDate;
    private Date expectedReturnDate;
    private String status;
    private double totalAmount;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "users", referencedColumnName = "id")
    private User user;

    @OneToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation",referencedColumnName = "reservationId")
    private List<ReservationBook> reservationBooks;

    @Transient
    private List<Book> booksList;

    @Transient
    private Integer[] list;

   // @Transient
   // private List<Movie> moviesList;

}
