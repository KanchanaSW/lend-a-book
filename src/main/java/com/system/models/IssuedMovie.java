package com.system.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class IssuedMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issuedMovieId;
    private Integer returned;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "movie", referencedColumnName = "movieId")
    private Movie movie;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "issue", referencedColumnName = "issueId")
    private Issue issue;

    private Date startDate;
    private Date endDate;
    private double amount;
}
