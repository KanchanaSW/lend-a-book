package com.system.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class ReserveTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reserveId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "users", referencedColumnName = "id")
    private User user;

    @OneToOne(targetEntity = Book.class,cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "books",referencedColumnName = "id")
    private Book book;

    @OneToOne(targetEntity = Movie.class,cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "movies",referencedColumnName = "movieId")
    private Movie movie;


}
