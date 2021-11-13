package com.system.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer issueId;
    private Date issueDate;
    private Date expectedReturnDate;
    private String status;
    private double totalAmount;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    @OneToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "issue",referencedColumnName = "issueId")
    private List<IssueBook> issueBooks;

    @Transient
    private List<Book> booksList;
    @Transient
    private Long[] list;
    @Transient
    private List<Movie> moviesList;
/*
    public Issue() {
    }

    public Issue(Date issueDate, Date expectedReturnDate, String status, double totalAmount, User user, List<IssueBook> issueBooks, List<Book> booksList, Long[] list, List<Movie> moviesList) {
        this.issueDate = issueDate;
        this.expectedReturnDate = expectedReturnDate;
        this.status=status;
        this.totalAmount = totalAmount;
        this.user = user;
        this.issueBooks = issueBooks;
        this.booksList = booksList;
        this.list = list;
        this.moviesList = moviesList;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(Date expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<IssueBook> getIssueBooks() {
        return issueBooks;
    }

    public void setIssueBooks(List<IssueBook> issueBooks) {
        this.issueBooks = issueBooks;
    }

    public List<Book> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<Book> booksList) {
        this.booksList = booksList;
    }

    public Long[] getList() {
        return list;
    }

    public void setList(Long[] list) {
        this.list = list;
    }

    public List<Movie> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }*/
}
