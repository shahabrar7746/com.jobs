package com.jobs.layers.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class verificationTokens {



    private static int TOKEN_EXIRATTION_TIME = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String token;
    public Date expirationTime;

    @OneToOne(targetEntity = user.class,
    fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_ID_VERIFICATION_TOKEN"))

    public user User;


    public verificationTokens(user User, String token){
        super();
        this.User = User;
        this.token = token;
        this.expirationTime = calculateExpirationTime();
    }
    public verificationTokens(String token){
        super();
        this.token = token;
       this.expirationTime = calculateExpirationTime();
    }
static Date calculateExpirationTime(){
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(new Date().getTime());
    cal.add(Calendar.MINUTE, TOKEN_EXIRATTION_TIME);
    return new Date(cal.getTime().getTime());
}
}
