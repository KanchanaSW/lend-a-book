package com.system.security.services;

import com.system.DTO.MovieDTO;
import com.system.models.Book;
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

    public boolean existsByTitle(String title) {
        return movieRepository.existsByTitle(title);
    }

    public boolean existsById(Integer movieId) {
        return movieRepository.existsById(movieId);
    }


    //list all movies
    public List<Movie> getAllMovies() {
        List<Movie> list = new ArrayList<>();
        movieRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    //list all movies by title
    public List<Movie> getAllByTitle(String title) {
        List<Movie> list = new ArrayList<>();
        movieRepository.findAllByTitle(title).forEach(e -> list.add(e));
        return list;
    }

    //list all distint movies
    public List<Movie> getAllDistintMovie() {
        List<Movie> list = new ArrayList<>();
        movieRepository.findAll().forEach(e -> {
            if (!list.contains(e)) {
                list.add(e);
            }
        });
        return list;
    }

    //list 18+ movies
    public List<Movie> getRRatedMovies() {
        List<Movie> list = new ArrayList<>();
        movieRepository.findByR18(true).forEach(e -> list.add(e));
        return list;
    }

    public Movie addMovie(MovieDTO movieDTO) {
        try {
            Movie m = new Movie();
            m.setTitle(movieDTO.getTitle());
            m.setStatus(movieDTO.getStatus());
            m.setLength(movieDTO.getLength());
            m.setImage(movieDTO.getImage());
            m.setR18(movieDTO.isR18());
            m.setDescription(movieDTO.getDescription());
            m.setNoOfCopies(movieDTO.getNoOfCopies());
            movieRepository.save(m);

            return m;
        } catch (Exception e) {
            return null;
        }
    }

    public Movie updateMovie(MovieDTO movieDTO, Integer movieId) {
        try {
            Movie m = movieRepository.getById(movieId);
            m.setTitle(movieDTO.getTitle());
            m.setStatus(movieDTO.getStatus());
            m.setLength(movieDTO.getLength());
            m.setImage(movieDTO.getImage());
            m.setR18(movieDTO.isR18());
            m.setDescription(movieDTO.getDescription());
            m.setNoOfCopies(movieDTO.getNoOfCopies());
            movieRepository.save(m);

            return m;
        } catch (Exception e) {
            return null;
        }
    }
    public Movie updateMovieCopies(MovieDTO movieDTO, Integer movieId) {
        try {
            Movie m = movieRepository.getById(movieId);
            m.setNoOfCopies(m.getNoOfCopies()+movieDTO.getNoOfCopies());
            movieRepository.save(m);

            return m;
        } catch (Exception e) {
            return null;
        }
    }
    /*    public Book findBook(Integer id){
        Optional<Book> book=bookRepository.findById(id);
        Book b=null;
        if (book.isPresent()){
            b=book.get();
        }
        return b;
    }*/

    public Movie findMovie(Integer movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        Movie m = null;
        if (movie.isPresent()) {
            m = movie.get();
        }
        return m;
    }

    public Movie findMovieByID(Integer movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        Movie m = null;
        if (movie.isPresent()) {
            m = movie.get();
        }
        return m;
    }

    public Movie findMovieByTitle(String title) {
        Movie movie = movieRepository.findByTitle(title);
        return movie;
    }

    public void deleteMovie(Integer movieId) {
        movieRepository.deleteById(movieId);
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

}
