package com.system.controllers;

import com.system.DTO.BookDTO;
import com.system.DTO.MovieDTO;
import com.system.DTO.ReserveTempDTO;
import com.system.Integration.CSV.Reader;
import com.system.Integration.CSV.ReaderMovie;
import com.system.models.*;
import com.system.payload.response.MessageResponse;
import com.system.repository.UserRepository;
import com.system.security.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private BookService bookService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private IssuedBookService issuedBookService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReserveTempService reserveTempService;
    @Autowired
    private IssuedMovieService issuedMovieService;
    @Autowired
    private Reader reader;
    @Autowired
    private ReaderMovie readerMovie;



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //  ****************************************** Book Functions*******************************************************
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //add book
    @RequestMapping(value = "/addBook")
    public ResponseEntity<?> addBook(@RequestBody BookDTO newBook) {
        try {
            if (bookService.existsIsbn(newBook.getIsbn())) {
                return ResponseEntity.badRequest().body("existsIsbn");
            } else if (bookService.existsTitle(newBook.getTitle())) {
                return ResponseEntity.badRequest().body("existsTitle");
            } else {
                bookService.addBook(newBook);
                return ResponseEntity.ok("success");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");
        }
    }

    // view all books
    @RequestMapping(method = RequestMethod.GET, value = "/books")
    public ResponseEntity<?> getAllBookList() {
        try {
            List<Book> list = bookService.getALlBooks();
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");
        }
    }

    //count all books
    @RequestMapping(method = RequestMethod.GET, value = "/books/count")
    public ResponseEntity<?> countAllbooks() {
        long x = bookService.countAllBooks();
        return ResponseEntity.ok(x);
    }

    //get single book details
    @RequestMapping(method = RequestMethod.GET, value = "/book/{id}")
    public ResponseEntity<?> getBookDetails(@PathVariable Integer id) {
        if (bookService.existsId(id)) {
            Book book = bookService.findBook(id);
            return ResponseEntity.ok().body(book);
        } else {
            return ResponseEntity.badRequest().body("dontExist");
        }
    }

    //get book by title.
    @RequestMapping(method = RequestMethod.GET, value = "/bookTitle/{title}")
    public ResponseEntity<?> searchBookByTitle(@PathVariable String title) {
        if (bookService.existsTitle(title)) {
            Book book = bookService.findBookTitle(title);
            return ResponseEntity.ok().body(book);
        } else {
            return ResponseEntity.badRequest().body("dontExists");
        }
    }

    //update book details here.
    @RequestMapping(method = RequestMethod.PUT, value = "/updateBook/{id}")
    public ResponseEntity<?> updateBook(@Valid @PathVariable Integer id, @RequestBody BookDTO book) {
        try {
            if (bookService.existsIsbn(book.getIsbn())) {
                bookService.updateBook(book);
                return ResponseEntity.ok().body(book);
            } else {
                return ResponseEntity.badRequest().body("dontExists");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");
        }
    }
    //update book copies here.
    @RequestMapping(method = RequestMethod.PUT, value = "/updateBookCopies/{id}")
    public ResponseEntity<?> updateBookCopies(@Valid @PathVariable Integer id, @RequestBody BookDTO book) {
        try {
            if (bookService.existsIsbn(book.getIsbn())) {
                bookService.updateBookCopies(book);
                return ResponseEntity.ok().body(book);
            } else {
                return ResponseEntity.badRequest().body("dontExists");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");
        }
    }
    //csv function
    //add book
    @RequestMapping(value = "/addCSVBook")
    public ResponseEntity<?> addBookCSV(@RequestBody BookDTO newBook) {
        try {
            if (bookService.existsIsbn(newBook.getIsbn())) {
                return ResponseEntity.badRequest().body("existsIsbn");
            } else if (bookService.existsTitle(newBook.getTitle())) {
                return ResponseEntity.badRequest().body("existsTitle");
            } else {
                bookService.addBook(newBook);
                reader.removeLine(newBook.getTitle());
                return ResponseEntity.ok("success");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");
        }
    }

    //delete book
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteBook/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer id) {
       try{
           if (bookService.existsId(id)) {
               bookService.deleteBook(id);
               return ResponseEntity.ok("success");
           } else {
               return ResponseEntity.badRequest().body("dontExists");
           }
       }catch (Exception ex){
           return ResponseEntity.badRequest().body("issued");
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
                return ResponseEntity.badRequest().body("existsTitle");
            } else {
                movieService.addMovie(movieDTO);
                return ResponseEntity.ok("success");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");
        }
    }

    //view al movies
    @RequestMapping(method = RequestMethod.GET, value = "/movies")
    public ResponseEntity<?> getAllMovieList() {
        try {
            List<Movie> list = movieService.getAllMovies();
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");
        }
    }

    //get single book details
    @RequestMapping(method = RequestMethod.GET, value = "/movie/{movieId}")
    public ResponseEntity<?> getMovieDetails(@PathVariable Integer movieId) {
        if (movieService.existsById(movieId)) {
            Movie movie = movieService.findMovieByID(movieId);
            return ResponseEntity.ok().body(movie);
        } else {
            return ResponseEntity.badRequest().body("error");
        }
    }

    //get single book details by title
    @RequestMapping(method = RequestMethod.GET, value = "/movieByTitle/{title}")
    public ResponseEntity<?> getMovieDetailsByTitle(@PathVariable String title) {
        if (movieService.existsByTitle(title)) {
            Movie movie = movieService.findMovieByTitle(title);
            return ResponseEntity.ok().body(movie);
        } else {
            return ResponseEntity.badRequest().body("error");
        }
    }

    //update movie details here.
    @RequestMapping(method = RequestMethod.PUT, value = "/updateMovie/{movieId}")
    public ResponseEntity<?> updateMovie(@Valid @PathVariable Integer movieId, @RequestBody MovieDTO movieDTO) {
        try {
            if (movieService.existsById(movieId)) {
                movieService.updateMovie(movieDTO, movieId);
                return ResponseEntity.ok().body(movieDTO);
            } else {
                return ResponseEntity.badRequest().body("dontExists");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");
        }
    }

    //delete Movie
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteMovie/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable Integer movieId) {
       try{
           if (movieService.existsById(movieId)) {
               movieService.deleteMovie(movieId);
               return ResponseEntity.ok("success");
           } else {
               return ResponseEntity.badRequest().body("dontExists");
           }
       }catch (Exception ex){
           return ResponseEntity.badRequest().body("issued");
       }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////   Reserve Controller     /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/reserve")
    public ResponseEntity<?> saveReserve(@RequestBody ReserveTempDTO reserveTempDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Long userId= userDetails.getId();
        try{
            ReserveTemp rt= reserveTempService.save(reserveTempDTO,userId);
            return ResponseEntity.ok().body(rt);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("error");
        }
    }

    @RequestMapping(value = "/deleteReserve/{reserveId}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteResrve(@PathVariable Long reserveId){
        try{
            reserveTempService.delete(reserveId);
            return ResponseEntity.ok().body("Success");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("error");
        }
    }

    @RequestMapping(value = "/userReservesBooks",method = RequestMethod.GET)
    public ResponseEntity<?> findUserReserves(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Long userId= userDetails.getId();
        try{
            List<ReserveTemp> rts=reserveTempService.usersReservesBooks(userId);
            return ResponseEntity.ok().body(rts);
        }catch (Exception ex){
            System.out.println(ex);
            return ResponseEntity.badRequest().body("error");
        }
    }
    @RequestMapping(value = "/userReservesMovies",method = RequestMethod.GET)
    public ResponseEntity<?> findUserReservesMovies(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Long userId= userDetails.getId();
        try{
            List<ReserveTemp> rts=reserveTempService.usersReservesMovies(userId);
            return ResponseEntity.ok().body(rts);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("error");
        }
    }
    @RequestMapping(value = "/userBookReserve/{bookId}",method = RequestMethod.GET)
    public ResponseEntity<?> findUserBookReserves(@PathVariable Integer bookId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Long userId= userDetails.getId();
        try{
            ReserveTemp rti= reserveTempService.getReserve(bookId,userId);
            return ResponseEntity.ok().body(rti);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("error");
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////        Issue Controller         ////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "/addNewIssue")
    public ResponseEntity<?> saveSingleIssue(@RequestBody Issue issue) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        String email = userDetails.getEmail();

        return issueService.addSingleIssue(email, issue);
    }

    //working for both movie and books
    @RequestMapping(value = "/extendIssue/{issueId}", method = RequestMethod.GET)
    public ResponseEntity<?> extendBookIssue(@PathVariable Long issueId) {
        try{
            Issue issue1 = issueService.get(issueId);
            if (issue1 != null) {
                if (issueService.isBooks(issueId)){
                    System.out.println(issueService.isBooks(issueId));
                    return issueService.extendReturn(issue1);//function for books
                }else {
                    System.out.println("are not books");
                    return issueService.extendReturnMovie(issue1);
                }

            } else {
                return ResponseEntity.ok().body("error");
            }
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("error-ex");
        }

    }

    //working for both book amd movie
    @RequestMapping(value = "/returnAllIssues/{issueId}", method = RequestMethod.GET)
    public ResponseEntity<?> returnAllIssues(@PathVariable Long issueId) {
        Issue issue = issueService.get(issueId);
        if (issue != null) {
            if (issueService.isBooks(issueId)){
                System.out.println(issueService.isBooks(issueId));
                List<IssuedBook> issuedBooks = issue.getIssuedBooks();
                for (IssuedBook ib : issuedBooks) {
                    ib.setReturned(1);
                    issuedBookService.save(ib);

                    Book book = ib.getBook();
                    Integer copies = book.getNoOfCopies();
                    book.setNoOfCopies(copies + 1);
                    bookService.save(book);
                }
                issue.setReturned(1);
                issueService.save(issue);
                return ResponseEntity.ok().body("successful all books returned");
            }else {
                System.out.println("are not books");
                List<IssuedMovie> issuedMovies = issue.getIssuedMovies();
                for (IssuedMovie im : issuedMovies) {
                    im.setReturned(1);
                    issuedMovieService.save(im);

                    Movie movie = im.getMovie();
                    Integer copies = movie.getNoOfCopies();
                    movie.setNoOfCopies(copies + 1);
                    movieService.save(movie);
                }
                issue.setReturned(1);
                issueService.save(issue);
                return ResponseEntity.ok().body("successful all movies returned");
            }

        } else {
            return ResponseEntity.ok().body("error");
        }
    }

    @RequestMapping(value = "/returnABook/{issuedBookId}", method = RequestMethod.GET)
    public ResponseEntity<?> returnABook(@PathVariable Long issuedBookId){
        IssuedBook issuedBook = issuedBookService.get(issuedBookId);
      try {
          if (issuedBook != null) {
              //set the book returned
              issuedBook.setReturned(1);
              issuedBookService.save(issuedBook);

              Book book = issuedBook.getBook();
              Integer copies = book.getNoOfCopies();
              book.setNoOfCopies(copies + 1);
              bookService.save(book);

              Issue issue=issueService.get(issuedBook.getIssue().getIssueId());
              long count =issuedBookService.countBooksByIssueNotReturned(issue);
              if (count == 0){
                  issue.setReturned(1);
                  issueService.save(issue);
              }
              return ResponseEntity.ok().body("success");
          } else {
              return ResponseEntity.ok().body("un-successful");
          }
      }catch (Exception ex){
          return ResponseEntity.badRequest().body("error");
      }
    }

    //both
    @RequestMapping(value = "/viewMyIssues", method = RequestMethod.GET)
    public ResponseEntity<?> viewMyIssuedBooks() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            String email = userDetails.getEmail();
            User user = userService.directUserType(email);//get user
            List<Issue> userIssues = issueService.findBynotReturUser(user);
            return ResponseEntity.ok().body(userIssues);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");
        }

    }

    //both
    @RequestMapping(value = "/viewMyReturnedIssues", method = RequestMethod.GET)
    public ResponseEntity<?> viewMyReturnedIssuedBooks() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            String email = userDetails.getEmail();
            User user = userService.directUserType(email);//get user
            List<Issue> userIssues = issueService.findByReturnedUser(user);
            return ResponseEntity.ok().body(userIssues);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");
        }

    }
    @RequestMapping(value = "/isBooks/{issueId}",method = RequestMethod.GET)
    public String isBooks(@PathVariable Long issueId){
        if (issueService.isBooks(issueId)){
            return "books";
        }else{
            return "movies";
        }
    }

    //working both for movie and ooks
    @RequestMapping(value = "/viewIssued/{issueId}", method = RequestMethod.GET)
    public ResponseEntity<?> viewIssuedBookOrMovie(@PathVariable Long issueId){
       try{
           if (issueService.isBooks(issueId)){
               System.out.println(issueService.isBooks(issueId));
               Issue issue=issueService.get(issueId);
               List<IssuedBook> ibs= issuedBookService.findIssuedBooksBYissueNotReturned(issue);
               return ResponseEntity.ok().body(ibs);
           }else {
               System.out.println("are movies");
               Issue issue=issueService.get(issueId);
               List<IssuedMovie> iMs=issuedMovieService.findIssuedMoviesByIssueNotReturned(issue);
               return ResponseEntity.ok().body(iMs);
           }
       }catch (Exception e){
           return ResponseEntity.badRequest().body("error");
       }
    }

    @RequestMapping(value = "/viewReturnedIssued/{issueId}", method = RequestMethod.GET)
    public ResponseEntity<?> viewReturnIssuedBOOKS(@PathVariable Long issueId){
        try{
            if (issueService.isBooks(issueId)){
                System.out.println(issueService.isBooks(issueId));
                Issue issue=issueService.get(issueId);
                List<IssuedBook> ibs= issuedBookService.findIssuedBooksBYissueR(issue);
                return ResponseEntity.ok().body(ibs);
            }else {
                System.out.println("are movies");
                Issue issue=issueService.get(issueId);
                List<IssuedMovie> iMs=issuedMovieService.findIssuedMoviesByIssueR(issue);
                return ResponseEntity.ok().body(iMs);
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("error");
        }
    }
    //movie csv functions
    @RequestMapping(value = "/addCSVMovie")
    public ResponseEntity<?> addCSVMovie(@RequestBody MovieDTO movieDTO) {
        try {
            if (movieService.existsByTitle(movieDTO.getTitle())) {
                return ResponseEntity.badRequest().body("existsTitle");
            } else {
                movieService.addMovie(movieDTO);
                readerMovie.removeLine(movieDTO.getTitle());
                return ResponseEntity.ok("success");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");
        }
    }
    @RequestMapping(method = RequestMethod.PUT, value = "/updateCSVMovie/{movieId}")
    public ResponseEntity<?> updateCSVMovie(@Valid @PathVariable Integer movieId, @RequestBody MovieDTO movieDTO) {
        try {
            if (movieService.existsByTitle(movieDTO.getTitle())) {
                movieService.updateMovieCopies(movieDTO, movieId);
                readerMovie.removeLine(movieDTO.getTitle());
                return ResponseEntity.ok().body(movieDTO);
            } else {
                return ResponseEntity.badRequest().body("dontExists");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");
        }
    }


    ///////////////////////////////////////////Movies////////////////////////////////////

    @RequestMapping(value = "/addNewIssueMovie")
    public ResponseEntity<?> saveSingleMovieIssue(@RequestBody Issue issue) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        String email = userDetails.getEmail();
        return issueService.addSingleMovieIssue(email, issue);
    }

    //return a movie
    @RequestMapping(value = "/returnAMovie/{issuedMovieId}", method = RequestMethod.GET)
    public ResponseEntity<?> returnAMovie(@PathVariable Long issuedMovieId){
        IssuedMovie issuedMovie=issuedMovieService.get(issuedMovieId);
        try {
            if (issuedMovie != null) {
                //set the book returned
                issuedMovie.setReturned(1);
                issuedMovieService.save(issuedMovie);

                Movie movie = issuedMovie.getMovie();
                Integer copies = movie.getNoOfCopies();
                movie.setNoOfCopies(copies + 1);
                movieService.save(movie);

                Issue issue=issueService.get(issuedMovie.getIssue().getIssueId());
                long count =issuedMovieService.countMoviesByIssueNotReturned(issue);
                if (count == 0){
                    issue.setReturned(1);
                    issueService.save(issue);
                }
                return ResponseEntity.ok().body("success");
            } else {
                return ResponseEntity.ok().body("un-success");
            }
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("error");
        }
    }























    //temp
/*
    @RequestMapping(value = "/saveIssue")
    public ResponseEntity<?> saveIssue(@RequestBody Issue issue) {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            String email = userDetails.getEmail();
            User user = userService.directUserType(email);//get user
            Integer[] list;
            list = issue.getList();
            ///////////////////////////////////////
            Subscription mySubscription = subscriptionService.getSubById(user.getSubscription().getSubscriptionId());
            Integer subBooks = mySubscription.getNoOfBooks();
            Integer subDurationB = mySubscription.getDurationBooks() * 7;
            double subBCharge = mySubscription.getChargesBooks();
            double subOverDueCharges = mySubscription.getOverDueCharges();

            Long userIssueCount = issueService.getCountByUser(user);
            List<Issue> userIssues = issueService.findBynotReturUser(user);
            System.out.println(userIssues.size());

            Date currentDate = new Date();
            // convert date to calendar
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);

            c.add(Calendar.DATE, subDurationB); //change date to future date
            // convert calendar to date
            Date currentDatePlusFuture = c.getTime();

            Long ibCount = 0L;
            for (int x = 0; x < userIssues.size(); x++) {
                ibCount += Math.toIntExact(issuedBookService.countBooksByIssueNotReturned(userIssues.get(x)));
            }
            System.out.println("Total=>" + ibCount);
            if (ibCount <= subBooks && (list.length + ibCount) <= subBooks) {
                // can issue more Books
                //
                Issue issue1 = new Issue();
                issue1.setUser(user);//user added
                issue1.setIssueDate(currentDate);
                issue1.setReturned(0);
                issue1.setExpectedReturnDate(currentDatePlusFuture);
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
                //

                return ResponseEntity.ok().body(new MessageResponse("Success =>"));
            } else {
                return ResponseEntity.ok().body(new MessageResponse("Number of books for subscription is over"));
            }
            ////////////////////////////////////////


        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("error occcured" + e));
        }
    }


    @RequestMapping(value = "/returnBook/{issueId}/{id}")
    public ResponseEntity<?> returnBook(@PathVariable Long issueId, @PathVariable Integer id) {
        Issue issue = issueService.get(issueId);
        if (issue != null) {
            List<IssuedBook> issuedBooks = issue.getIssuedBooks();

            for (int k = 0; k < issuedBooks.size(); k++) {
                IssuedBook ib = issuedBooks.get(k);
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

    //View all issued books


    // view my issued books - user functions

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

*/
}