package com.system.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long otpId;
    @Column(nullable = false, unique = true)
    private Integer otpNumber;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false, name = "id")
    private User user;

    private Date expiryDate;
    public Date setExpiryDate(int mins){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, mins);
        this.expiryDate = now.getTime();
        return expiryDate;
    }
}
