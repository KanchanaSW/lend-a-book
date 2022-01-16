package com.system.Integration.Controller;

import com.system.Integration.CSV.Reader;
import com.system.Integration.CSV.ReaderMovie;
import com.system.models.Book;
import com.system.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/csv/")
public class CSVController {

    @Autowired
    private Reader reader;
    @Autowired
    private ReaderMovie readerMovie;

    @RequestMapping(method = RequestMethod.GET, value = "/csvBooks")
    public List<Book> bookListNew(){
        return reader.csvReadNew();
    }
    @RequestMapping(method = RequestMethod.GET, value = "/csvBooksExists")
    public List<Book> bookListExits(){
        return reader.csvReadExists();
    }
    @RequestMapping(method = RequestMethod.GET, value = "/csvBook/{bookId}")
    public Book csvBook(@PathVariable Integer bookId){
        return reader.csvReadById(bookId);
    }
    //movies
    @RequestMapping(method = RequestMethod.GET, value = "/csvMovies")
    public List<Movie> movieListNew(){
        return readerMovie.csvReadMovieNew();
    }
    @RequestMapping(method = RequestMethod.GET, value = "/csvMoviesExists")
    public List<Movie> movieListExists(){
        return readerMovie.csvReadMovieExists();
    }
    @RequestMapping(method = RequestMethod.GET, value = "/csvMovies/{movieId}")
    public Movie csvMove(@PathVariable Integer movieId){
        return readerMovie.csvReadMovieFindById(movieId);
    }

}
