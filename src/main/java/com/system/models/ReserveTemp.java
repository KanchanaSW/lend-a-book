package com.system.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class ReserveTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reserveId;
    private Integer bookId;
    private Long movieId;
    private Long userId;
}
