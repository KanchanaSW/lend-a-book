package com.system.Integration.Models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "ExMovies")
public class ExMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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
