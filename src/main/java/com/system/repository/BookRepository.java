package com.system.repository;

import com.system.models.Book;
import com.system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findById(Integer id);
    boolean existsById(Integer id);
    boolean existsByIsbn(Long isbn);
    boolean existsByTitle(String title);
    boolean existsByAuthor(String author);
    List<Book> findByAuthor(String author);
    List<Book> findAllByTitle(String title);

    //@Query("SELECT b FROM Books b WHERE b.title like ?1%")
    //Book findBooksByTitle(String title);
    Book findByTitle(String title);
    Book findByIsbn(Long isbn);

    @Query("FROM Book u where u.title like %:title%")
    List<Book> name(String title);
}
