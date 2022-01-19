package com.system.Integration.Repository;

import com.system.Integration.Models.ExBook;
import com.system.Integration.Models.ExMovie;
import com.system.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExMovieRepo extends JpaRepository<ExMovie,Integer> {

    @Query("FROM ExMovie em where em.title like %:title%")
    List<ExMovie> searchByTitle(String title);
}
