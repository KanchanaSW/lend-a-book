package com.system.controllers;

import com.system.DTO.BookDTO;
import com.system.DTO.MovieDTO;
import com.system.models.*;
import com.system.payload.response.MessageResponse;
import com.system.repository.UserRepository;
import com.system.security.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private BookService bookService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private IssuedBookService issuedBookService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //  ****************************************** Book Functions*******************************************************
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //add book
    @RequestMapping(value = "/addBook")
    public ResponseEntity<?> addBook(@RequestBody BookDTO newBook) {
        try {
            if (bookService.existsIsbn(newBook.getIsbn())) {
                return ResponseEntity.badRequest().body(
                        new MessageResponse("Error : Book already exists with the same ISBN"));
            } else if (bookService.existsTitle(newBook.getTitle())) {
                return ResponseEntity.badRequest().body(
                        new MessageResponse("Error : Book already exists with the same Title"));
            } else {
                bookService.addBook(newBook);
                return ResponseEntity.ok(new MessageResponse("Success : Book added!!!"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("error occcured" + e));
        }
    }

    // view all books
    @RequestMapping(method = RequestMethod.GET, value = "/books")
    public ResponseEntity<?> getAllBookList() {
        try {
            List<Book> list = bookService.getALlBooks();
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("error occcured get all books" + e));
        }
    }

    //count all books
    @RequestMapping(method = RequestMethod.GET, value = "/books/count")
    public ResponseEntity<?> countAllbooks() {
        long x = bookService.countAllBooks();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Count is : " + x);
    }

    //get single book details
    @RequestMapping(method = RequestMethod.GET, value = "/book/{id}")
    public ResponseEntity<?> getBookDetails(@PathVariable Integer id) {
        if (bookService.existsId(id)) {
            Book book = bookService.findBook(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
        } else {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Error : book don't exists with the Id : " + id)
            );
        }
    }

    //get book by title.
    @RequestMapping(method = RequestMethod.GET, value = "/bookTitle/{title}")
    public ResponseEntity<?> searchBookByTitle(@PathVariable String title) {
        if (bookService.existsTitle(title)) {
            Book book = bookService.findBookTitle(title);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
        } else {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Error : book don't exists with the title : " + title)
            );
        }
    }

    //update book details here.
    @RequestMapping(method = RequestMethod.PUT, value = "/updateBook/{id}")
    public ResponseEntity<?> updateBook(@Valid @PathVariable Integer id, @RequestBody BookDTO book) {
        try {
            if (bookService.existsIsbn(book.getIsbn())) {
                bookService.updateBook(book);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Error : book dont exists with isbn :" + book.getIsbn())
                );
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("error occcured" + e));
        }
    }

    //delete book
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteBook/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer id) {
        if (bookService.existsId(id)) {
            bookService.deleteBook(id);
            return ResponseEntity.ok(new MessageResponse("Success: Book deleted!!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error : no book with this isbn : " + id));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //********************************************Movie Functions*************************************************************************
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //add new Movie
    @RequestMapping(value = "/addMovie")
    public ResponseEntity<?> addMovie(@RequestBody MovieDTO movieDTO) {
        try {
            if (movieService.existsByTitle(movieDTO.getTitle())) {
                return ResponseEntity.badRequest().body(
                        new MessageResponse("Error : Movie already exists with the same Title"));
            } else {
                movieService.addMovie(movieDTO);
                return ResponseEntity.ok(new MessageResponse("Success : Movie added!!!"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("error occcured" + e));
        }
    }

    //view al movies
    @RequestMapping(method = RequestMethod.GET, value = "/movies")
    public ResponseEntity<?> getAllMovieList() {
        try {
            List<Movie> list = movieService.getAllMovies();
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("error occcured get all Movies" + e));
        }
    }

    //get single book details
    @RequestMapping(method = RequestMethod.GET, value = "/movie/{movieId}")
    public ResponseEntity<?> getMovieDetails(@PathVariable long movieId) {
        if (movieService.existsById(movieId)) {
            Movie movie = movieService.findMovieByID(movieId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(movie);
        } else {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Error : movie don't exists with the movieId : " + movieId)
            );
        }
    }

    //get single book details by title
    @RequestMapping(method = RequestMethod.GET, value = "/movieByTitle/{title}")
    public ResponseEntity<?> getMovieDetailsByTitle(@PathVariable String title) {
        if (movieService.existsByTitle(title)) {
            Movie movie = movieService.findMovieByTitle(title);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(movie);
        } else {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Error : movie don't exists with the title : " + title)
            );
        }
    }

    //update movie details here.
    @RequestMapping(method = RequestMethod.PUT, value = "/updateMovie/{movieId}")
    public ResponseEntity<?> updateMovie(@Valid @PathVariable long movieId, @RequestBody MovieDTO movieDTO) {
        try {
            if (movieService.existsById(movieId)) {
                movieService.updateMovie(movieDTO, movieId);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(movieDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Error : Movie don't exists with MovieId :" + movieDTO.getMovieId())
                );
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("error occcured" + e));
        }
    }

    //delete Movie
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteMovie/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable long movieId) {
        if (movieService.existsById(movieId)) {
            movieService.deleteMovie(movieId);
            return ResponseEntity.ok(new MessageResponse("Success: Movie deleted!!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error : no Movie with this movieId : " + movieId));
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //---------------------------------------------Book Reservation Functions***********************************************
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(method = RequestMethod.POST, value = "/addReservation")
    public ResponseEntity<?> saveReservation(@RequestBody Reservation reservation, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return reservationService.saveReservation(reservation, userDetails.getEmail(), reservation.getTotalAmount());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allUserReservation")
    public List<Reservation> viewAllUserReservation(@RequestBody Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return reservationService.getAllReservation(userDetails.getId());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allReservation")
    public List<Reservation> viewAllReservation() {
        return reservationService.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/viewReservation/{reservationId}")
    public Reservation viewAReservation(@PathVariable Integer reservationId) {
        return reservationService.getByReservationId(reservationId);
    }

    //update not working
    @RequestMapping(method = RequestMethod.PUT, value = "updateReservation/{reservationId}")
    public ResponseEntity<?> updateReservation(@PathVariable Integer reservationId, @RequestBody String status) {
        return reservationService.updateStatusReservation(reservationId, status);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////        Issue Controller         ////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(value = "/saveIssue")
    public ResponseEntity<?> saveIssue(@RequestBody Issue issue, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String email = userDetails.getEmail();
          //  System.out.println(email);
            User user = userService.directUserType(email);//get user
            Integer[] list;
            list = issue.getList();

            Issue issue1 = new Issue();
            issue1.setUser(user);//user added
            issue1 = issueService.addNew(issue1);
           // System.out.println(books.size());
            for (int l = 0; l < list.length; l++) {
                Book book = bookService.findBook(list[l]);
                if (book.getStatus().equals("Available")) {
                    Integer copies = book.getNoOfCopies();
                    book.setNoOfCopies(copies - 1);
                    book = bookService.save(book);

                    IssuedBook ib = new IssuedBook();
                    ib.setBook(book);
                    ib.setIssue(issue1);
                    issuedBookService.addNew(ib);
                } else {
                    return ResponseEntity.badRequest().body(new MessageResponse("book is un-available"));
                }
            }
             return ResponseEntity.ok().body(new MessageResponse("Success"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("error occcured" + e));
        }
    }

    @RequestMapping(value = "/returnAllBooks/{issueId}",method = RequestMethod.GET)
    public ResponseEntity<?> returnAllBooks(@PathVariable Long issueId){
        Issue issue=issueService.get(issueId);
        if(issue != null){
            List<IssuedBook> issuedBooks=issue.getIssuedBooks();
            for (int k=0;k<issuedBooks.size();k++){
                IssuedBook ib=issuedBooks.get(k);
                ib.setReturned(1);
                issuedBookService.save(ib);

                Book book=ib.getBook();
                Integer copies = book.getNoOfCopies();
                book.setNoOfCopies(copies+1);
                bookService.save(book);
            }
            issue.setReturned(1);
            issueService.save(issue);
            return ResponseEntity.ok().body(new MessageResponse("successfull"));
        }else {
            return ResponseEntity.ok().body(new MessageResponse("unSuccessfull"));
        }
    }

    @RequestMapping(value = "/returnBook/{issueId}/{id}")
    public ResponseEntity<?> returnBook(@PathVariable Long issueId,@PathVariable Integer id){
        Issue issue=issueService.get(issueId);
        if(issue != null){
            List<IssuedBook> issuedBooks=issue.getIssuedBooks();

            for (int k=0;k<issuedBooks.size();k++){
                IssuedBook ib=issuedBooks.get(k);
                if (ib.getBook().getId().equals(id))
                    ib.setReturned(1);
                    issuedBookService.save(ib);

                    Book book = ib.getBook();
                    Integer copies = book.getNoOfCopies();
                    book.setNoOfCopies(copies + 1);
                    bookService.save(book);
                return ResponseEntity.ok().body(new MessageResponse("successfull"));
                }
            return ResponseEntity.ok().body(new MessageResponse("success"));
            } else {
            return ResponseEntity.ok().body(new MessageResponse("unSuccessfull"));
        }
    }

    /*
    @RequestMapping(value = "/returnSelectedBooks/{issueId}")
    public ResponseEntity<?> returnSelectedBooks(@PathVariable Long issueId,@RequestBody Issue issue){
        Issue issue1=issueService.get(issueId);
        Integer[] list;
        list = issue.getList();
        List<Integer> bookList = new ArrayList<>();
        try {
            for (int k = 0; k < list.length; k++) {
                bookList.add(list[k]);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("error"));
        }

        List<Book> books = bookService.get(bookList);//books

        if(issue1 != null){
            List<IssuedBook> issuedBooks=issue.getIssuedBooks();
            for (int k=0;k<issuedBooks.size();k++) {
                IssuedBook ib = issuedBooks.get(k);
                if (books.contains(ib.getIssuedBookId())) {
                    ib.setReturned(1);
                    issuedBookService.save(ib);

                    Book book = ib.getBook();
                    Integer copies = book.getNoOfCopies();
                    book.setNoOfCopies(copies + 1);
                    bookService.save(book);
                }
            }
            return ResponseEntity.ok().body(new MessageResponse("successfull"));
        }else {
            return ResponseEntity.ok().body(new MessageResponse("unSuccessfull"));
        }
    }
*/
    //View all issued books


    // view my issued books - user functions
}