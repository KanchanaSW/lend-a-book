package com.system.security.services;

import com.system.models.Book;
import com.system.models.Reservation;
import com.system.models.ReservationBook;
import com.system.models.User;
import com.system.payload.response.MessageResponse;
import com.system.repository.BookRepository;
import com.system.repository.ReservationBookRepository;
import com.system.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {


    private ReservationRepository reservationRepository;
    private ReservationBookRepository reservationBookRepository;
    private BookRepository bookRepository;
    private BookService bookService;
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ReservationBookRepository reservationBookRepository, BookRepository bookRepository, BookService bookService, UserDetailsServiceImpl userDetailsService) {
        this.reservationRepository = reservationRepository;
        this.reservationBookRepository = reservationBookRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.userDetailsService = userDetailsService;
    }



    public ResponseEntity<?> saveReservation(Reservation newReservation, String email, Double total){
        User user=userDetailsService.getByEmail(email);
        Integer[] list;
        List<ReservationBook> reservationBookList =new ArrayList<>();
        Reservation reservation =new Reservation();
        reservation.setIssueDate(newReservation.getIssueDate());
        reservation.setExpectedReturnDate(newReservation.getExpectedReturnDate());
        reservation.setStatus("Confirmed");
        reservation.setUser(user);
        reservation.setTotalAmount(total);
        list= newReservation.getList();
        if (list != null){
            for (int i=0; i< list.length;i++){
                Book book=bookRepository.findById(list[i]).get();
                System.out.println("++++++"+book.getTitle());
                ReservationBook reservationBook =new ReservationBook();
                reservationBook.setBook(book);
                reservationBook.setStartDate(newReservation.getIssueDate());
                reservationBook.setEndDate(newReservation.getExpectedReturnDate());
                reservationBookList.add(reservationBook);
                List<ReservationBook> reservationBooks = reservationBookRepository.
                        findByBookAndStartDateLessThanEqualAndEndDateGreaterThanEqual(book, reservationBook.getEndDate(), reservationBook.getStartDate());
                if (reservationBooks.size()>0){
                    return ResponseEntity.ok().body(new MessageResponse("The books are already issued"));
                }else {
                    reservation.setReservationBooks(reservationBookList);
                    reservation.getReservationBooks().add(reservationBook);
                }
            }
        }
        reservation.setReservationBooks(reservationBookList);
        reservationRepository.save(reservation);
        return ResponseEntity.ok().body(new MessageResponse("Books Issued"));
    }
    public List<Reservation> getAllReservation(Long id){
        return reservationRepository.findByUserId(id);
    }
    public List<Reservation> getAll(){
        return reservationRepository.findAll();
    }
    public Reservation getByReservationId(Integer issueId){
        return reservationRepository.findById(issueId).get();
    }
    public boolean checkReservationStatus(List<Reservation> list){
        boolean result=false;
        for (Reservation reservation :list){
            if (reservation.getStatus().equals("Cancelled")){
                result =true;
            }else{
                result=false;
            }
        }return result;
    }

    public ResponseEntity<?> updateStatusReservation(Integer reservationId,String status){
       // if (reservationRepository.existsById(reservationId)){
            Reservation reservation = reservationRepository.findById(reservationId).get();
            if (status.equals("Extended")){
                if (reservationBookRepository.existsByReservation(reservation)){
                    ReservationBook reservationBook = reservationBookRepository.findByReservation(reservation);
                    Date dateTimeTo = reservation.getExpectedReturnDate();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateTimeTo);
                    calendar.set(Calendar.HOUR_OF_DAY, 40); //24 hours + 1 hours to be 4 pm
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    Date newDateTime = calendar.getTime();
                    reservation.setExpectedReturnDate(newDateTime);
                    reservation.setStatus(status);
                    reservationBook.setEndDate(newDateTime);
                    reservationBookRepository.save(reservationBook);
                    reservationRepository.save(reservation);
                    return ResponseEntity.ok().body(reservation);
                }else {
                    Date dateTimeTo = reservation.getExpectedReturnDate();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateTimeTo);
                    calendar.set(Calendar.HOUR_OF_DAY, 38); //24 hours + 1 hours to be 4 pm
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    Date newDateTime = calendar.getTime();
                    reservation.setExpectedReturnDate(newDateTime);
                    reservation.setStatus(status);
                    reservationRepository.save(reservation);
                    return ResponseEntity.ok().body(reservation);
                }
            }else if (status.equals("Cancelled")){
                reservation.setStatus(status);
                reservationRepository.save(reservation);
                return ResponseEntity.ok().body(reservation);
            }else if (status.equals("Taken")){
                reservation.setStatus(status);
                reservationRepository.save(reservation);
                return ResponseEntity.ok().body(reservation);
            }
      //  }else {
          //  return ResponseEntity.badRequest().body(new MessageResponse("error Happend"));
        //}
        return ResponseEntity.unprocessableEntity().body(new MessageResponse("execption"));
    }
}












