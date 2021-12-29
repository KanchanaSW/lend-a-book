package com.system.security.services;

import com.system.models.Book;
import com.system.models.ReserveTemp;
import com.system.repository.BookRepository;
import com.system.repository.ReserveTempRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReserveTempService {
    @Autowired
    private ReserveTempRepository reserveTempRepository;


    public ReserveTemp save(ReserveTemp reserveTemp){
        return reserveTempRepository.save(reserveTemp);
    }
    public void delete(Long reserveId){
        ReserveTemp reserveTemp= reserveTempRepository.getById(reserveId);
        reserveTempRepository.delete(reserveTemp);
    }
    public void deleteUserReserves(Long userId){
        reserveTempRepository.deleteByUserId(userId);
    }
    public void deleteReserveByBookId(Long userId,Integer bookid){
        ReserveTemp rt= reserveTempRepository.findByUserIdAndBookId(userId,bookid);
        System.out.println("////////////////////////"+rt.getReserveId());
      reserveTempRepository.deleteById(rt.getReserveId());
    }
    public ReserveTemp get(Long reserveTempId){
        return reserveTempRepository.getById(reserveTempId);
    }
    public List<ReserveTemp> usersReserves(Long userId){
        return reserveTempRepository.findByUserId(userId);
    }
    public ReserveTemp getReserve(Integer bookId,Long userId){
        return reserveTempRepository.findByUserIdAndBookId(userId,bookId);
    }

    //movie
    public void deleteReserveByMovieId(Long userId,Integer movieid){
        ReserveTemp rt= reserveTempRepository.findByUserIdAndMovieId(userId,movieid);
        System.out.println("////////////////////////"+rt.getReserveId());
        reserveTempRepository.deleteById(rt.getReserveId());
    }
    public ReserveTemp getReserveMovie(Integer movieId,Long userId){
        return reserveTempRepository.findByUserIdAndMovieId(userId,movieId);
    }
}
