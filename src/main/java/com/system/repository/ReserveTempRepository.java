package com.system.repository;

import com.system.models.Book;
import com.system.models.Movie;
import com.system.models.ReserveTemp;
import com.system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveTempRepository extends JpaRepository<ReserveTemp,Long> {
    List<ReserveTemp> findByUser(User user);
    void deleteByUser(User user);
    ReserveTemp findByUserAndBook(User user, Book book);
    ReserveTemp findByUserAndMovie(User user, Movie movie);
}
