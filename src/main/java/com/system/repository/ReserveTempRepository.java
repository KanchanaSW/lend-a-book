package com.system.repository;

import com.system.models.ReserveTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveTempRepository extends JpaRepository<ReserveTemp,Long> {
    List<ReserveTemp> findByUserId(Long userId);
    void deleteByUserId(Long userId);
    ReserveTemp findByUserIdAndBookId(Long userId,Integer bookId);
    ReserveTemp findByUserIdAndMovieId(Long userId,Integer movieId);
}
