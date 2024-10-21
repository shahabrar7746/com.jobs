package com.jobs.layers.Listener;

import com.jobs.helper;
import com.jobs.layers.Events.JobInviteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class JobInviteEventListener implements ApplicationListener<JobInviteEvent> {
    @Autowired
    private helper help;
    @Override
    public void onApplicationEvent(JobInviteEvent event) {
        help.sendEmail(event.email, event.subject,event.body + event.jobUrl);
    }
}
