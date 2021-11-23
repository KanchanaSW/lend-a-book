package com.system.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class IssuedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issuedBookId;
    private Integer returned;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "books", referencedColumnName = "id")
    private Book book;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "issue", referencedColumnName = "issueId")
    @JsonIgnore
    private Issue issue;
}
