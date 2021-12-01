package com.system.security.services;

import com.system.models.ReserveTemp;
import com.system.repository.ReserveTempRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ReserveTemp get(Long reserveTempId){
        return reserveTempRepository.getById(reserveTempId);
    }
    public List<ReserveTemp> usersReserves(Long userId){
        return reserveTempRepository.findByUserId(userId);
    }
}
