package com.system.repository;

import com.system.models.Book;
import com.system.models.Reservation;
import com.system.models.ReservationBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationBookRepository extends JpaRepository<ReservationBook,Integer> {
    //Integer countByBookAndReturned(Book book,boolean returned);
    List<ReservationBook> findByBookAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Book book, Date endDate, Date startDate);
    boolean existsByBook(Book book);
    boolean existsByReservation(Reservation reservationId);
    ReservationBook findByReservation(Reservation reservationId);
}
