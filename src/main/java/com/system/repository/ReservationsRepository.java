package com.system.repository;

import com.system.models.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservations,Integer> {

}
