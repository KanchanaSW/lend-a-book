package com.system.security.services;

import com.system.models.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ImplBookService {

    List<Book> getALl();
    Boolean existsBookISBN(long isbn);
    void addBook(Book book);
   Optional<Book> findByIsbn(long isbn);
   List<Book> findByTitle(String title);

    Boolean existsBookTitle(String title);

    void updateBook(Book book);

    void deleteBook(long isbn);
    long countAll();
}
