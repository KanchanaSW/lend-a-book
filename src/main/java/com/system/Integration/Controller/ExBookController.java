package com.system.Integration.Controller;

import com.system.Integration.Models.ExBook;
import com.system.Integration.Models.ExMovie;
import com.system.Integration.Services.ExService;
import com.system.models.Book;
import com.system.models.Movie;
import com.system.repository.BookRepository;
import com.system.repository.MovieRepository;
import com.system.security.services.BookService;
import com.system.security.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/external/")
public class ExBookController {

    @Autowired
    private ExService exService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieRepository movieRepository;

    //BOOKS
    @GetMapping(value = "/books")
    public List<ExBook> exBooks() {
        return exService.all();
    }

    @GetMapping(value = "books/{title}")
    public List<ExBook> searchByTitle(@PathVariable(name = "title") String title) {
        return exService.seachBook(title);
    }

    @GetMapping(value = "findBook/{id}")
    public ExBook find(@PathVariable(name = "id")Integer id){
        return exService.find(id);
    }

    @GetMapping(value = "book/{id}")
    public ResponseEntity<?> addBook(@PathVariable(name = "id") Integer id) {
        System.out.println(id);
        if (exService.exists(id)) {
            ExBook exBook = exService.find(id);
            if (bookService.existsIsbn(exBook.getIsbn())) {
                return ResponseEntity.badRequest().body("existsIsbn");
            } else {
                Book book = new Book();
                book.setIsbn(exBook.getIsbn());
                book.setTitle(exBook.getTitle());
                book.setAuthor(exBook.getAuthor());
                book.setPublisher(exBook.getPublisher());
                book.setStatus(exBook.getStatus());
                book.setCoverPage(exBook.getCoverPage());
                book.setSummary(exBook.getSummary());
                book.setNoOfCopies(exBook.getNoOfCopies());
                bookRepository.save(book);
                return ResponseEntity.ok("success");
            }
        } else {
            return ResponseEntity.badRequest().body("dontExists");
        }

    }

    //MOVIES
    @GetMapping(value = "/movies")
    public List<ExMovie> exMovies() {
        return exService.allMovie();
    }

    @GetMapping(value = "movies/{title}")
    public List<ExMovie> seByTitleMovie(@PathVariable(name = "title") String title) {
        return exService.searchMovie(title);
    }

    @GetMapping(value = "movie/{id}")
    public ResponseEntity<?> addMovie(@PathVariable(name = "id") Integer id) {
        if (exService.existsMovie(id)) {
            ExMovie exMovie = exService.findMovie(id);
            if (movieService.existsByTitle(exMovie.getTitle())) {
                return ResponseEntity.badRequest().body("existsTitle");
            } else {
                Movie m = new Movie();
                m.setTitle(exMovie.getTitle());
                m.setStatus(exMovie.getStatus());
                m.setLength(exMovie.getLength());
                m.setImage(exMovie.getImage());
                m.setR18(exMovie.isR18());
                m.setDescription(exMovie.getDescription());
                m.setNoOfCopies(exMovie.getNoOfCopies());
                movieRepository.save(m);
                return ResponseEntity.ok("success");
            }
        } else {
            return ResponseEntity.badRequest().body("dontExists");
        }
    }
}
