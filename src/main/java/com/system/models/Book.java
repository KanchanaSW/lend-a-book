package com.system.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "Books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    public Long isbn;
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    private String publisher;
    @NotNull
    private String status;
    private String coverPage;
    @Column(columnDefinition = "text")
    private String summary;

    /*
    @OneToOne(targetEntity = Quantity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "noOfCopies")
    private Quantity quantity;*/

}
