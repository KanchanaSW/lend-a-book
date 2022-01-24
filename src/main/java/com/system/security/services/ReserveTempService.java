package com.system.security.services;

import com.system.DTO.ReserveTempDTO;
import com.system.models.Book;
import com.system.models.Movie;
import com.system.models.ReserveTemp;
import com.system.models.User;
import com.system.repository.BookRepository;
import com.system.repository.ReserveTempRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReserveTempService {
    @Autowired
    private ReserveTempRepository reserveTempRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private UserService userService;


    public ReserveTemp save(ReserveTempDTO reserveTempDTO,Long userId){
        try{
            ReserveTemp reserveTemp=new ReserveTemp();
            User user=userService.findUser(userId);
            reserveTemp.setUser(user);

            if (reserveTempDTO.getBookId() != null){
                Book book= bookService.findBook(reserveTempDTO.getBookId());
                if (!reserveTempRepository.existsByBookAndUser(book,user)){
                    reserveTemp.setBook(book);
                    reserveTempRepository.save(reserveTemp);
                }
            }
            if (reserveTempDTO.getMovieId() != null) {
                Movie movie = movieService.findMovie(reserveTempDTO.getMovieId());
                if (!reserveTempRepository.existsByMovieAndUser(movie, user)) {
                    reserveTemp.setMovie(movie);
                    reserveTempRepository.save(reserveTemp);
                }
            }
            return reserveTemp;

        }catch (Exception ex){
            return null;
        }
    }


    public void delete(Long reserveId){
        ReserveTemp reserveTemp= reserveTempRepository.getById(reserveId);
        reserveTempRepository.delete(reserveTemp);
    }
    public void deleteUserReserves(Long userId){
        User user=userService.findUser(userId);
        reserveTempRepository.deleteByUser(user);
    }
    public void deleteReserveByBookId(Long userId,Integer bookid){
        User user=userService.findUser(userId);
        Book book= bookService.findBook(bookid);
        ReserveTemp rt= reserveTempRepository.findByUserAndBook(user,book);
        System.out.println("////////////////////////"+rt.getReserveId());
      reserveTempRepository.deleteById(rt.getReserveId());
    }
    public ReserveTemp get(Long reserveTempId){
        return reserveTempRepository.getById(reserveTempId);
    }
    public List<ReserveTemp> usersReservesBooks(Long userId){
        User user=userService.findUser(userId);
        List<ReserveTemp> list=new ArrayList<>();
        for (ReserveTemp reserveTemp:reserveTempRepository.findByUser(user)){
            if (reserveTemp.getMovie()==null){
                System.out.println(reserveTemp.getReserveId());
                ReserveTemp rt=new ReserveTemp();
                rt.setReserveId(reserveTemp.getReserveId());
                rt.setUser(reserveTemp.getUser());
                rt.setBook(reserveTemp.getBook());
                list.add(rt);
            }
        }
        return list;
    }
    public List<ReserveTemp> usersReservesMovies(Long userId){
        User user=userService.findUser(userId);
        List<ReserveTemp> list=new ArrayList<>();
        for (ReserveTemp reserveTemp:reserveTempRepository.findByUser(user)){
            if (reserveTemp.getBook() == null){
                System.out.println(reserveTemp.getReserveId());
                ReserveTemp rt=new ReserveTemp();
                rt.setReserveId(reserveTemp.getReserveId());
                rt.setUser(reserveTemp.getUser());
                rt.setMovie(reserveTemp.getMovie());
                list.add(rt);
            }
        }
        return list;
    }
    public ReserveTemp getReserve(Integer bookId,Long userId){
        User user=userService.findUser(userId);
        Book book= bookService.findBook(bookId);
        return reserveTempRepository.findByUserAndBook(user,book);
    }

    //movie
    public void deleteReserveByMovieId(Long userId,Integer movieid){
        User user=userService.findUser(userId);
        Movie movie= movieService.findMovie(movieid);
        ReserveTemp rt= reserveTempRepository.findByUserAndMovie(user,movie);
        reserveTempRepository.delete(rt);
        System.out.println("//reserve deleted//=--"+rt.getReserveId());
    }
    public ReserveTemp getReserveMovie(Integer movieId,Long userId){
        Movie movie= movieService.findMovie(movieId);
        User user=userService.findUser(userId);
        return reserveTempRepository.findByUserAndMovie(user,movie);
    }
}
