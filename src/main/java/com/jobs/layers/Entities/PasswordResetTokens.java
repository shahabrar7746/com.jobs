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
public class PasswordResetTokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String token;
    private static int TOKEN_EXIRATTION_TIME = 10;

    public Date expirationTime;

    @OneToOne(targetEntity = user.class)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_ID_PASSWORD_RESET_TOKKEN"))

    public user curUser;
    public PasswordResetTokens(user User, String token){
        super();
        this.curUser = User;
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
