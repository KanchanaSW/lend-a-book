package com.system.repository;

import com.system.models.Book;
import com.system.models.Issue;
import com.system.models.IssuedBook;
import com.system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface IssuedBookRepository extends JpaRepository<IssuedBook,Long> {
    Long countByBookAndReturned(Book book,Integer returned);
    Long countIssuedBooksByIssueAndReturned(Issue issue,Integer returned);
    IssuedBook findByReturnedAndIssue(Integer returned,Issue issues);
    List<IssuedBook> findByIssueAndReturned(Issue issue,Integer returned);
    boolean existsByIssue(Issue issue);
    boolean existsByIssuedBookIdAndBook(Long issuedBookId,Book book);
}












