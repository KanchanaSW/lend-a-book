package com.system.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Quantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quantityId;
    private Integer noOfCopies;

    @OneToOne(targetEntity = Book.class, fetch = FetchType.EAGER)
    @JoinColumn( name = "id")
    private Book book;

    @OneToOne(targetEntity = Movie.class, fetch = FetchType.EAGER)
    @JoinColumn( name = "movieId")
    private Movie movie;
}
