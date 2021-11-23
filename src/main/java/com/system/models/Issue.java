package com.system.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;
    private Date issueDate;
    private String notes;
    private Date expectedReturnDate;
    private Integer returned;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "users", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    private List<IssuedBook> issuedBooks;

    @Transient
   private List<Book> booksList;

    @Transient
    private Integer[] list;
}
