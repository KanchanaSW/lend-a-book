package com.system.repository;

import com.system.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    Optional<Movie> findById(Long movieId);
    boolean existsById(Long movieId);
    boolean existsByTitle(String title);

    List<Movie> findByR18(boolean r18);

    Movie findByTitle(String title);
}
