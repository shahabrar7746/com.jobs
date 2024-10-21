package com.jobs.layers.Events;

import com.jobs.layers.Entities.user;
import org.springframework.context.ApplicationEvent;

public class ChangePasswordEvent extends ApplicationEvent {
  public user curUser;
  public String applicationUrl;
public String token;
  public ChangePasswordEvent(user curUser, String applicationUrl, String token){
      super(curUser);
      this.token = token;
      this.curUser = curUser;
      this.applicationUrl = applicationUrl;
  }


}
