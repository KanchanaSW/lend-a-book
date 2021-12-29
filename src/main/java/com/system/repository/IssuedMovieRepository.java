package com.system.repository;

import com.system.models.Issue;
import com.system.models.IssuedBook;
import com.system.models.IssuedMovie;
import com.system.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuedMovieRepository extends JpaRepository<IssuedMovie,Long> {
    Long countByMovieAndReturned(Movie movie,Integer returned);
    int countIssuedMovieByIssueAndReturned(Issue issue, Integer returned);
    IssuedMovie findByReturnedAndIssue(Integer returned,Issue issue);
    List<IssuedMovie> findByIssueAndReturned(Issue issue,Integer returned);
    boolean existsByIssue(Issue issue);
}
