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
    public ExBook find(Integer id){return exBookRepo.findById(id).get();}
    public boolean exists(Integer id){return exBookRepo.existsById(id);}
   // public void delete(ExBook exBook){ exBookRepo.delete(exBook);}

    //movies
    public List<ExMovie> allMovie() {
        return exMovieRepo.findAll();
    }
    public List<ExMovie> searchMovie(String movie){
        return exMovieRepo.searchByTitle(movie);
    }
    public ExMovie findMovie(Integer id){return exMovieRepo.findById(id).get();}
    public boolean existsMovie(Integer id){return exMovieRepo.existsById(id);}
  //  public void deleteMovie(ExMovie exMovie){ exMovieRepo.delete(exMovie);}
}
