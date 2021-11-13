package com.system.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class ReservationBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationBookId;
    private Date startDate;
    private Date endDate;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation", referencedColumnName = "reservationId")
    @JsonIgnore
    private Reservation reservation;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "books", referencedColumnName = "id")
    private Book book;

}
