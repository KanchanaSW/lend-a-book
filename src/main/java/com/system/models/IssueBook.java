package com.system.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class IssueBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int issueBookId;
    //private boolean returned;
    private Date startDate;
    private Date endDate;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "issue", referencedColumnName = "issueId")
    private Issue issue;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "book", referencedColumnName = "isbn")
    private Book book;
/*
    public IssueBook() {
    }

    public IssueBook(Date startDate, Date endDate, Issue issue, Book book) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.issue = issue;
        this.book = book;
    }

    public int getIssueBookId() {
        return issueBookId;
    }

    public void setIssueBookId(int issueBookId) {
        this.issueBookId = issueBookId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }*/
}
