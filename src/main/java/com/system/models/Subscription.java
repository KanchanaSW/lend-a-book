package com.system.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;
    private String type;
    private double membershipFee;

    private Integer noOfBooks;
    private Integer durationBooks;
    private double chargesBooks;

    private Integer noOfMovies;
    private Integer durationMovies;
    private double chargesMovies;

    private double overDueCharges;
}
