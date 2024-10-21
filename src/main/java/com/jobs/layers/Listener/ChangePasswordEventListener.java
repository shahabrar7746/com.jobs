package com.jobs.layers.Listener;

import com.jobs.layers.Entities.PasswordResetTokens;
import com.jobs.layers.Events.ChangePasswordEvent;
import com.jobs.layers.Repositories.reset_password_tokens_repo;
import com.jobs.layers.Services.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLNonTransientException;

@Component
public class ChangePasswordEventListener implements ApplicationListener<ChangePasswordEvent> {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Authentication auth;

    @Autowired
    private reset_password_tokens_repo resetPasswordRepo;

    public void onApplicationEvent(ChangePasswordEvent event) {
     String token = event.token;
        PasswordResetTokens tokenObject = new PasswordResetTokens(event.curUser, token);
try {

    resetPasswordRepo.save(tokenObject);
}catch(Exception exception){
 resetPasswordRepo.deleteById(tokenObject.id);
 resetPasswordRepo.save(tokenObject);
}
        logger.info("Publishing Change Password Event");
    }
}
