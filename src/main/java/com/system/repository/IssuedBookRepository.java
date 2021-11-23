package com.system.repository;

import com.system.models.Book;
import com.system.models.IssuedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface IssuedBookRepository extends JpaRepository<IssuedBook,Long> {
    Long countByBookAndReturned(Book book,Integer returned);
}
