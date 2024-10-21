package com.jobs.layers.Events;

import com.jobs.layers.Messages.Mails.MailBodies;
import com.jobs.layers.Messages.Mails.MailSubjects;
import org.springframework.context.ApplicationEvent;

public class JobInviteEvent extends ApplicationEvent {
    public String email;
   public String subject = MailSubjects.JOB_INVITATION;
    public String body = MailBodies.JOB_INVITATION;
    public String jobUrl;
    public JobInviteEvent(String email, String jobUrl) {
        super(email);
        this.jobUrl = jobUrl;
        this.email = email;
    }
}
