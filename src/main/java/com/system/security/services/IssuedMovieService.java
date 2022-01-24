package com.system.security.services;

import com.system.models.Issue;
import com.system.models.IssuedMovie;
import com.system.repository.IssuedMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssuedMovieService {

    IssuedMovieRepository issuedMovieRepository;

    @Autowired
    public IssuedMovieService(IssuedMovieRepository issuedMovieRepository) {
        this.issuedMovieRepository = issuedMovieRepository;
    }

    public IssuedMovie get(Long movieId) {
        return issuedMovieRepository.findById(movieId).get();
    }

    public List<IssuedMovie> getAll(){return issuedMovieRepository.findAll();}

    public IssuedMovie save(IssuedMovie issuedMovie) {
        return issuedMovieRepository.save(issuedMovie);
    }

    public IssuedMovie addNew(IssuedMovie issuedMovie) {
        issuedMovie.setReturned(0);
        return issuedMovieRepository.save(issuedMovie);
    }

    public int countMoviesByIssueNotReturned(Issue issue) {
        return issuedMovieRepository.countIssuedMovieByIssueAndReturned(issue, 0);
    }

    public List<IssuedMovie> findIssuedMoviesByIssueNotReturned(Issue issue) {
        return issuedMovieRepository.findByIssueAndReturned(issue, 0);
    }

    public List<IssuedMovie> findIssuedMoviesByIssueR(Issue issue) {
        return issuedMovieRepository.findByIssueAndReturned(issue, 1);
    }
}
