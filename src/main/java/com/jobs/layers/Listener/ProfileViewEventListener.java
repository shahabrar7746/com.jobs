package com.jobs.layers.Listener;

import com.jobs.helper;
import com.jobs.layers.Events.ProfileViewEvent;
import com.jobs.layers.Messages.Mails.MailBodies;
import com.jobs.layers.Messages.Mails.MailSubjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ProfileViewEventListener implements ApplicationListener<ProfileViewEvent> {


    @Autowired
    private helper help;
    @Override
    public void onApplicationEvent(ProfileViewEvent event) {
        String Body = MailBodies.PROFILE_VIEW  + "/n company : /n" + event.companyUrl + "/n query : " + event.query;
        help.sendEmail(event.email, MailSubjects.PROFILE_VIEW,Body);
    }
}
