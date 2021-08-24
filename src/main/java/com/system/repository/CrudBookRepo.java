package com.system.repository;

import com.system.models.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CrudBookRepo extends CrudRepository<Book, Long> {

    @Override
    List<Book> findAll();
    Optional<Book> findByIsbn(long isbn);
    List<Book> findByTitle(String title);
    Boolean existsByTitle(String title);

}
