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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@PreAuthorize("hasRole('ADMIN')")
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////   Reserve Controller     /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/reserve")
    public ResponseEntity<?> saveReserve(@RequestBody ReserveTemp reserveTemp){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Long userId= userDetails.getId();
        try{
            ReserveTemp rt=new ReserveTemp();
            rt.setBookId(reserveTemp.getBookId());
            rt.setUserId(userId);
            rt.setMovieId(reserveTemp.getMovieId());
            reserveTempService.save(rt);
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

    @RequestMapping(value = "/userReserves",method = RequestMethod.GET)
    public ResponseEntity<?> findUserReserves(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Long userId= userDetails.getId();
        try{
            List<ReserveTemp> rts=reserveTempService.usersReserves(userId);
            return ResponseEntity.ok().body(rts);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("error"+ex);
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
            return ResponseEntity.badRequest().body("error"+ex);
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

    @RequestMapping(value = "/extendIssue/{issueId}", method = RequestMethod.GET)
    public ResponseEntity<?> extendBookIssue(@PathVariable Long issueId) {

        Issue issue1 = issueService.get(issueId);
        if (issue1 != null) {
            return issueService.extendReturn(issue1);
        } else {
            return ResponseEntity.ok().body(new MessageResponse("unSuccessfull"));
        }
    }

    @RequestMapping(value = "/returnAllBooks/{issueId}", method = RequestMethod.GET)
    public ResponseEntity<?> returnAllBooks(@PathVariable Long issueId) {
        Issue issue = issueService.get(issueId);
        if (issue != null) {
            List<IssuedBook> issuedBooks = issue.getIssuedBooks();
            for (int k = 0; k < issuedBooks.size(); k++) {
                IssuedBook ib = issuedBooks.get(k);
                ib.setReturned(1);
                issuedBookService.save(ib);

                Book book = ib.getBook();
                Integer copies = book.getNoOfCopies();
                book.setNoOfCopies(copies + 1);
                bookService.save(book);
            }
            issue.setReturned(1);
            issueService.save(issue);
            return ResponseEntity.ok().body(new MessageResponse("successfull"));
        } else {
            return ResponseEntity.ok().body(new MessageResponse("unSuccessfull"));
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
              return ResponseEntity.ok().body(new MessageResponse("successfull"));
          } else {
              return ResponseEntity.ok().body(new MessageResponse("unSuccessfull"));
          }
      }catch (Exception ex){
          return ResponseEntity.badRequest().body("ex errer"+ex);
      }finally {

      }
    }

    @RequestMapping(value = "/viewMyIssues", method = RequestMethod.GET)
    public ResponseEntity<?> viewMyIssuedBooks() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            String email = userDetails.getEmail();
            User user = userService.directUserType(email);//get user
            List<Issue> userIssues = issueService.findBynotReturUser(user);
            /*
            List<Long> issuedBookIds = new ArrayList<Long>();
            for (int k = 0; k < userIssues.size(); k++) {
                issuedBookIds.add(issuedBookService.issuedBookId(userIssues.get(k)));
            }
            List<IssuedBook> ibList= issuedBookService.findIssuedBooksForUserIssues(issuedBookIds);
            */
            return ResponseEntity.ok().body(userIssues);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }

    }

    @RequestMapping(value = "/viewMyReturnedIssues", method = RequestMethod.GET)
    public ResponseEntity<?> viewMyReturnedIssuedBooks() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            String email = userDetails.getEmail();
            User user = userService.directUserType(email);//get user
            List<Issue> userIssues = issueService.findByReturnedUser(user);
            /*
            List<Long> issuedBookIds = new ArrayList<Long>();
            for (int k = 0; k < userIssues.size(); k++) {
                issuedBookIds.add(issuedBookService.issuedBookId(userIssues.get(k)));
            }
            List<IssuedBook> ibList= issuedBookService.findIssuedBooksForUserIssues(issuedBookIds);
            */
            return ResponseEntity.ok().body(userIssues);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }

    }

    @RequestMapping(value = "/viewIssuedBooks/{issueId}", method = RequestMethod.GET)
    public ResponseEntity<?> viewIssuedBOOKS(@PathVariable Long issueId){
       try{
           Issue issue=issueService.get(issueId);
           List<IssuedBook> ibs= issuedBookService.findIssuedBooksBYissueNotReturned(issue);
           return ResponseEntity.ok().body(ibs);
       }catch (Exception e){
           return ResponseEntity.badRequest().body("Error"+e);
       }
    }

    @RequestMapping(value = "/viewReturnedIssuedBooks/{issueId}", method = RequestMethod.GET)
    public ResponseEntity<?> viewReturnIssuedBOOKS(@PathVariable Long issueId){
        try{
            Issue issue=issueService.get(issueId);
            List<IssuedBook> ibs= issuedBookService.findIssuedBooksBYissueR(issue);
            return ResponseEntity.ok().body(ibs);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error"+e);
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