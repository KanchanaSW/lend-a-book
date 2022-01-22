package com.system.repository;

import com.system.models.Book;
import com.system.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {
    Optional<Movie> findById(Integer movieId);
    boolean existsById(Integer movieId);
    boolean existsByTitle(String title);

    List<Movie> findByR18(boolean r18);

    Movie findByTitle(String title);
    List<Movie> findAllByTitle(String title);

    @Query("FROM Movie u where u.title like %:title%")
    List<Movie> name(String title);

}
