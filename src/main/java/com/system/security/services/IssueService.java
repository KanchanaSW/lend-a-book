package com.system.security.services;

import com.system.models.*;
import com.system.repository.IssueRepository;
import com.system.repository.IssuedBookRepository;
import com.system.repository.IssuedMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class IssueService {

    private IssueRepository issueRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private IssuedBookService issuedBookService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private BookService bookService;
    @Autowired
    private IssuedBookRepository issuedBookRepository;
    @Autowired
    private ReserveTempService reserveTempService;
    @Autowired
    private  IssuedMovieService issuedMovieService;
    @Autowired
    private IssuedMovieRepository issuedMovieRepository;
    @Autowired
    private  MovieService movieService;

    @Autowired
    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public List<Issue> getAll() {
        return issueRepository.findAll();
    }

    public Issue get(Long issueId) {
        return issueRepository.findById(issueId).get();
    }

    public boolean isBooks(Long issueId) {
        Issue issue=get(issueId);
        return issuedBookRepository.existsByIssue(issue);
    }

    public List<Issue> getAllUnreturned() {
        return issueRepository.findByReturned(0);
    }
    public List<Issue> getAllReturned() {
        return issueRepository.findByReturned(1);
    }

    public Issue addNew(Issue issue) {
        return issueRepository.save(issue);
    }

    public Issue save(Issue issue) {
        return issueRepository.save(issue);
    }

    public Long getCountByUser(User user) {
        return issueRepository.countByUserAndReturned(user, 0);
    }

    public List<Issue> findBynotReturUser(User user) {
        return issueRepository.findByReturnedAndUser(0, user);
    }
    public List<Issue> findByReturnedUser(User user) {
        return issueRepository.findByReturnedAndUser(1, user);
    }

    public ResponseEntity<?> extendReturn(Issue issue){
        try {
            Date extendRD=issue.getExtendReturnDate();
            if (extendRD == null){
                Date expectedRDate = issue.getExpectedReturnDate();
                Date eDate = new Date(expectedRDate.getTime()+(5*24*60*60*1000));
                double presentCharges = issue.getCharges();
                long days_difference = 5;
                User user = issue.getUser();
                Subscription mySubscription = subscriptionService.getSubById(user.getSubscription().getSubscriptionId());
                // Integer subBooks = mySubscription.getNoOfBooks();
                // Integer subDurationB = mySubscription.getDurationBooks() * 7;
                double subBCharge = mySubscription.getChargesBooks();
                double subOverDueCharges = mySubscription.getOverDueCharges();

                long booksCountNR = issuedBookService.countBooksByIssueNotReturned(issue);
                System.out.println(booksCountNR);
                double lendingChargePerBook = (days_difference) * subBCharge;
                double futureCharges = (lendingChargePerBook * booksCountNR);
                issue.setExtendReturnDate(eDate);
                issue.setCharges(presentCharges + futureCharges);
                issueRepository.save(issue);
                List<IssuedBook> ibs = issuedBookRepository.findByIssueAndReturned(issue, 0);
                System.out.println(ibs.size());

                for (int l = 0; l < ibs.size(); l++) {
                    IssuedBook ib = issuedBookService.get(ibs.get(l).getIssuedBookId());
                    double presentAmount = ib.getAmount();
                    ib.setStartDate(expectedRDate);
                    ib.setEndDate(eDate);
                    ib.setAmount(presentAmount + (lendingChargePerBook));
                    issuedBookService.save(ib);
                }
                return ResponseEntity.ok().body("success");
            }else {
                return ResponseEntity.ok().body("Already extended");
            }

        }catch (Exception ex){
            return ResponseEntity.badRequest().body("error");
        }
    }

    public ResponseEntity<?> addSingleIssue(String email,Issue issue) {
        try {
            User user = userService.directUserType(email);//get user
            Integer[] list;
            list = issue.getList();
            Subscription mySubscription = subscriptionService.getSubById(user.getSubscription().getSubscriptionId());
            Integer subBooks = mySubscription.getNoOfBooks();
            Integer subDurationB = mySubscription.getDurationBooks() * 7;
            double subBCharge = mySubscription.getChargesBooks();
            double subOverDueCharges = mySubscription.getOverDueCharges();
            Long userIssueCount = getCountByUser(user);
            List<Issue> userIssues = findBynotReturUser(user);
            //set days
            Date currentDate;
            Date currentDatePlusFuture ;
            if (issue.getIssueDate()==null){
                currentDate= new Date();
                // convert date to calendar
                Calendar c = Calendar.getInstance();
                c.setTime(currentDate);
                c.add(Calendar.DATE, subDurationB); //change date to future date
                // convert calendar to date
                currentDatePlusFuture = c.getTime();
            }else{
                currentDate=issue.getIssueDate();
                currentDatePlusFuture=issue.getExpectedReturnDate();
            }
            //calculate lending days
            long days_difference=calculateLendingDays(currentDate,currentDatePlusFuture);
            double lendingChargePerBook=(days_difference)*subBCharge;
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
                issue1.setCharges(lendingChargePerBook * list.length);
                issue1 = issueRepository.save(issue1);
                for (int l = 0; l < list.length; l++) {
                    Book book = bookService.findBook(list[l]);
                    reserveTempService.deleteReserveByBookId(user.getId(),list[l]);
                    if (book.getStatus().equals("Available")) {
                        Integer copies = book.getNoOfCopies();
                        book.setNoOfCopies(copies - 1);
                        book = bookService.save(book);

                        IssuedBook ib = new IssuedBook();
                        ib.setBook(book);
                        ib.setIssue(issue1);
                        ib.setStartDate(currentDate);
                        ib.setEndDate(currentDatePlusFuture);
                        ib.setAmount(lendingChargePerBook);
                        issuedBookService.addNew(ib);

                    } else {
                        return ResponseEntity.ok().body("book is un-available");
                    }
                }
                //
                return ResponseEntity.ok().body("success");
            } else {
                return ResponseEntity.ok().body("Number of books for subscription is over");
            }
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("error");
        }
    }

    public long calculateLendingDays(Date currentDate,Date currentDatePlusFuture){
        long time_difference =currentDatePlusFuture.getTime() - currentDate.getTime();
        // Calucalte time difference in days
        long days_difference = (time_difference / (1000*60*60*24)) % 365;
        return days_difference;
    }

    //save movie issue
    public ResponseEntity<?> addSingleMovieIssue(String email,Issue issue) {
        try {
            User user = userService.directUserType(email);//get user
            Integer[] list;
            list = issue.getList();
            Subscription mySubscription = subscriptionService.getSubById(user.getSubscription().getSubscriptionId());
            Integer subMovies = mySubscription.getNoOfMovies();
            int subDurationM = mySubscription.getDurationMovies() * 7;
            double subMCharge = mySubscription.getChargesMovies();
            double subOverDueCharges = mySubscription.getOverDueCharges();
            Long userIssueCount = getCountByUser(user);
            List<Issue> userIssues = findBynotReturUser(user);
            //set days
            Date currentDate;
            Date currentDatePlusFuture ;
            if (issue.getIssueDate()==null){
                currentDate= new Date();
                // convert date to calendar
                Calendar c = Calendar.getInstance();
                c.setTime(currentDate);
                c.add(Calendar.DATE, subDurationM); //change date to future date
                // convert calendar to date
                currentDatePlusFuture = c.getTime();
            }else{
                currentDate=issue.getIssueDate();
                currentDatePlusFuture=issue.getExpectedReturnDate();
            }
            //calculate lending days
            long days_difference=calculateLendingDays(currentDate,currentDatePlusFuture);
            double lendingChargePerMovie=(days_difference)*subMCharge;
            int ibCount = 0;
            for (Issue userIssue : userIssues) {
                ibCount += issuedMovieService.countMoviesByIssueNotReturned(userIssue);
            }
            System.out.println("Total=>" + ibCount);
            if (ibCount <= subMovies && (list.length + ibCount) <= subMovies) {
                // can issue more Books
                //
                Issue issue2 = new Issue();
                issue2.setUser(user);//user added
                issue2.setIssueDate(currentDate);
                issue2.setReturned(0);
                issue2.setExpectedReturnDate(currentDatePlusFuture);
                issue2.setCharges(lendingChargePerMovie * list.length);
                issue2 = issueRepository.save(issue2);
                System.out.println(issue2.getIssueDate());
                for (int l = 0; l < list.length; l++) {
                    Movie movie = movieService.findMovie(list[l]);
                    System.out.println("Title==> "+movie.getTitle());
                    reserveTempService.deleteReserveByMovieId(user.getId(),list[l]);//beta
                    if (movie.getStatus().equals("Available")) {
                        Integer copies = movie.getNoOfCopies();
                        movie.setNoOfCopies(copies - 1);
                        movie = movieService.save(movie);

                        System.out.println(movie.getNoOfCopies());
                        System.out.println(movie.getTitle());

                        IssuedMovie im = new IssuedMovie();
                        im.setMovie(movie);
                        im.setIssue(issue2);
                        im.setStartDate(currentDate);
                        im.setEndDate(currentDatePlusFuture);
                        im.setAmount(lendingChargePerMovie);
                        issuedMovieService.addNew(im);

                    } else {
                        return ResponseEntity.ok().body("movie is un-available");
                    }
                }
                //
                return ResponseEntity.ok().body("success");
            } else {
                return ResponseEntity.ok().body("Number of movies for subscription is over");
            }
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("error");
        }
    }

    //extend movie issue
    public ResponseEntity<?> extendReturnMovie(Issue issue){
        try {
            Date extendRD=issue.getExtendReturnDate();
            if (extendRD == null){
                Date expectedRDate = issue.getExpectedReturnDate();
                Date eDate = new Date(expectedRDate.getTime()+(5*24*60*60*1000));
                double presentCharges = issue.getCharges();
                long days_difference = 5;
                User user = issue.getUser();
                Subscription mySubscription = subscriptionService.getSubById(user.getSubscription().getSubscriptionId());
                // Integer subMovies = mySubscription.getNoOfMovies();
                // Integer subDurationM = mySubscription.getDurationMovies() * 7;
                double subMCharge = mySubscription.getChargesMovies();
                double subOverDueCharges = mySubscription.getOverDueCharges();

                long moviesCountNR = issuedMovieService.countMoviesByIssueNotReturned(issue);
                System.out.println(moviesCountNR);
                double lendingChargePerMovie = (days_difference) * subMCharge;
                double futureCharges = (lendingChargePerMovie * moviesCountNR);
                issue.setExtendReturnDate(eDate);
                issue.setCharges(presentCharges + futureCharges);
                issueRepository.save(issue);
                List<IssuedMovie> iMs = issuedMovieRepository.findByIssueAndReturned(issue, 0);
                System.out.println(iMs.size());

                for (IssuedMovie iM : iMs) {
                    IssuedMovie im = issuedMovieService.get(iM.getIssuedMovieId());
                    double presentAmount = im.getAmount();
                    im.setStartDate(expectedRDate);
                    im.setEndDate(eDate);
                    im.setAmount(presentAmount + (lendingChargePerMovie));
                    issuedMovieService.save(im);
                }
                return ResponseEntity.ok().body("success");
            }else {
                return ResponseEntity.ok().body("Already extended");
            }

        }catch (Exception ex){
            return ResponseEntity.badRequest().body("error");
        }
    }
}










