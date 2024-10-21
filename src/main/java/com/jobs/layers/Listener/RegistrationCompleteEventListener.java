package com.jobs.layers.Listener;

import com.jobs.helper;
import com.jobs.layers.Entities.user;
import com.jobs.layers.Events.RegistrationCompleteEvent;
import com.jobs.layers.Messages.Mails.MailSubjects;
import com.jobs.layers.Messages.TaskCategories;
import com.jobs.layers.Messages.Tasks;
import com.jobs.layers.Services.Authentication;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import java.util.UUID;


@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

 private  Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Authentication auth;

    @Autowired
    private helper help;

    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create a verification tokken
        user curUser = event.curUser;
        String token = UUID.randomUUID().toString();
        String applicationUrl = event.applicationUrl;
       auth.saveVerificationTokenForUser(curUser,token);
       String userType = curUser.type;
String url = event.applicationUrl + "/"+ userType +  "/verifyRegistration?token=" + token;

logger.info("issued verification token {} for email {}", token, curUser.email);
logger.info("{} : {}",TaskCategories.COMPLETED_TASK,Tasks.COMPLETE_REGITRATION_TASK);
logger.info("{} : {}",TaskCategories.EXPECTED_TASK, Tasks.VERIFICATION_BY_EMIAL);
help.sendEmail(curUser.email, MailSubjects.IDENTITY_VERIFICATION, url);




    }
}
