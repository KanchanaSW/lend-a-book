package com.system.repository;

import com.system.models.Movie;
import com.system.models.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityRepository extends JpaRepository<Quantity,Integer> {
    Quantity findByMovieMovieId(Long movieId);
    Quantity findByBookId(Integer id);
}
