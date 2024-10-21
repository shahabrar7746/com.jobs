package com.jobs.layers.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class tokens {
    private static final int TOKEN_EXIRATTION_TIME = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;


    public String token;

    public Date expiry;

    @OneToOne(targetEntity = user.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_ID_LOG_IN_TOKKEN"))

    public user userObj;

    public tokens(user userObj, String token){
        super();
        this.token = token;
        this.userObj = userObj;
        this.expiry = calculateExpirationTime();
    }
    static Date calculateExpirationTime(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, TOKEN_EXIRATTION_TIME);
        return new Date(cal.getTime().getTime());
    }

}
