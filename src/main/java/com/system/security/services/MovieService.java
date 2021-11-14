package com.system.security.services;

import com.system.DTO.MovieDTO;
import com.system.models.Movie;
import com.system.models.Quantity;
import com.system.repository.MovieRepository;
import com.system.repository.QuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private QuantityRepository quantityRepository;

    public boolean existsByTitle(String title){
        return movieRepository.existsByTitle(title);
    }
    public boolean existsById(Long movieId){
        return movieRepository.existsById(movieId);
    }


    //list all movies
    public List<Movie> getAllMovies(){
        List<Movie> list=new ArrayList<>();
        movieRepository.findAll().forEach(e->list.add(e));
        return list;
    }
    //list 18+ movies
    public List<Movie> getRRatedMovies(){
        List<Movie> list=new ArrayList<>();
        movieRepository.findByR18(true).forEach(e->list.add(e));
        return list;
    }
    public Movie addMovie(MovieDTO movieDTO){
        try{
            Movie m=new Movie();
            m.setTitle(movieDTO.getTitle());
            m.setStatus(movieDTO.getStatus());
            m.setWriter(movieDTO.getWriter());
            m.setImage(movieDTO.getImage());
            m.setR18(movieDTO.isR18());
            m.setDescription(movieDTO.getDescription());
            movieRepository.save(m);
            //set quantity
            Movie movie=movieRepository.findByTitle(movieDTO.getTitle());
            Quantity quantity=new Quantity();
            quantity.setNoOfCopies(movieDTO.getNoOfCopies());
            quantity.setMovie(movie);
            quantityRepository.save(quantity);
            return m;
        }catch (Exception e){
            return null;
        }
    }
    public Movie updateMovie(MovieDTO movieDTO){
        try{
            Movie m=movieRepository.getById(movieDTO.getMovieId());
            m.setTitle(movieDTO.getTitle());
            m.setStatus(movieDTO.getStatus());
            m.setWriter(movieDTO.getWriter());
            m.setImage(movieDTO.getImage());
            m.setR18(movieDTO.isR18());
            m.setDescription(movieDTO.getDescription());
            movieRepository.save(m);
            Quantity quantity=quantityRepository.findByMovieMovieId(m.getMovieId());
            quantity.setNoOfCopies(movieDTO.getNoOfCopies());
            quantity.setMovie(m);
            quantityRepository.save(quantity);
            return m;
        }catch (Exception e){
            return null;
        }
    }
    public Quantity updateMovieQuantity(Long movieId, Integer copies){
        try{
            //Movie m=movieRepository.getById(movieId);
            Quantity q=quantityRepository.findByMovieMovieId(movieId);
            Quantity quantity=new Quantity();
            quantity.setNoOfCopies(copies);
            quantityRepository.save(q);
            return q;
        }catch (Exception e){
            return null;
        }
    }
    public Movie findMovieByID(long movieId){
        Optional<Movie> movie=movieRepository.findById(movieId);
        Movie m=null;
        if (movie.isPresent()){
            m=movie.get();
        }
        return m;
    }
    public Movie findMovieByTitle(String title){
        Movie movie=movieRepository.findByTitle(title);
        return movie;
    }
    public void deleteMovie(long movieId){
        movieRepository.deleteById(movieId);
    }


}
