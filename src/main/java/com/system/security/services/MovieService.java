package com.system.security.services;

import com.system.DTO.MovieDTO;
import com.system.models.Movie;
import com.system.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public boolean existsByTitle(String title){
        return movieRepository.existsByTitle(title);
    }
    public boolean existsById(Long movieId){
        return movieRepository.existsById(movieId);
    }
    public int movieCopiesAvailable(Long movieId){
        Movie movie=movieRepository.getById(movieId);
        return movie.getCopiesAvailable();
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
            m.setWriter(movieDTO.getWriter());
            m.setDirecter(movieDTO.getDirecter());
            m.setCopiesAvailable(movieDTO.getCopiesAvailable());
            m.setImage(movieDTO.getImage());
            m.setR18(movieDTO.isR18());

            movieRepository.save(m);
            return m;
        }catch (Exception e){
            return null;
        }
    }
    public Movie updateMovie(MovieDTO movieDTO){
        try{
            Movie m=movieRepository.getById(movieDTO.getMovieId());
            m.setTitle(movieDTO.getTitle());
            m.setWriter(movieDTO.getWriter());
            m.setDirecter(movieDTO.getDirecter());
            m.setCopiesAvailable(movieDTO.getCopiesAvailable());
            m.setImage(movieDTO.getImage());
            m.setR18(movieDTO.isR18());

            movieRepository.save(m);
            return m;
        }catch (Exception e){
            return null;
        }
    }
    public Movie updateMovieCopies(long movieId,int copies){
        try{
            Movie m=movieRepository.getById(movieId);
            m.setCopiesAvailable(copies);
            movieRepository.save(m);
            return m;
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
