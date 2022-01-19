package com.system.Integration.Services;

import com.system.Integration.Models.ExBook;
import com.system.Integration.Models.ExMovie;
import com.system.Integration.Repository.ExBookRepo;
import com.system.Integration.Repository.ExMovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExService {
    @Autowired
    private ExBookRepo exBookRepo;
    @Autowired
    private ExMovieRepo exMovieRepo;

    //books
    public List<ExBook> all() {
        return exBookRepo.findAll();
    }

    public List<ExBook> seachBook(String title){
        return exBookRepo.searchByTitle(title);
    }


    //movies
    public List<ExMovie> allMovie() {
        return exMovieRepo.findAll();
    }
    public List<ExMovie> searchMovie(String movie){
        return exMovieRepo.searchByTitle(movie);
    }
}
