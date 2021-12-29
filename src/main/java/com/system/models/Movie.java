package com.system.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;
    @NotNull
    private String title;
    private String status;
    private String length;
    private String image;
    private boolean r18;
    @Column(columnDefinition = "text")
    private String description;
    private Integer noOfCopies;
}
