package com.system.repository;

import com.system.models.Book;
import com.system.models.Issue;
import com.system.models.IssueBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IssueBookRepository extends JpaRepository<IssueBook,Integer> {
    //Integer countByBookAndReturned(Book book,boolean returned);
    List<IssueBook> findByBookAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Book book, Date endDate,Date startDate);
    boolean existsByBook(Book book);
    boolean existsByIssue(Issue issueId);
    IssueBook findByIssue(Issue issueId);
}
